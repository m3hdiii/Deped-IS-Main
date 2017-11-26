package com.deped.repository.pack;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.pack.Pack;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class PackRepositoryImpl implements PackRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Pack create(Pack entity) throws DatabaseRolesViolationException {
        Pack pack = hibernateFacade.saveEntity(Pack.class, entity);
        return pack;
    }

    @Override
    public Boolean update(Pack entity) throws DatabaseRolesViolationException {
        Boolean isUpdated = hibernateFacade.updateEntity(entity);
        return isUpdated;
    }

    @Override
    public List<Pack> fetchAll() {
        List<Pack> packs = hibernateFacade.fetchAllEntity(FETCH_ALL_PACKS, Pack.class);
        return packs;
    }

    @Override
    public List<Pack> fetchByRange(Range range) {
        List<Pack> packs = hibernateFacade.fetchAllEntity(FETCH_ALL_PACKS_RANGE, range, Pack.class);
        return packs;
    }

    @Override
    public Pack fetchById(Object id) {
        Pack pack = hibernateFacade.fetchEntityById(Pack.class, id);
        return pack;
    }

    @Override
    public Boolean remove(Pack... entities) throws DatabaseRolesViolationException {
        Boolean isRemoved = hibernateFacade.removeEntities(PACK_TABLE, PACK_TABLE_ID, entities);
        return isRemoved;
    }

    @Override
    public Boolean createOrUpdateAll(Pack... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
