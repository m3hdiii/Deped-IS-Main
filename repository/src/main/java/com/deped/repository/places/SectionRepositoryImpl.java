package com.deped.repository.places;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.location.office.Department;
import com.deped.model.location.office.Section;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class SectionRepositoryImpl implements SectionRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Section create(Section entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Section.class, entity);
    }

    @Override
    public Boolean update(Section entity) throws DatabaseRolesViolationException {
        String sqlQuery = "UPDATE section SET section_name = :sectionName , description = :description , department_name = :departmentName WHERE category_name = :oldSectionName ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("section_name", entity.getName());
        paramMap.put("description", entity.getDescription());
        paramMap.put("departmentName", entity.getDepartment() != null ? entity.getDepartment().getName() : null);
        paramMap.put("oldSectionName", entity.getPreviousIdName());

        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, Department.class, paramMap);
        return rowAffected < 0;
    }

    @Override
    public List<Section> fetchAll() {
        List<Section> sections = hibernateFacade.fetchAllEntity(FETCH_ALL_SECTION, Section.class);
        return sections;
    }


    @Override
    public List<Section> fetchByRange(Range range) {
        List<Section> sections = hibernateFacade.fetchAllEntity(FETCH_ALL_SECTION_RANGE, range, Section.class);
        return sections;
    }

    @Override
    public Section fetchById(String id) {
        Section section


                = hibernateFacade.fetchEntityById(Section.class, id);
        return section;
    }

    @Override
    public Boolean remove(Section... entities) {
        Boolean isDeleted = hibernateFacade.removeEntities(SECTION_TABLE, SECTION_TABLE_ID, entities);
        return isDeleted;
    }

    @Override
    public Boolean createOrUpdateAll(Section... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public List<Section> fetchAllByDepartment(String departmentName) {
        String sqlQuery = "SELECT * FROM section WHERE department_name = :departmentName";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("departmentName", departmentName);
        List<Section> list = hibernateFacade.fetchAllEntityBySqlQuery(sqlQuery, null, Section.class, parameterMap);
        return list;
    }
}
