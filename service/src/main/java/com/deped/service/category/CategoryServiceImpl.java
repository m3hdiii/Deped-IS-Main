package com.deped.service.category;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.category.Category;
import com.deped.repository.category.CategoryRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Category> create(Category entity) {
        Category savedEntity = null;
        try {
            savedEntity = categoryRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        if (savedEntity == null) {
            ResponseEntity<Category> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return responseEntity;
        }

        ResponseEntity<Category> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Category entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = categoryRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Category.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Category>> fetchAll() {
        List<Category> categories = categoryRepository.fetchAll();
        ResponseEntity<List<Category>> responseEntity = new ResponseEntity<>(categories, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Category>> fetchByRange(Range range) {
        List<Category> categories = categoryRepository.fetchByRange(range);
        ResponseEntity<List<Category>> responseEntity = new ResponseEntity<>(categories, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Category> fetchById(String id) {
        Category category = categoryRepository.fetchById(id);
        ResponseEntity<Category> responseEntity = new ResponseEntity<>(category, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Category... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = categoryRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Category.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Category... entities) {
        return null;
    }
}
