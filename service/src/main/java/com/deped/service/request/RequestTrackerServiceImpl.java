package com.deped.service.request;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.repository.request.RequestTrackerRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTrackerServiceImpl implements RequestTrackerService {

    @Autowired
    private RequestTrackerRepository requestTrackerRepository;

    @Override
    public ResponseEntity<RequestTracker> create(RequestTracker entity) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(RequestTracker entity) {
        return null;
    }

    @Override
    public ResponseEntity<List<RequestTracker>> fetchAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<RequestTracker>> fetchByRange(Range range) {
        return null;
    }

    @Override
    public ResponseEntity<RequestTracker> fetchById(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Response> remove(RequestTracker... entities) {
        return null;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(RequestTracker... entities) {
        boolean isCreated = false;
        try {
            isCreated = requestTrackerRepository.createOrUpdateAll(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
        }
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> updateRequestStatus(String username, RequestDetailsStatus status, RequestDetails[] entities) {
        Boolean isUpdated = requestTrackerRepository.updateRequestStatus(username, status, entities);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
