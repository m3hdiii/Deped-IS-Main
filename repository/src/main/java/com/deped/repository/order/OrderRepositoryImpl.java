package com.deped.repository.order;

import com.deped.model.order.Order;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Order create(Order entity) {
        Order savedOrder = hibernateFacade.saveEntity(Order.class, entity);
        return savedOrder;
    }

    @Override
    public Boolean update(Order entity) {
        Boolean isUpdated = hibernateFacade.updateEntity(entity);
        return isUpdated;
    }

    @Override
    public List<Order> fetchAll() {
        List<Order> items = hibernateFacade.fetchAllEntity(FETCH_ALL_ITEMS, Order.class);
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
    public Boolean remove(Order... entities) {
        Boolean isOrderDeleted = hibernateFacade.removeEntities(ITEM_TABLE, ITEM_TABLE_ID, entities);
        return isOrderDeleted;
    }
}
