package com.deped.repository.order;

import com.deped.model.order.OrderDetails;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
