package com.deped.service.equipment;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.items.type.Equipment;
import com.deped.repository.equipment.EquipmentRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public ResponseEntity<Equipment> create(Equipment entity) {
        Equipment savedEntity = equipmentRepository.create(entity);
        ResponseEntity<Equipment> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Equipment entity) {

        Boolean isUpdated = equipmentRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Equipment.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Equipment>> fetchAll() {
        List<Equipment> equipments = equipmentRepository.fetchAll();
        ResponseEntity<List<Equipment>> responseEntity = new ResponseEntity<>(equipments, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Equipment>> fetchByRange(Range range) {
        List<Equipment> equipments = equipmentRepository.fetchByRange(range);
        ResponseEntity<List<Equipment>> responseEntity = new ResponseEntity<>(equipments, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Equipment> fetchById(Object id) {
        Equipment equipment = equipmentRepository.fetchById(id);
        ResponseEntity<Equipment> responseEntity = new ResponseEntity<>(equipment, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Equipment... entities) {
        Boolean isRemoved = equipmentRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Equipment.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
