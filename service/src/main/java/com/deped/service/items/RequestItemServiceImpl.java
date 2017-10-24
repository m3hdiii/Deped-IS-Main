package com.deped.service.items;

import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.repository.utils.Range;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class RequestItemServiceImpl implements RequestItemService {

    @Override
    public ResponseEntity<Item> create(Item entity) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(Item entity) {
        return null;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<Item>> fetchByRange(Range range) {
        return null;
    }

    @Override
    public ResponseEntity<Item> fetchById(Object id) {
        return null;
    }

    @Override
    public ResponseEntity<Response> remove(Item... entities) {
        return null;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Item... entities) {
        return null;
    }
}
