package com.deped.repository.order;

import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsID;
import com.deped.model.order.OrderDetailsState;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public OrderDetails create(OrderDetails entity) {
        return hibernateFacade.saveEntity(OrderDetails.class, entity);
    }

    @Override
    public Boolean update(OrderDetails entity) {
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
        Boolean isDone = hibernateFacade.createOrUpdateAll(OrderDetails.class, entities);
        return isDone;
    }

    @Override
    public boolean updateOrderStatus(OrderDetails[] entities) {
        final String baseQuery = "UPDATE order_details SET order_details_state = :orderDetailsState "
                .concat("WHERE (order_order_id, item_item_id, category_category_id) IN (");

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
                doUpdate(sb.toString(), parameterMap);
                parameterMap.clear();
                sb.setLength(0);
                sb.append(baseQuery);
                firstElementState = nextIndexState;
            }
        }


        return false;
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

        new OrderDetailsRepositoryImpl().updateOrderStatus(orderDetailsList);
    }
}
