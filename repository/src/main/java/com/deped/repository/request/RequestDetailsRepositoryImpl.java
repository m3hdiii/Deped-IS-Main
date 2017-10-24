package com.deped.repository.request;

import com.deped.model.request.RequestDetails;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class RequestDetailsRepositoryImpl implements RequestDetailsRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public RequestDetails create(RequestDetails entity) {
        return hibernateFacade.saveEntity(RequestDetails.class, entity);
    }

    @Override
    public Boolean update(RequestDetails entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<RequestDetails> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS_DETAILS, RequestDetails.class);
    }

    @Override
    public List<RequestDetails> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_REQUESTS_DETAILS_RANGES, range, RequestDetails.class);
    }

    @Override
    public RequestDetails fetchById(Object id) {
        return hibernateFacade.fetchEntityById(RequestDetails.class, id);
    }

    @Override
    public Boolean remove(RequestDetails... entities) {
        //TODO write different query for this
        return hibernateFacade.removeEntities(REQUEST_DETAILS_TABLE, "composite?", entities);
    }

    @Override
    public Boolean createOrUpdateAll(RequestDetails... entities) {
        Boolean isDone = hibernateFacade.createOrUpdateAll(RequestDetails.class, entities);
        return isDone;
    }

}
