package com.deped.service;

import com.deped.model.Response;
import com.deped.repository.utils.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseService<T, ID> {
    HttpStatus OK = HttpStatus.OK;

    ResponseEntity<T> create(T entity);

    ResponseEntity<Response> update(T entity);

    ResponseEntity<List<T>> fetchAll();

    ResponseEntity<List<T>> fetchByRange(Range range);

    ResponseEntity<T> fetchById(ID id);

    ResponseEntity<Response> remove(T... entities);

    ResponseEntity<Response> createOrUpdateAll(T... entities);
}
