package com.deped.repository.equipment;

import com.deped.model.items.ItemDetails;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.deped.repository.utils.ConstantValues.*;

import java.util.List;

@Repository
public class EquipmentInfoRepositoryImpl implements EquipmentInfoRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public ItemDetails create(ItemDetails entity) {
        return hibernateFacade.saveEntity(ItemDetails.class, entity);
    }

    @Override
    public Boolean update(ItemDetails entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<ItemDetails> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_EQUIPMENT_INFO, ItemDetails.class);
    }

    @Override
    public List<ItemDetails> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_EQUIPMENT_INFO_RANGE, range, ItemDetails.class);
    }

    @Override
    public ItemDetails fetchById(Object id) {
        return hibernateFacade.fetchEntityById(ItemDetails.class, id);
    }

    @Override
    public Boolean remove(ItemDetails... entities) {
        return hibernateFacade.removeEntities(EQUIPMENT_INFO_TABLE, EQUIPMENT_INFO_TABLE_ID, entities);
    }
}
