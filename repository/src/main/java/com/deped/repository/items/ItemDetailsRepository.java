package com.deped.repository.items;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.model.search.BorrowHistorySearch;
import com.deped.model.search.BorrowSearch;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface ItemDetailsRepository extends BaseRepository<ItemDetails, String> {

    List<ItemDetails> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes);

    List<CaptureInfo> fetchToBeCaptureInfo(ItemType[] itemTypes);

    List<ItemDetails> fetchAllByItemName(String itemName);

    List<ItemDetails> itemDetailsSearch(BorrowSearch entity);

    List<BorrowItem> borrowItemSearch(BorrowHistorySearch entity);

    Boolean returnById(String s, ItemDetails itemDetails) throws DatabaseRolesViolationException;
}
