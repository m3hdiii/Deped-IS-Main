package com.deped.service.pack;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.pack.Pack;
import com.deped.repository.pack.PackRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackServiceImpl implements PackService {

    @Autowired
    private PackRepository packRepository;

    @Override
    public ResponseEntity<Pack> create(Pack entity) {
        Pack savedEntity = null;
        try {
            savedEntity = packRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Pack> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Pack entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = packRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Pack.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Pack>> fetchAll() {
        List<Pack> packs = packRepository.fetchAll();
        ResponseEntity<List<Pack>> responseEntity = new ResponseEntity<>(packs, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Pack>> fetchByRange(Range range) {
        List<Pack> packs = packRepository.fetchByRange(range);
        ResponseEntity<List<Pack>> responseEntity = new ResponseEntity<>(packs, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Pack> fetchById(Object id) {
        Pack pack = packRepository.fetchById(id);
        ResponseEntity<Pack> responseEntity = new ResponseEntity<>(pack, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Pack... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = packRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Pack.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Pack... entities) {
        return null;
    }
}
