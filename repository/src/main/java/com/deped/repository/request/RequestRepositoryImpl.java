package com.deped.repository.request;

import com.deped.model.request.Request;
import com.deped.model.request.RequestStatus;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Request create(Request entity) {
        if (entity.getRequestStatus() == null)
            entity.setRequestStatus(RequestStatus.SAVED);

        Request request = hibernateFacade.saveEntity(Request.class, entity);
        return request;
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

    @Override
    public List<Request> fetchAllByRequestStatus(RequestStatus requestStatus) {
        String nativeQuery = "SELECT * FROM request WHERE request_status = :requestStatus";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("requestStatus", requestStatus.toString());
        List<Request> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Request.class, parameterMap);
        return requests;
    }

    @Override
    public List<Request> fetchAllByUserId(Long userId) {
        String nativeQuery = "SELECT * FROM request WHERE user_id = :userId";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userId", userId);
        List<Request> requests = hibernateFacade.fetchAllEntityBySqlQuery(nativeQuery, null, Request.class, parameterMap);
        return requests;
    }


}
