package com.deped.service.items;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.repository.items.ItemRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    private static final String BASE_FILE_FOLDER = "items";


    @Override
    public ResponseEntity<Item> create(Item entity) {
        String pictureBase64 = entity.getPictureBase64();

        String fileName = ServiceUtils.saveImageIntoDisk(pictureBase64, BASE_FILE_FOLDER);
        entity.setPicName(fileName);

        Item savedEntity = null;
        try {
            savedEntity = itemRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Item> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Item entity) {
        String pictureBase64 = entity.getPictureBase64();

        String fileName = ServiceUtils.saveImageIntoDisk(pictureBase64, BASE_FILE_FOLDER);
        entity.setPicName(fileName);

        Boolean isUpdated = null;
        try {
            isUpdated = itemRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Item.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAll() {
        List<Item> items = itemRepository.fetchAll();
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchByRange(Range range) {
        List<Item> items = itemRepository.fetchByRange(range);
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Item> fetchById(String id) {
        Item item = itemRepository.fetchById(id);
        ResponseEntity<Item> responseEntity = new ResponseEntity<>(item, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Item... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = itemRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Item.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Item... entities) {
        return null;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAllGoods() {
        List<Item> items = itemRepository.fetchAllGoods();
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAllSemiExpendable() {
        List<Item> items = itemRepository.fetchAllSemiExpendable();
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }
}
