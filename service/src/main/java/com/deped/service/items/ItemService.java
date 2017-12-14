package com.deped.service.items;

import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService extends BaseService<Item, String> {

    ResponseEntity<List<Item>> fetchAllByItemType(ItemType itemType);
}
