package com.deped.service.unit;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.unit.Unit;
import com.deped.repository.unit.UnitRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public ResponseEntity<Unit> create(Unit entity) {
        Unit savedEntity = null;
        try {
            savedEntity = unitRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Unit> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Unit entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = unitRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Unit.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Unit>> fetchAll() {
        List<Unit> units = unitRepository.fetchAll();
        ResponseEntity<List<Unit>> responseEntity = new ResponseEntity<>(units, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Unit>> fetchByRange(Range range) {
        List<Unit> units = unitRepository.fetchByRange(range);
        ResponseEntity<List<Unit>> responseEntity = new ResponseEntity<>(units, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Unit> fetchById(String id) {
        Unit unit = unitRepository.fetchById(id);
        ResponseEntity<Unit> responseEntity = new ResponseEntity<>(unit, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Unit... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = unitRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Unit.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Unit... entities) {
        return null;
    }
}
