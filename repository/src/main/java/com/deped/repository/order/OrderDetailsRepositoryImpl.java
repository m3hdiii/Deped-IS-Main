package com.deped.repository.order;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.items.Item;
import com.deped.model.order.*;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {
    @Autowired
    private HibernateFacade hibernateFacade;

    private class StateUpdateBean {
        private String query;
        private Map<String, Object> parameterMap;

        public StateUpdateBean(String query, Map<String, Object> parameterMap) {
            this.query = query;
            this.parameterMap = parameterMap;
        }

        public String getQuery() {
            return query;
        }

        public Map<String, Object> getParameterMap() {
            return parameterMap;
        }
    }

    @Override
    public OrderDetails create(OrderDetails entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(OrderDetails.class, entity);
    }

    @Override
    public Boolean update(OrderDetails entity) throws DatabaseRolesViolationException {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<OrderDetails> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS_DETAILS, OrderDetails.class);
    }

    @Override
    public List<OrderDetails> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS_DETAILS_RANGES, range, OrderDetails.class);
    }

    @Override
    public OrderDetails fetchById(Object id) {
        return hibernateFacade.fetchEntityById(OrderDetails.class, id);
    }

    @Override
    public Boolean remove(OrderDetails... entities) {
        //TODO write different query for this
        return hibernateFacade.removeEntities(REQUEST_DETAILS_TABLE, "composite?", entities);
    }

    @Override
    public Boolean createOrUpdateAll(OrderDetails... entities) {

        if (entities.length == 0) {
            return false;
        }

        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;


        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery nativeQuery = hibernateSession.createNativeQuery("DELETE FROM order_details WHERE order_order_id = :orderId");
            nativeQuery.setParameter("orderId", entities[0].getOrderDetailsID().getOrderId());
            nativeQuery.executeUpdate();

            saveOrUpdate(hibernateSession, entities);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }
        return true;
    }

    private void saveOrUpdate(Session hibernateSession, OrderDetails... entities) {
        for (OrderDetails elem : entities) {
            OrderDetailsState state = elem.getOrderDetailsState();
            if (state == null) {
                elem.setOrderDetailsState(OrderDetailsState.WAITING);
            }

            hibernateSession.saveOrUpdate("OrderDetails", elem);
        }
    }

    public boolean updateOrderState(Long userId, OrderDetailsState orderDetailsState, OrderDetails... entities) {
        List<StateUpdateBean> stateUpdateBeanList = makeStateUpdateBeanList(entities, userId, orderDetailsState);
        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        boolean isAllCancelled = true;
        for (OrderDetails ord : entities) {
            if (ord.getOrderDetailsState() != OrderDetailsState.CANCELED && ord.getOrderDetailsState() != OrderDetailsState.DISAPPROVED && ord.getOrderDetailsState() != OrderDetailsState.NOT_ARRIVED) {
                isAllCancelled = false;
                break;
            }
        }

        OrderState nextOrderState = null;
        if (isAllCancelled) {
            nextOrderState = OrderState.FINALIZED;
        } else {
            switch (orderDetailsState) {
                case APPROVED:
                case DISAPPROVED:
                    nextOrderState = OrderState.CONSIDERED;
                    break;
                case ORDERED:
                    nextOrderState = OrderState.ORDERED;
                    break;
                case ARRIVED:
                case NOT_ARRIVED:
                case CANCELED:
                    nextOrderState = OrderState.FINALIZED;
                    break;
            }
        }

        Transaction tx = null;
        int effectedRows = -1;
        try {
            tx = hibernateSession.beginTransaction();

            String requestStatusQuery = "UPDATE order_ SET order_state = :orderState WHERE order_id = :orderId";
            NativeQuery<Order> requestNativeQuery = hibernateSession.createNativeQuery(requestStatusQuery, Order.class);
            requestNativeQuery.setParameter("orderState", nextOrderState.toString());
            requestNativeQuery.setParameter("orderId", entities[0].getOrderDetailsID().getOrderId());
            int requestRowEffected = requestNativeQuery.executeUpdate();
            if (requestRowEffected <= 0) {
                throw new Exception("Sql update operation encountered a problem");
            }


            for (StateUpdateBean sub : stateUpdateBeanList) {
                NativeQuery<OrderDetails> nativeQuery = hibernateSession.createNativeQuery(sub.getQuery(), OrderDetails.class);
                for (Map.Entry<String, Object> entry : sub.getParameterMap().entrySet()) {
                    nativeQuery.setParameter(entry.getKey(), entry.getValue());
                }
                effectedRows = nativeQuery.executeUpdate();
                if (effectedRows <= 0) {
                    throw new Exception("Sql update operation encountered a problem");
                }
            }


            String notArrivalReasonQuery = "UPDATE order_details SET not_arrival_message = :reason WHERE order_order_id = :orderId AND item_item_id = :itemId AND category_category_id = :categoryId";
            String disapprovalReasonQuery = "UPDATE order_details SET disapproval_message = :reason WHERE order_order_id = :orderId AND item_item_id = :itemId AND category_category_id = :categoryId";
            for (OrderDetails od : entities) {
                String notArrivalReason = od.getNotArrivalMessage();
                String disapprovalMessage = od.getDisapprovalMessage();
                if (notArrivalReason != null && !notArrivalReason.isEmpty()) {
                    notArrivalOrDisapprovalProcessor(od, notArrivalReason, hibernateSession, notArrivalReasonQuery);
                }

                if (disapprovalMessage != null && !disapprovalMessage.isEmpty()) {
                    notArrivalOrDisapprovalProcessor(od, disapprovalMessage, hibernateSession, disapprovalReasonQuery);
                }
            }

            String updateItemQuantityQuery = "UPDATE item SET quantity = quantity + :newQuantity WHERE item_id = :itemId";
            if (orderDetailsState == OrderDetailsState.ARRIVED) {
                for (OrderDetails od : entities) {
                    if (od.getOrderDetailsState() == OrderDetailsState.ARRIVED) {
                        NativeQuery<Item> nativeQuery = hibernateSession.createNativeQuery(updateItemQuantityQuery, Item.class);
                        nativeQuery.setParameter("newQuantity", od.getTotalQuantityArrivedNo());
                        nativeQuery.setParameter("itemId", od.getOrderDetailsID().getItemId());
                        nativeQuery.executeUpdate();
                    }
                }
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }

        return true;

    }

    private void notArrivalOrDisapprovalProcessor(OrderDetails orderDetails, String reason, Session hibernateSession, String query) {
        NativeQuery<OrderDetails> updateMessagesNativeQuery = hibernateSession.createNativeQuery(query, OrderDetails.class);
        updateMessagesNativeQuery.setParameter("orderId", orderDetails.getOrderDetailsID().getOrderId());
        updateMessagesNativeQuery.setParameter("itemId", orderDetails.getOrderDetailsID().getItemId());
        updateMessagesNativeQuery.setParameter("categoryId", orderDetails.getOrderDetailsID().getCategoryId());
        updateMessagesNativeQuery.setParameter("reason", reason);
        updateMessagesNativeQuery.executeUpdate();
    }

    @Override
    public boolean orderAll(OrderDetails... entities) {
        if (entities.length == 0) {
            return false;
        }

        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;


        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery nativeQuery = hibernateSession.createNativeQuery("UPDATE order_ SET order_state = 'PENDING' WHERE order_id = :orderId");
            nativeQuery.setParameter("orderId", entities[0].getOrderDetailsID().getOrderId());
            nativeQuery.executeUpdate();

            nativeQuery = hibernateSession.createNativeQuery("DELETE FROM order_details WHERE order_order_id = :orderId");
            nativeQuery.setParameter("orderId", entities[0].getOrderDetailsID().getOrderId());
            nativeQuery.executeUpdate();

            saveOrUpdate(hibernateSession, entities);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }
        return true;
    }

    @Override
    public List<OrderDetails> fetchAllById(Long orderId) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("orderId", orderId);
        String sqlQuery = "SELECT * FROM order_details WHERE order_order_id = :orderId AND order_details_state NOT IN ('DISAPPROVED', 'NOT_ARRIVED', 'CANCELLED') ";
        List<OrderDetails> list = hibernateFacade.fetchAllEntityBySqlQuery(sqlQuery, null, OrderDetails.class, parameterMap);
        return list;
    }

    @Override
    public List<OrderDetails> fetchAllByStates(List<OrderDetailsState> orderDetailsStates) {
        String fetchQuery = "SELECT * FROM order_details WHERE order_details_state IN ( %s ) ORDER BY order_details_state";
        StringBuilder sb = new StringBuilder();
        for (OrderDetailsState ods : orderDetailsStates) {
            sb
                    .append("'")
                    .append(ods.toString())
                    .append("' ,");
        }

        String formatValue = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String query = String.format(fetchQuery, formatValue);

        Session hibernateSession = null;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        List<OrderDetails> list;
        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery<OrderDetails> nativeQuery = hibernateSession.createNativeQuery(query, OrderDetails.class);
            list = nativeQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }
        return list;
    }

    private void setMap(Map<String, Object> parameterMap, OrderDetails temp, String orderIdMapKey, String itemIdMapKey, String categoryIdMapKey) {
        parameterMap.put(orderIdMapKey, temp.getOrderDetailsID().getOrderId());
        parameterMap.put(itemIdMapKey, temp.getOrderDetailsID().getItemId());
        parameterMap.put(categoryIdMapKey, temp.getOrderDetailsID().getCategoryId());
    }

    private boolean doUpdate(String query, Map<String, Object> parameterMap) {
        query = query.substring(0, query.lastIndexOf(",")).concat(")");
//
//        int numberOfRowEffected = hibernateFacade.updateEntitySqlQuery(query, OrderDetails.class, parameterMap);
//        return numberOfRowEffected != -1;

        return false;
    }

    public static void main(String[] args) {
        new OrderDetailsRepositoryImpl().fetchAllByStates(new ArrayList<OrderDetailsState>() {{
            add(OrderDetailsState.CANCELED);
            add(OrderDetailsState.NOT_ARRIVED);
            add(OrderDetailsState.DISAPPROVED);
        }});

        for (int i = 1; i <= 254; i++) {
            System.out.println("192.168.0." + i);
        }

        OrderDetails[] orderDetailsList = new OrderDetails[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                OrderDetails od = new OrderDetails();
                Long id = Long.parseLong(i + "");
                od.setOrderDetailsID(new OrderDetailsID(id, id, id));
                od.setOrderDetailsState(OrderDetailsState.APPROVED);
                orderDetailsList[i] = od;
            } else {
                OrderDetails od = new OrderDetails();
                Long id = Long.parseLong(i + "");
                od.setOrderDetailsID(new OrderDetailsID(id, id, id));
                od.setOrderDetailsState(OrderDetailsState.DISAPPROVED);
                orderDetailsList[i] = od;
            }
        }

        new OrderDetailsRepositoryImpl().updateOrderState(0L, OrderDetailsState.WAITING, orderDetailsList);
    }


    private List<StateUpdateBean> makeStateUpdateBeanList(OrderDetails[] entities, Long userId, OrderDetailsState orderDetailsState) {

        List<StateUpdateBean> stateUpdateBeanList = new ArrayList<>();
        String stitch = "";
        switch (orderDetailsState) {
            case APPROVED:
            case DISAPPROVED:
                stitch = ", considered_by_user_id = :userId";
                break;
            case ORDERED:
                stitch = ", ordered_by_user_id = :userId";
                break;
            case ARRIVED:
            case NOT_ARRIVED:
                stitch = ", received_by_user_id = :userId";
                break;
        }

        String strFormat = "UPDATE order_details SET order_details_state = :orderDetailsState %s "
                .concat("WHERE (order_order_id, item_item_id, category_category_id) IN (");

        final String baseQuery = String.format(strFormat, stitch);

        List<OrderDetails> orderDetailsList = Arrays.asList(entities);
        Collections.sort(orderDetailsList, Comparator.comparing(OrderDetails::getOrderDetailsState));

        OrderDetailsState firstElementState = null;
        if (!orderDetailsList.isEmpty()) {
            firstElementState = orderDetailsList.get(0).getOrderDetailsState();
        }

        StringBuilder sb = new StringBuilder(baseQuery);
        Map<String, Object> parameterMap = new HashMap<>();

        final String orderIdMapKeyFormat = "orderId%d";
        final String itemIdMapKeyFormat = "itemId%d";
        final String categoryIdMapKeyFormat = "categoryId%d";
        final String colonStr = ":";

        for (int i = 0; i < orderDetailsList.size(); i++) {
            OrderDetails temp = orderDetailsList.get(i);

            String orderIdMapKey = String.format(orderIdMapKeyFormat, i);
            String itemIdMapKey = String.format(itemIdMapKeyFormat, i);
            String categoryIdMapKey = String.format(categoryIdMapKeyFormat, i);
            setMap(parameterMap, temp, orderIdMapKey, itemIdMapKey, categoryIdMapKey);
            String tempAppend = String.format("( %s , %s , %s), ", (colonStr + orderIdMapKey), (colonStr + itemIdMapKey), (colonStr + categoryIdMapKey));
            sb.append(tempAppend);

            boolean isLastElement = (i + 1 == orderDetailsList.size());
            OrderDetailsState nextIndexState;

            if (isLastElement) {
                nextIndexState = orderDetailsList.get(i).getOrderDetailsState();
            } else {
                nextIndexState = orderDetailsList.get(i + 1).getOrderDetailsState();
            }

            if (isLastElement || nextIndexState != firstElementState) {
                parameterMap.put("orderDetailsState", firstElementState.toString());
                parameterMap.put("userId", userId);
                String query = sb.toString();
                query = query.substring(0, query.lastIndexOf(",")).concat(")");
                stateUpdateBeanList.add(new StateUpdateBean(query, parameterMap));

                parameterMap = new HashMap<>();
                sb.setLength(0);
                sb.append(baseQuery);
                firstElementState = nextIndexState;
            }
        }
        return stateUpdateBeanList;
    }
}
