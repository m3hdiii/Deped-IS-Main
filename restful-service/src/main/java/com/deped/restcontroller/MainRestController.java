package com.deped.restcontroller;

import com.deped.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;


import java.util.List;

public interface MainRestController<T, ID> extends ConstantController {

    ResponseEntity<T> create(T entity);

    ResponseEntity<Response> update(T entity);

    ResponseEntity<List<T>> fetchAll();

    ResponseEntity<List<T>> fetchByRange(int from, int to);

    ResponseEntity<T> fetchById(ID id);

    ResponseEntity<Response> remove(T... entities);
}
