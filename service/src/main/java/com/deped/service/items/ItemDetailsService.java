package com.deped.service.items;

import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemDetailsService extends BaseService<ItemDetails, String> {
    ResponseEntity<List<ItemDetails>> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes);

    ResponseEntity<List<CaptureInfo>> fetchToBeCaptureInfo(ItemType[] itemTypes);
}
