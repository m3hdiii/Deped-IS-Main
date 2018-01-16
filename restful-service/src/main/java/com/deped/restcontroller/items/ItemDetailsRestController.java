package com.deped.restcontroller.items;

import com.deped.model.Response;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.model.search.BorrowHistorySearch;
import com.deped.model.search.BorrowSearch;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.items.ItemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemDetailsRestController extends AbstractMainRestController<ItemDetails, String> {
    private static final String BASE_NAME = "item-details";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String CREATE_ALL_MAPPING = BASE_NAME + CREATE_ALL_PATTERN;
    private static final String ORDER_ALL_MAPPING = BASE_NAME + URL_SEPARATOR + "order-all";
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_ORDER_ID = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_MAPPING_BY_ORDER_ID_AND_ITEM_TYPE = BASE_NAME + "/find-by-order-type" + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_ITEM_NAME_MAPPING = BASE_NAME + "/fetch-by-item-name" + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_STATES = FETCH_MAPPING + URL_SEPARATOR + "by-states";
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String UPDATE_STATE_MAPPING = BASE_NAME + "/update-state/user" + FETCH_BY_ID_PATTERN + "/state/{state}";
    private static final String SEARCH_REQUEST_MAPPING = BASE_NAME + URL_SEPARATOR + "search-list";
    private static final String HISTORY_SEARCH_REQUEST_MAPPING = BASE_NAME + URL_SEPARATOR + "history-search-list";

    @Autowired
    private ItemDetailsService itemDetailsService;

    @Override
    public ResponseEntity<ItemDetails> create(ItemDetails entity) {
        return null;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(ItemDetails entity) {
        ResponseEntity<Response> response = itemDetailsService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<ItemDetails>> fetchAll() {
        ResponseEntity<List<ItemDetails>> response = itemDetailsService.fetchAll();
        return response;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchByRange(int from, int to) {
        return null;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<ItemDetails> fetchById(@PathVariable(ID_STRING_LITERAL) String s) {
        ResponseEntity<ItemDetails> response = itemDetailsService.fetchById(s);
        return response;
    }

    @RequestMapping(value = FETCH_BY_ITEM_NAME_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<ItemDetails>> fetchByItemId(@PathVariable(ID_STRING_LITERAL) String itemId) {
        ResponseEntity<List<ItemDetails>> response = itemDetailsService.fetchAllByItemName(itemId);
        return response;
    }

    @Override
    public ResponseEntity<Response> remove(ItemDetails... entities) {
        return null;
    }

    @Override
    @RequestMapping(value = CREATE_ALL_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> createOrUpdateAll(@RequestBody ItemDetails... entities) {
        ResponseEntity<Response> response = itemDetailsService.createOrUpdateAll(entities);
        return response;
    }

    @RequestMapping(value = FETCH_MAPPING_BY_ORDER_ID_AND_ITEM_TYPE, method = RequestMethod.POST)
    public ResponseEntity<List<ItemDetails>> fetchAllByOrderIdAndItemType(@PathVariable(ID_STRING_LITERAL) Long id, @RequestBody Integer... itemTypeOrdinals) {
        ItemType[] itemTypes = new ItemType[itemTypeOrdinals.length];
        for (int i = 0; i < itemTypeOrdinals.length; i++) {
            itemTypes[i] = ItemType.values()[itemTypeOrdinals[i]];
        }
        ResponseEntity<List<ItemDetails>> response = itemDetailsService.fetchAllByOrderIdAndItemType(id, itemTypes);
        return response;
    }

    @RequestMapping(value = "item-details/capture-info", method = RequestMethod.POST)
    public ResponseEntity<List<CaptureInfo>> fetchToBeCaptureInfo(@RequestBody Integer... itemTypeOrdinals) {
        ItemType[] itemTypes = new ItemType[itemTypeOrdinals.length];
        for (int i = 0; i < itemTypeOrdinals.length; i++) {
            itemTypes[i] = ItemType.values()[itemTypeOrdinals[i]];
        }
        ResponseEntity<List<CaptureInfo>> response = itemDetailsService.fetchToBeCaptureInfo(itemTypes);
        return response;

    }

    @RequestMapping(value = SEARCH_REQUEST_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<ItemDetails>> fetchRequestSearch(@RequestBody BorrowSearch entity) {
        ResponseEntity<List<ItemDetails>> requestList = itemDetailsService.itemDetailsSearch(entity);
        return requestList;
    }

    @RequestMapping(value = HISTORY_SEARCH_REQUEST_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<BorrowItem>> fetchHistoryRequestSearch(@RequestBody BorrowHistorySearch entity) {
        ResponseEntity<List<BorrowItem>> requestList = itemDetailsService.borrowItemSearch(entity);
        return requestList;
    }
}
