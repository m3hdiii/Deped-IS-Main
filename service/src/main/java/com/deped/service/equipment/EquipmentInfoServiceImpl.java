package com.deped.service.equipment;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.items.ItemDetails;
import com.deped.repository.equipment.EquipmentInfoRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentInfoServiceImpl implements EquipmentInfoService {

    @Autowired
    private EquipmentInfoRepository equipmentInfoRepository;

    @Override
    public ResponseEntity<ItemDetails> create(ItemDetails entity) {
        ItemDetails savedEntity = equipmentInfoRepository.create(entity);
        ResponseEntity<ItemDetails> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(ItemDetails entity) {
        Boolean isUpdated = equipmentInfoRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, ItemDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchAll() {
        List<ItemDetails> equipmentInfo = equipmentInfoRepository.fetchAll();
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(equipmentInfo, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ItemDetails>> fetchByRange(Range range) {
        List<ItemDetails> equipmentInfo = equipmentInfoRepository.fetchByRange(range);
        ResponseEntity<List<ItemDetails>> responseEntity = new ResponseEntity<>(equipmentInfo, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<ItemDetails> fetchById(Object id) {
        ItemDetails equipmentInfo = equipmentInfoRepository.fetchById(id);
        ResponseEntity<ItemDetails> responseEntity = new ResponseEntity<>(equipmentInfo, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(ItemDetails... entities) {
        Boolean isRemoved = equipmentInfoRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, ItemDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
