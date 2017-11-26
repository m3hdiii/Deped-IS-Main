package com.deped.repository.order;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Order fetchById(Object id) {
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
    public List<Order> fetchAllByUserId(Long userId) {
        String nativeQuery = "SELECT * FROM order_ WHERE user_id = :userId";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userId", userId);
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
            hibernateSession = hibernateFacade.getSessionFactory().openSession();
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
}
