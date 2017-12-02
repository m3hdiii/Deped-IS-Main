package com.deped.repository.items;

import com.deped.model.items.Item;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface ItemRepository extends BaseRepository<Item, String> {
    List<Item> fetchAllGoods();

    List<Item> fetchAllSemiExpendable();
}
