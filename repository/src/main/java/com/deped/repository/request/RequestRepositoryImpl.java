package com.deped.repository.request;

import com.deped.model.request.Request;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Request create(Request entity) {
        return hibernateFacade.saveEntity(Request.class, entity);
    }

    @Override
    public Boolean update(Request entity) {
        return hibernateFacade.updateEntity(entity);
    }


    @Override
    public List<Request> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS, Request.class);
    }

    @Override
    public List<Request> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS, range, Request.class);
    }

    @Override
    public Request fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Request.class, id);
    }

    @Override
    public Boolean remove(Request... entities) {
        return hibernateFacade.removeEntities(REQUEST_TABLE, REQUEST_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Request... entities) {
        return null;
    }
}
