package com.deped.service.items;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.model.search.BorrowHistorySearch;
import com.deped.model.search.BorrowSearch;
import com.deped.repository.items.ItemDetailsRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemDetailsServiceImpl implements ItemDetailsService {

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

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
        List<ItemDetails> entities = itemDetailsRepository.fetchAll();
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(entities, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchByRange(Range range) {
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
        Boolean isCreated = null;
        try {
            isCreated = itemDetailsRepository.createOrUpdateAll(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, ItemDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes) {
        List<ItemDetails> brands = itemDetailsRepository.fetchAllByOrderIdAndItemType(orderId, itemTypes);
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<CaptureInfo>> fetchToBeCaptureInfo(ItemType[] itemTypes) {
        List<CaptureInfo> brands = itemDetailsRepository.fetchToBeCaptureInfo(itemTypes);
        ResponseEntity<List<CaptureInfo>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchAllByItemName(String itemName) {
        List<ItemDetails> brands = itemDetailsRepository.fetchAllByItemName(itemName);
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> itemDetailsSearch(BorrowSearch entity) {
        List<ItemDetails> requests = itemDetailsRepository.itemDetailsSearch(entity);
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<BorrowItem>> borrowItemSearch(BorrowHistorySearch entity) {
        List<BorrowItem> requests = itemDetailsRepository.borrowItemSearch(entity);
        ResponseEntity<List<BorrowItem>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }
}
