package com.deped.repository.requests;

import com.deped.model.request.Request;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowRequestRepositoryImpl implements BorrowRequestRepository {

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
        List<Request> query = hibernateFacade.fetchAllEntity("fetchAll", Request.class);
        return query;
    }

    @Override
    public List<Request> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity("", range, Request.class);
    }

    @Override
    public Request fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Request.class, id);
    }

    @Override
    public Boolean remove(Request... entities) {
        return hibernateFacade.removeEntities("borrow_request", "borrow_request_id", entities);
    }
}
