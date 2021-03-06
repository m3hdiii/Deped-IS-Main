package com.deped.service.items;

import com.deped.model.Response;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.model.search.BorrowHistorySearch;
import com.deped.model.search.BorrowSearch;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemDetailsService extends BaseService<ItemDetails, String> {
    ResponseEntity<List<ItemDetails>> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes);

    ResponseEntity<List<CaptureInfo>> fetchToBeCaptureInfo(ItemType[] itemTypes);

    ResponseEntity<List<ItemDetails>> fetchAllByItemName(String itemName);

    ResponseEntity<List<ItemDetails>> itemDetailsSearch(BorrowSearch entity);

    ResponseEntity<List<BorrowItem>> borrowItemSearch(BorrowHistorySearch entity);

    ResponseEntity<Response> returnById(String s, ItemDetails itemDetails);
}
