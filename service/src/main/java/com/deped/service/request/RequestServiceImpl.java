package com.deped.service.request;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.request.Request;
import com.deped.model.request.RequestStatus;
import com.deped.model.search.RequestSearch;
import com.deped.repository.request.RequestRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {


    @Autowired
    private RequestRepository requestRepository;

    @Override
    public ResponseEntity<Request> create(Request entity) {
        Request savedEntity = null;
        try {
            savedEntity = requestRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Request> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Request entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = requestRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Request.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> fetchAll() {
        List<Request> requests = requestRepository.fetchAll();
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> fetchByRange(Range range) {
        List<Request> requests = requestRepository.fetchByRange(range);
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Request> fetchById(Long id) {
        Request request = requestRepository.fetchById(id);
        ResponseEntity<Request> responseEntity = new ResponseEntity<>(request, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Request... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = requestRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Request.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Request... entities) {
        return null;
    }

    @Override
    public ResponseEntity<List<Request>> fetchAllByRequestStatus(RequestStatus requestStatus) {
        List<Request> requests = requestRepository.fetchAllByRequestStatus(requestStatus);
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> fetchAllConsideredEquipment() {
        List<Request> requests = requestRepository.fetchAllConsideredEquipment();
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> fetchAllByUsername(String username) {
        List<Request> requests = requestRepository.fetchAllByUsername(username);
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Request>> requestSearch(RequestSearch requestSearch) {
        List<Request> requests = requestRepository.requestSearch(requestSearch);
        ResponseEntity<List<Request>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }
}
