package com.deped.service.requests;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.request.Request;
import com.deped.repository.request.BorrowRequestRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {

    @Autowired
    private BorrowRequestRepository borrowRequestRepository;

    @Override
    public ResponseEntity<Request> create(Request entity) {
        Request savedEntity = borrowRequestRepository.create(entity);
        ResponseEntity<Request> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Request entity) {
        Boolean isUpdated = borrowRequestRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Request.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> fetchAll() {
        List<Request> entities = borrowRequestRepository.fetchAll();
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(entities, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> fetchByRange(Range range) {
        List<Request> entities = borrowRequestRepository.fetchByRange(range);
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(entities, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Request> fetchById(Object id) {
        Request entity = borrowRequestRepository.fetchById(id);
        ResponseEntity<Request> responseEntity = new ResponseEntity<>(entity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Request... entities) {
        Boolean isRemoved = borrowRequestRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Request.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Request... entities) {
        return null;
    }
}
