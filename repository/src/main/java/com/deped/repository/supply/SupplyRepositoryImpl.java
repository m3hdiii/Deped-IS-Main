package com.deped.repository.supply;

import com.deped.model.supply.Supply;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class SupplyRepositoryImpl implements SupplyRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Supply create(Supply entity) {
        return hibernateFacade.saveEntity(Supply.class, entity);
    }

    @Override
    public Boolean update(Supply entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Supply> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_SUPPLY, Supply.class);
    }

    @Override
    public List<Supply> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_SUPPLY_RANGE, range, Supply.class);
    }

    @Override
    public Supply fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Supply.class, id);
    }

    @Override
    public Boolean remove(Supply... entities) {
        return hibernateFacade.removeEntities(SUPPLY_TABLE, SUPPLY_TABLE_ID, entities);
    }
}
