package com.deped.repository.role;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.security.Role;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Role create(Role entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Role.class, entity);
    }

    @Override
    public Boolean update(Role entity) throws DatabaseRolesViolationException {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Role> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_ROLE, Role.class);
    }

    @Override
    public List<Role> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_ROLE_RANGE, range, Role.class);
    }

    @Override
    public Role fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Role.class, id);
    }

    @Override
    public Boolean remove(Role... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.removeEntities(ROLE_TABLE, ROLE_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Role... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
