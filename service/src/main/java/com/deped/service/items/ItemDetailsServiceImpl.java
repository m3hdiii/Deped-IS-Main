package com.deped.service.items;

import com.deped.model.Response;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.repository.items.ItemDetailsRepository;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes) {
        List<ItemDetails> brands = itemDetailsRepository.fetchAllByOrderIdAndItemType(orderId, itemTypes);
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<CaptureInfo>> fetchToBeCaptureInfo(Long orderId, ItemType[] itemTypes) {
        List<CaptureInfo> brands = itemDetailsRepository.fetchToBeCaptureInfo(orderId, itemTypes);
        ResponseEntity<List<CaptureInfo>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }
}
