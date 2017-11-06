package com.deped.service.request;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.request.RequestDetails;
import com.deped.repository.request.RequestDetailsRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestDetailsServiceImpl implements RequestDetailsService {


    @Autowired
    private RequestDetailsRepository requestDetailsRepository;

    @Override
    public ResponseEntity<RequestDetails> create(RequestDetails entity) {
        RequestDetails savedEntity = requestDetailsRepository.create(entity);
        ResponseEntity<RequestDetails> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(RequestDetails entity) {
        Boolean isUpdated = requestDetailsRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<RequestDetails>> fetchAll() {
        List<RequestDetails> brands = requestDetailsRepository.fetchAll();
        ResponseEntity<List<RequestDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<RequestDetails>> fetchByRange(Range range) {
        List<RequestDetails> brands = requestDetailsRepository.fetchByRange(range);
        ResponseEntity<List<RequestDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<RequestDetails> fetchById(Object id) {
        RequestDetails brand = requestDetailsRepository.fetchById(id);
        ResponseEntity<RequestDetails> responseEntity = new ResponseEntity<>(brand, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(RequestDetails... entities) {
        Boolean isRemoved = requestDetailsRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(RequestDetails... entities) {
        Boolean isCreated = requestDetailsRepository.createOrUpdateAll(entities);
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> requestAll(RequestDetails... entities) {
        Boolean isCreated = requestDetailsRepository.requestAll(entities);
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> updateRequestStatus(RequestDetails[] entities) {
        Boolean isUpdated = requestDetailsRepository.updateRequestStatus(entities);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, RequestDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;

    }

    @Override
    public ResponseEntity<List<RequestDetails>> fetchAllById(Long requestId) {
        List<RequestDetails> brands = requestDetailsRepository.fetchAllById(requestId);
        ResponseEntity<List<RequestDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }
}
