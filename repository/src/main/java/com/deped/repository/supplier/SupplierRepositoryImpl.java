package com.deped.repository.supplier;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.supply.Supplier;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Supplier create(Supplier entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Supplier.class, entity);
    }

    @Override
    public Boolean update(Supplier entity) throws DatabaseRolesViolationException {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Supplier> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_SUPPLIER, Supplier.class);
    }

    @Override
    public List<Supplier> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_SUPPLIER_RANGE, range, Supplier.class);
    }

    @Override
    public Supplier fetchById(Long id) {
        return hibernateFacade.fetchEntityById(Supplier.class, id);
    }

    @Override
    public Boolean remove(Supplier... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.removeEntities(SUPPLIER_TABLE, SUPPLIER_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Supplier... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
