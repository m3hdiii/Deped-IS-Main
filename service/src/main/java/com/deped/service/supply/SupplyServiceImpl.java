package com.deped.service.supply;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.supply.Supply;
import com.deped.repository.supply.SupplyRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Override
    public ResponseEntity<Supply> create(Supply entity) {
        Supply savedEntity = supplyRepository.create(entity);
        ResponseEntity<Supply> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Supply entity) {
        Boolean isUpdated = supplyRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Supply.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Supply>> fetchAll() {
        List<Supply> supplies = supplyRepository.fetchAll();
        ResponseEntity<List<Supply>> responseEntity = new ResponseEntity<>(supplies, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Supply>> fetchByRange(Range range) {
        List<Supply> supplies = supplyRepository.fetchByRange(range);
        ResponseEntity<List<Supply>> responseEntity = new ResponseEntity<>(supplies, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Supply> fetchById(Object id) {
        Supply supply = supplyRepository.fetchById(id);
        ResponseEntity<Supply> responseEntity = new ResponseEntity<>(supply, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Supply... entities) {
        Boolean isRemoved = supplyRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Supply.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
