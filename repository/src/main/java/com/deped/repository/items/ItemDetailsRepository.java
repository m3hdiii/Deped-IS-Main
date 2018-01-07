package com.deped.repository.items;

import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface ItemDetailsRepository extends BaseRepository<ItemDetails, String> {

    List<ItemDetails> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes);

    List<CaptureInfo> fetchToBeCaptureInfo(ItemType[] itemTypes);

    List<ItemDetails> fetchAllByItemName(String itemName);
}
