package com.deped.repository.equipment;

import com.deped.model.items.type.Equipment;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.deped.repository.utils.ConstantValues.*;

import java.util.List;

@Repository
public class EquipmentRepositoryImpl implements EquipmentRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Equipment create(Equipment entity) {
        return hibernateFacade.saveEntity(Equipment.class, entity);
    }

    @Override
    public Boolean update(Equipment entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Equipment> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_EQUIPMENTS, Equipment.class);
    }

    @Override
    public List<Equipment> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_EQUIPMENTS_RANGE, range, Equipment.class);
    }

    @Override
    public Equipment fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Equipment.class, id);
    }

    @Override
    public Boolean remove(Equipment... entities) {
        return hibernateFacade.removeEntities(EQUIPMENTS_TABLE, EQUIPMENTS_TABLE_ID, entities);
    }
}
