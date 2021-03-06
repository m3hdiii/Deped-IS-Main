package com.deped.service.brand;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.brand.Brand;
import com.deped.repository.brand.BrandRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private BrandRepository brandRepository;
    private static final String BASE_FILE_FOLDER = "brands";

    @Override
    public ResponseEntity<Brand> create(Brand entity) {
        String pictureBase64 = entity.getLogoPic();

        String fileName = ServiceUtils.saveImageIntoDisk(pictureBase64, BASE_FILE_FOLDER);
        entity.setLogoUrl(fileName);

        Brand savedEntity = null;
        try {
            savedEntity = brandRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Brand> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Brand entity) {
        Boolean isUpdated = null;

        String pictureBase64 = entity.getLogoPic();
        String fileName = ServiceUtils.saveImageIntoDisk(pictureBase64, BASE_FILE_FOLDER);
        entity.setLogoUrl(fileName);

        try {
            isUpdated = brandRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Brand.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Brand>> fetchAll() {
        List<Brand> brands = brandRepository.fetchAll();
        ResponseEntity<List<Brand>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Brand>> fetchByRange(Range range) {
        List<Brand> brands = brandRepository.fetchByRange(range);
        ResponseEntity<List<Brand>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Brand> fetchById(String id) {
        Brand brand = brandRepository.fetchById(id);
        ResponseEntity<Brand> responseEntity = new ResponseEntity<>(brand, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Brand... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = brandRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Brand.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Brand... entities) {
        return null;
    }
}
