package com.deped.repository.places;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.location.office.Department;
import com.deped.repository.utils.ConstantValues;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_DEPARTMENTS;
import static com.deped.repository.utils.ConstantValues.FETCH_ALL_DEPARTMENT_RANGES;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Department create(Department entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Department.class, entity);
    }

    @Override
    public Boolean update(Department entity) throws DatabaseRolesViolationException {
        String sqlQuery = "UPDATE department SET department_name = :departmentName , description = :description , department_head = :departmentHead WHERE category_name = :oldDepartmentName ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("departmentName", entity.getName());
        paramMap.put("description", entity.getDescription());
        paramMap.put("departmentHead", entity.getDepartmentHead());
        paramMap.put("oldDepartmentName", entity.getPreviousIdName());

        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, Department.class, paramMap);
        return rowAffected < 0;
    }

    @Override
    public List<Department> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_DEPARTMENTS, Department.class);
    }

    @Override
    public List<Department> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_DEPARTMENT_RANGES, range, Department.class);
    }

    @Override
    public Department fetchById(String id) {
        return hibernateFacade.fetchEntityById(Department.class, id);
    }

    @Override
    public Boolean remove(Department... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.
                removeEntities(ConstantValues.DEPARTMENT_TABLE, ConstantValues.DEPARTMENT_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Department... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
