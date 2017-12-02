package com.deped.service.supplier;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.supply.Supplier;
import com.deped.repository.supplier.SupplierRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplyRepository;
    private static final String BASE_FILE_FOLDER = "suppliers";

    @Override
    public ResponseEntity<Supplier> create(Supplier entity) {
        String pictureBase64 = entity.getPictureBase64();

        String fileName = ServiceUtils.saveImageIntoDisk(pictureBase64, BASE_FILE_FOLDER);
        entity.setPicName(fileName);

        Supplier savedEntity = null;
        try {
            savedEntity = supplyRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Supplier> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Supplier entity) {
        String pictureBase64 = entity.getPictureBase64();

        if (pictureBase64 != null) {
            String fileName = ServiceUtils.saveImageIntoDisk(pictureBase64, BASE_FILE_FOLDER);
            entity.setPicName(fileName);
        }

        Boolean isUpdated = null;
        try {
            isUpdated = supplyRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Supplier.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Supplier>> fetchAll() {
        List<Supplier> supplies = supplyRepository.fetchAll();
        ResponseEntity<List<Supplier>> responseEntity = new ResponseEntity<>(supplies, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Supplier>> fetchByRange(Range range) {
        List<Supplier> supplies = supplyRepository.fetchByRange(range);
        ResponseEntity<List<Supplier>> responseEntity = new ResponseEntity<>(supplies, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Supplier> fetchById(Long id) {
        Supplier supply = supplyRepository.fetchById(id);
        ResponseEntity<Supplier> responseEntity = new ResponseEntity<>(supply, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Supplier... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = supplyRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Supplier.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Supplier... entities) {
        return null;
    }
}
