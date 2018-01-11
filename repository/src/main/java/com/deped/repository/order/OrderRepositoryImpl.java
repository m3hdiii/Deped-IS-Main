package com.deped.repository.order;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.account.User;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.order.Order;
import com.deped.model.order.OrderDetailsState;
import com.deped.model.order.OrderState;
import com.deped.model.order.Schedule;
import com.deped.model.search.OrderSearch;
import com.deped.model.supply.Supplier;
import com.deped.model.unit.Unit;
import com.deped.repository.ListParameter;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Order create(Order entity) throws DatabaseRolesViolationException {
        if (entity.getOrderState() == null)
            entity.setOrderState(OrderState.SAVED);

        Order savedOrder = hibernateFacade.saveEntity(Order.class, entity);
        return savedOrder;
    }

    @Override
    public Boolean update(Order entity) throws DatabaseRolesViolationException {
        Boolean isUpdated = hibernateFacade.updateEntity(entity);
        return isUpdated;
    }

    @Override
    public List<Order> fetchAll() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("SELECT * FROM order_ ")
                .append("ORDER BY CASE ")
                .append("WHEN order_state='ORDERING' THEN 0 ")
                .append("WHEN order_state='ORDERS_REGISTERED' THEN 1 ")
                .append("WHEN order_state='ALL_APPROVED' THEN 2 ")
                .append("WHEN order_state='PARTIALLY_APPROVED' THEN 3 ")
                .append("WHEN order_state='ORDERED' THEN 4 ")
                .append("WHEN order_state='ALL_ARRIVED' THEN 5 ")
                .append("WHEN order_state='PARTIALLY_ARRIVED' THEN 6 ")
                .append("WHEN order_state='NOT_ARRIVED' THEN 7 ")
                .append("END ASC");
        List<Order> items = hibernateFacade.fetchAllEntityBySqlQuery(sb.toString(), Order.class);
        return items;
    }

    @Override
    public List<Order> fetchByRange(Range range) {
        List<Order> items = hibernateFacade.fetchAllEntity(FETCH_ALL_ITEMS, range, Order.class);
        return items;
    }

    @Override
    public Order fetchById(Long id) {
        Order item = hibernateFacade.fetchEntityById(Order.class, id);
        return item;
    }

    @Override
    public Boolean remove(Order... entities) throws DatabaseRolesViolationException {
        Boolean isOrderDeleted = hibernateFacade.removeEntities(ITEM_TABLE, ITEM_TABLE_ID, entities);
        return isOrderDeleted;
    }

    @Override
    public Boolean createOrUpdateAll(Order... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public List<Order> fetchAllByOrderState(OrderState orderState) {
        String nativeQuery = "SELECT * FROM order_ WHERE order_state = :orderState";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("orderState", orderState.toString());
        List<Order> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Order.class, parameterMap);
        return requests;
    }

    @Override
    public List<Order> fetchAllByUsername(String username) {
        String nativeQuery = "SELECT * FROM order_ WHERE username = :username";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("username", username);
        List<Order> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Order.class, parameterMap);
        return requests;
    }

    @Override
    public List<Order> fetchAllByStates(List<OrderState> orderStates) {

        String fetchQuery = "SELECT * FROM order_ WHERE order_state IN ( %s ) ORDER BY order_state";
        StringBuilder sb = new StringBuilder();
        for (OrderState ods : orderStates) {
            sb
                    .append("'")
                    .append(ods.toString())
                    .append("' ,");
        }

        String formatValue = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String query = String.format(fetchQuery, formatValue);

        Session hibernateSession = null;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        List<Order> list;
        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery<Order> nativeQuery = hibernateSession.createNativeQuery(query, Order.class);
            list = nativeQuery.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        }

        return list;

    }


    @Override
    public List<Order> orderSearch(OrderSearch orderSearch) {

        if (orderSearch == null)
            return null;

        boolean isOrderDateFrom = isEmpty(orderSearch.getOrderDateFrom());
        boolean isOrderDateTo = isEmpty(orderSearch.getOrderDateTo());
        boolean isRequiredDateFrom = isEmpty(orderSearch.getRequiredDateFrom());
        boolean isRequiredDateTo = isEmpty(orderSearch.getRequiredDateTo());
        boolean isOrderedUserListEmpty = isEmpty(orderSearch.getUsers());
        boolean isOrderScheduleListEmpty = isEmpty(orderSearch.getOrderSchedules());
        boolean isBudgetAmountFromEmpty = isEmpty(orderSearch.getBudgetAmountFrom());
        boolean isBudgetAmountToEmpty = isEmpty(orderSearch.getBudgetAmountTo());
        boolean isOrderStatesEmpty = isEmpty(orderSearch.getOrderStates());
        boolean isArrivalDescriptionEmpty = isEmpty(orderSearch.getArrivalDescription());
        boolean isItemListEmpty = isEmpty(orderSearch.getItems());
        boolean isCategoryListEmpty = isEmpty(orderSearch.getCategories());
        boolean isUnitListEmpty = isEmpty(orderSearch.getUnits());
        boolean isUnitPriceFromEmpty = isEmpty(orderSearch.getUnitPriceFrom());
        boolean isUnitPriceToEmpty = isEmpty(orderSearch.getUnitPriceTo());
        boolean isUnitCapacityFromEmpty = isEmpty(orderSearch.getUnitCapacityFrom());
        boolean isUnitCapacityToEmpty = isEmpty(orderSearch.getUnitCapacityTo());
        boolean isNoOfUnitsFromEmpty = isEmpty(orderSearch.getNoOfUnitsFrom());
        boolean isNoOfUnitsToEmpty = isEmpty(orderSearch.getNoOfUnitsTo());
        boolean isTotalQuantityRequestNoFromEmpty = isEmpty(orderSearch.getTotalQuantityRequestNoFrom());
        boolean isTotalQuantityRequestNoToEmpty = isEmpty(orderSearch.getTotalQuantityRequestNoTo());
        boolean isTotalQuantityArrivedNoFromEmpty = isEmpty(orderSearch.getTotalQuantityArrivedNoFrom());
        boolean isTotalQuantityArrivedNoToEmpty = isEmpty(orderSearch.getTotalQuantityArrivedNoTo());
        boolean isOrderDetailsStatesListEmpty = isEmpty(orderSearch.getOrderDetailsStates());
        boolean isSuppliersEmpty = isEmpty(orderSearch.getSuppliers());//Problem
        boolean isDisapprovalMessageEmpty = isEmpty(orderSearch.getDisapprovalMessage());
        boolean isNotArrivalMessageEmpty = isEmpty(orderSearch.getNotArrivalMessage());
        boolean isConsideredByUserListEmpty = isEmpty(orderSearch.getConsideredByUsers());
        boolean isOrderedByUserListEmpty = isEmpty(orderSearch.getOrderedByUsers());
        boolean isReceivedByUserListEmpty = isEmpty(orderSearch.getReceivedByUsers());

        boolean[] emptyList = new boolean[]{
                isOrderDateFrom, isOrderDateTo, isRequiredDateFrom, isRequiredDateTo,
                isOrderedUserListEmpty, isOrderScheduleListEmpty, isBudgetAmountFromEmpty, isBudgetAmountToEmpty,
                isOrderStatesEmpty, isArrivalDescriptionEmpty, isItemListEmpty, isCategoryListEmpty,
                isUnitListEmpty, isUnitPriceFromEmpty, isUnitPriceToEmpty, isUnitCapacityFromEmpty, isUnitCapacityToEmpty,
                isNoOfUnitsFromEmpty, isNoOfUnitsToEmpty, isTotalQuantityRequestNoFromEmpty, isTotalQuantityRequestNoToEmpty,
                isTotalQuantityArrivedNoFromEmpty, isTotalQuantityArrivedNoToEmpty, isOrderDetailsStatesListEmpty, isSuppliersEmpty, isDisapprovalMessageEmpty,
                isNotArrivalMessageEmpty, isConsideredByUserListEmpty,
                isOrderedByUserListEmpty, isReceivedByUserListEmpty
        };

        StringBuilder sb = new StringBuilder("SELECT distinct order_id, order_date, username, order_schedule, budget_amount, required_date, order_state, arrival_description FROM order_ ");

        String where = null;
        for (boolean b : emptyList) {
            if (!b) {
                where = " JOIN order_details ON order_.order_id = order_details.order_order_id WHERE\n";
                break;
            }
        }


        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;
        List<Order> list = null;

        if (where == null) {

            try {
                tx = hibernateSession.beginTransaction();
                NativeQuery<Order> query = hibernateSession.createNativeQuery(sb.toString(), Order.class);
                list = query.list();
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return list;
            }


            return list;

        } else {

            sb.append(where);

            ListParameter orderedUserParameter = isOrderedUserListEmpty ? null : createListParameter(orderSearch.getUsers(), "username", User.class);
            ListParameter orderScheduleListParameter = isOrderScheduleListEmpty ? null : createListParameter(orderSearch.getOrderSchedules(), "order_schedule", Schedule.class);
            ListParameter orderStateListParameter = isOrderStatesEmpty ? null : createListParameter(orderSearch.getOrderStates(), "order_state", OrderState.class);
            ListParameter orderItemListParameter = isItemListEmpty ? null : createListParameter(orderSearch.getItems(), "item_item_name", Item.class);
            ListParameter categoryListParameter = isCategoryListEmpty ? null : createListParameter(orderSearch.getCategories(), "category_category_name", Category.class);
            ListParameter unitListParameter = isUnitListEmpty ? null : createListParameter(orderSearch.getUnits(), "unit_name", Unit.class);
            ListParameter orderDetailsStateListParameter = isOrderDetailsStatesListEmpty ? null : createListParameter(orderSearch.getOrderDetailsStates(), "order_details_state", OrderDetailsState.class);
            ListParameter supplierListParameter = isSuppliersEmpty ? null : createListParameter(orderSearch.getSuppliers(), "supplier_id", Supplier.class);
            ListParameter consideredByUserParameter = isConsideredByUserListEmpty ? null : createListParameter(orderSearch.getConsideredByUsers(), "considered_by_username", User.class);
            ListParameter orderedByUserParameter = isOrderedByUserListEmpty ? null : createListParameter(orderSearch.getOrderedByUsers(), "ordered_by_username", User.class);
            ListParameter receivedByUserParameter = isReceivedByUserListEmpty ? null : createListParameter(orderSearch.getReceivedByUsers(), "received_by_username", User.class);


            sb
                    .append(isOrderDateFrom ? "" : "(order_.order_date >= :orderDateFrom) AND\n")
                    .append(isOrderDateTo ? "" : "(order_.order_date < :orderDateTo) AND\n")
                    .append(isRequiredDateFrom ? "" : "(order_.required_date >= :requiredDateFrom) AND\n")
                    .append(isRequiredDateTo ? "" : "(order_.required_date < :requiredDateTo) AND\n")
                    .append(isOrderedUserListEmpty ? "" : String.format("(order_.username IN ( %s )) AND\n", orderedUserParameter.getWherePartSection()))
                    .append(isOrderScheduleListEmpty ? "" : String.format("(order_.order_schedule IN ( %s )) AND\n", orderScheduleListParameter.getWherePartSection()))
                    .append(isBudgetAmountFromEmpty ? "" : "(order_.budget_amount >= :budgetAmountFrom) AND\n")
                    .append(isBudgetAmountToEmpty ? "" : "(order_.budget_amount < :budgetAmountTo) AND\n")
                    .append(isOrderStatesEmpty ? "" : String.format("(order_.order_state IN ( %s )) AND\n", orderStateListParameter.getWherePartSection()))
                    .append(isArrivalDescriptionEmpty ? "" : "(order_.arrival_description LIKE :arrivalDescription) AND\n")
                    .append(isItemListEmpty ? "" : String.format("(order_details.item_item_name IN ( %s )) AND\n", orderItemListParameter.getWherePartSection()))
                    .append(isCategoryListEmpty ? "" : String.format("(order_details.category_category_name IN ( %s )) AND\n", categoryListParameter.getWherePartSection()))
                    .append(isUnitListEmpty ? "" : String.format("(order_details.unit_name IN ( %s )) AND\n", unitListParameter.getWherePartSection()))

                    .append(isUnitPriceFromEmpty ? "" : "(order_details.item_unit_price >= :itemUnitPriceFrom) AND\n")
                    .append(isUnitPriceToEmpty ? "" : "(order_details.item_unit_price < :itemUnitPriceTo) AND\n")
                    .append(isUnitCapacityFromEmpty ? "" : "(order_details.unit_capacity >= :unitCapacityFrom) AND\n")
                    .append(isUnitCapacityToEmpty ? "" : "(order_details.unit_capacity < :unitCapacityTo) AND\n")
                    .append(isNoOfUnitsFromEmpty ? "" : "(order_details.no_of_units >= :noOfUnitsFrom) AND\n")
                    .append(isNoOfUnitsToEmpty ? "" : "(order_details.no_of_units < :noOfUnitsTo) AND\n")
                    .append(isTotalQuantityRequestNoFromEmpty ? "" : "(order_details.total_quantity_requested_no >= :totalQuantityRequestedFrom) AND\n")
                    .append(isTotalQuantityRequestNoToEmpty ? "" : "(order_details.total_quantity_requested_no < :totalQuantityRequestedTo) AND\n")
                    .append(isTotalQuantityArrivedNoFromEmpty ? "" : "(order_details.total_quantity_arrived_no >= :totalQuantityArrivedFrom) AND\n")
                    .append(isTotalQuantityArrivedNoToEmpty ? "" : "(order_details.total_quantity_arrived_no < :totalQuantityArrivedTo) AND\n")
                    .append(isSuppliersEmpty ? "" : String.format("(order_details.supplier_id IN ( %s )) AND\n", supplierListParameter.getWherePartSection()))
                    .append(isDisapprovalMessageEmpty ? "" : "(order_details.disapproval_message LIKE :disapprovalMessage) AND\n")
                    .append(isNotArrivalMessageEmpty ? "" : "(order_details.not_arrival_message LIKE :notArrivedMessage ) AND\n")
                    .append(isOrderDetailsStatesListEmpty ? "" : String.format("(order_details.order_details_state IN ( %s )) AND\n", orderDetailsStateListParameter.getWherePartSection()))

                    .append(isConsideredByUserListEmpty ? "" : String.format("(order_details.considered_by_username IN ( %s )) AND\n", consideredByUserParameter.getWherePartSection()))
                    .append(isOrderedByUserListEmpty ? "" : String.format("(order_details.ordered_by_username IN ( %s )) AND\n", orderedByUserParameter.getWherePartSection()))
                    .append(isReceivedByUserListEmpty ? "" : String.format("(order_details.received_by_username IN ( %s )) AND\n", receivedByUserParameter.getWherePartSection()));

            try {
                tx = hibernateSession.beginTransaction();

                String strQuery = sb.toString().trim();
                if (strQuery.substring(strQuery.lastIndexOf(" ") + 1).equals("AND")) {
                    strQuery = strQuery.substring(0, strQuery.lastIndexOf("AND"));
                }

                NativeQuery<Order> query = hibernateSession.createNativeQuery(strQuery, Order.class);

                ListParameter[] listParameters = new ListParameter[]{orderedUserParameter, orderScheduleListParameter, orderStateListParameter,
                        orderItemListParameter, categoryListParameter, unitListParameter, supplierListParameter,
                        orderDetailsStateListParameter, consideredByUserParameter, orderedByUserParameter, receivedByUserParameter
                };

                Map<String, Object> parameterMap = new HashMap<>();
                for (int i = 0; i < listParameters.length; i++) {
                    ListParameter lpTemp = listParameters[i];
                    if (lpTemp != null) {
                        parameterMap.putAll(lpTemp.getParameterMap());
                    }
                }


                if (!isOrderDateFrom) {
                    parameterMap.put("orderDateFrom", orderSearch.getOrderDateFrom());
                }

                if (!isOrderDateTo) {
                    parameterMap.put("orderDateTo", orderSearch.getOrderDateTo());
                }

                if (!isRequiredDateFrom) {
                    parameterMap.put("requiredDateFrom", orderSearch.getRequiredDateFrom());
                }

                if (!isRequiredDateTo) {
                    parameterMap.put("requiredDateTo", orderSearch.getRequiredDateTo());
                }

                if (!isBudgetAmountFromEmpty) {
                    parameterMap.put("budgetAmountFrom", orderSearch.getBudgetAmountFrom());
                }

                if (!isBudgetAmountToEmpty) {
                    parameterMap.put("budgetAmountTo", orderSearch.getBudgetAmountTo());
                }


                if (!isArrivalDescriptionEmpty) {
                    parameterMap.put("arrivalDescription", "%" + orderSearch.getArrivalDescription() + "%");
                }

                if (!isUnitPriceFromEmpty) {
                    parameterMap.put("itemUnitPriceFrom", orderSearch.getUnitPriceFrom());
                }

                if (!isUnitPriceToEmpty) {
                    parameterMap.put("itemUnitPriceTo", orderSearch.getUnitPriceTo());
                }

                if (!isUnitCapacityFromEmpty) {
                    parameterMap.put("unitCapacityFrom", orderSearch.getUnitCapacityFrom());
                }

                if (!isUnitCapacityToEmpty) {
                    parameterMap.put("unitCapacityTo", orderSearch.getUnitCapacityTo());
                }

                if (!isNoOfUnitsFromEmpty) {
                    parameterMap.put("noOfUnitsFrom", orderSearch.getNoOfUnitsFrom());
                }

                if (!isNoOfUnitsToEmpty) {
                    parameterMap.put("noOfUnitsTo", orderSearch.getNoOfUnitsTo());
                }

                if (!isTotalQuantityRequestNoFromEmpty) {
                    parameterMap.put("totalQuantityRequestedFrom", orderSearch.getTotalQuantityRequestNoFrom());
                }

                if (!isTotalQuantityRequestNoToEmpty) {
                    parameterMap.put("totalQuantityRequestedTo", orderSearch.getTotalQuantityRequestNoTo());
                }

                if (!isTotalQuantityArrivedNoFromEmpty) {
                    parameterMap.put("totalQuantityArrivedFrom", orderSearch.getTotalQuantityArrivedNoFrom());
                }

                if (!isTotalQuantityArrivedNoToEmpty) {
                    parameterMap.put("totalQuantityArrivedTo", orderSearch.getTotalQuantityArrivedNoTo());
                }


                if (!isDisapprovalMessageEmpty) {
                    parameterMap.put("disapprovalMessage", "%" + orderSearch.getDisapprovalMessage() + "%");
                }

                if (!isNotArrivalMessageEmpty) {
                    parameterMap.put("notArrivedMessage", "%" + orderSearch.getNotArrivalMessage() + "%");
                }

                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.setParameter(key, value);
                }

                list = query.list();
                tx.commit();

            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return null;
            }


            return list;
        }

    }

    private ListParameter createListParameter(List objects, String baseParamKey, Class<?> clazz) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<String, Object> parameterMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < objects.size(); i++) {
            String key = baseParamKey + i;
            if (clazz == User.class)
                parameterMap.put(key, ((User) objects.get(i)).getUsername());

            if (clazz == Item.class)
                parameterMap.put(key, ((Item) objects.get(i)).getName());

            if (clazz == Category.class)
                parameterMap.put(key, ((Category) objects.get(i)).getName());


            if (clazz == Unit.class)
                parameterMap.put(key, ((Unit) objects.get(i)).getName());

            if (clazz == Supplier.class)
                parameterMap.put(key, ((Supplier) objects.get(i)).getName());

            if (clazz == OrderState.class || clazz == Schedule.class || clazz == OrderDetailsState.class)
                parameterMap.put(key, (objects.get(i)).toString());

            sb.append(":" + key + " , ");
        }

        String tmp = sb.toString();
        String wherePartSection = tmp.substring(0, (tmp.lastIndexOf(",") - 1));

        ListParameter listParameter = new ListParameter(wherePartSection, parameterMap);
        return listParameter;
    }

    private boolean isEmpty(Object object) {
        if (object == null)
            return true;

        if (object instanceof Date) {
            return false;
        }

        if (object instanceof Integer)
            return false;

        if (object instanceof String) {
            String s = (String) object;
            if (s.isEmpty()) {
                return true;
            }
            return false;
        }

        if (object instanceof List) {
            List s = (List) object;
            if (s.isEmpty()) {
                return true;
            }
            return false;
        }

        return true;
    }
}
