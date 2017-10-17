package com.deped.service.items;

import com.deped.model.items.Item;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService extends BaseService<Item> {

    ResponseEntity<List<Item>> fetchAllGoods();

    ResponseEntity<List<Item>> fetchAllSemiExpendable();
}
