package com.deped.service.category;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.category.Category;
import com.deped.repository.category.CategoryRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Category> create(Category entity) {
        Category savedEntity = categoryRepository.create(entity);
        ResponseEntity<Category> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Category entity) {
        Boolean isUpdated = categoryRepository.update(entity);
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
    public ResponseEntity<Category> fetchById(Object id) {
        Category category = categoryRepository.fetchById(id);
        ResponseEntity<Category> responseEntity = new ResponseEntity<>(category, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Category... entities) {
        Boolean isRemoved = categoryRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Category.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
