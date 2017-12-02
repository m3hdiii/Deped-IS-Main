package com.deped.repository.unit;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.unit.Unit;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class UnitRepositoryImpl implements UnitRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Unit create(Unit entity) throws DatabaseRolesViolationException {
        Unit unit = hibernateFacade.saveEntity(Unit.class, entity);
        return unit;
    }

    @Override
    public Boolean update(Unit entity) throws DatabaseRolesViolationException {
        String sqlQuery = "UPDATE unit SET unit_name = :unitName , description = :description WHERE unit_name = :oldUnitName ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("unitName", entity.getName());
        paramMap.put("description", entity.getDescription());
        paramMap.put("oldUnitName", entity.getPreviousIdName());
        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, Unit.class, paramMap);
        return rowAffected < 0;
    }

    @Override
    public List<Unit> fetchAll() {
        List<Unit> units = hibernateFacade.fetchAllEntity(FETCH_ALL_UNITS, Unit.class);
        return units;
    }

    @Override
    public List<Unit> fetchByRange(Range range) {
        List<Unit> units = hibernateFacade.fetchAllEntity(FETCH_ALL_PACKS_RANGE, range, Unit.class);
        return units;
    }

    @Override
    public Unit fetchById(String id) {
        Unit unit = hibernateFacade.fetchEntityById(Unit.class, id);
        return unit;
    }

    @Override
    public Boolean remove(Unit... entities) throws DatabaseRolesViolationException {
        Boolean isRemoved = hibernateFacade.removeEntities(UNIT_TABLE, UNIT_TABLE_ID, entities);
        return isRemoved;
    }

    @Override
    public Boolean createOrUpdateAll(Unit... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
