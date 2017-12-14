package com.deped.restcontroller.items;

import com.deped.model.Response;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
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
    private static final String FETCH_BY_STATES = FETCH_MAPPING + URL_SEPARATOR + "by-states";
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String UPDATE_STATE_MAPPING = BASE_NAME + "/update-state/user" + FETCH_BY_ID_PATTERN + "/state/{state}";


    @Autowired
    private ItemDetailsService itemDetailsService;

    @Override
    public ResponseEntity<ItemDetails> create(ItemDetails entity) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(ItemDetails entity) {
        return null;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchByRange(int from, int to) {
        return null;
    }

    @Override
    public ResponseEntity<ItemDetails> fetchById(String s) {
        return null;
    }

    @Override
    public ResponseEntity<Response> remove(ItemDetails... entities) {
        return null;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(ItemDetails... entities) {
        return null;
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

    @RequestMapping(value = "item-details/capture-info/{id}", method = RequestMethod.POST)
    public ResponseEntity<List<CaptureInfo>> fetchToBeCaptureInfo(@PathVariable(ID_STRING_LITERAL) Long id, @RequestBody Integer... itemTypeOrdinals) {
        ItemType[] itemTypes = new ItemType[itemTypeOrdinals.length];
        for (int i = 0; i < itemTypeOrdinals.length; i++) {
            itemTypes[i] = ItemType.values()[itemTypeOrdinals[i]];
        }
        ResponseEntity<List<CaptureInfo>> response = itemDetailsService.fetchToBeCaptureInfo(id, itemTypes);
        return response;

    }
}