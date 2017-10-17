package com.deped.restcontroller.items;

import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.items.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemRestController extends AbstractMainRestController<Item, Long> {

    private static final String BASE_NAME = "item";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String FETCH_GOODS_MAPPING = "goods" + FETCH_PATTERN;
    private static final String FETCH_SEMI_EXPENDABLE_MAPPING = "semi-expendable" + FETCH_PATTERN;

    @Autowired
    private ItemService itemService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Item> create(@RequestBody Item entity) {
        ResponseEntity<Item> response = itemService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody Item entity) {
        ResponseEntity<Response> response = itemService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Item>> fetchAll() {
        ResponseEntity<List<Item>> response = itemService.fetchAll();
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Item>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<Item>> response = itemService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<Item> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Item> response = itemService.fetchById(aLong);
        return response;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> remove(@RequestBody Item... entities) {
        ResponseEntity<Response> response = itemService.remove(entities);
        return response;
    }

    @RequestMapping(value = FETCH_GOODS_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Item>> fetchAllGoods() {
        ResponseEntity<List<Item>> response = itemService.fetchAllGoods();
        return response;
    }


    @RequestMapping(value = FETCH_SEMI_EXPENDABLE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Item>> fetchAllSemiExpendable() {
        ResponseEntity<List<Item>> response = itemService.fetchAllSemiExpendable();
        return response;
    }
}