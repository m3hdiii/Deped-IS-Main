package com.deped.repository.category;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.category.Category;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Category create(Category entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Category.class, entity);
    }

    @Override
    public Boolean update(Category entity) throws DatabaseRolesViolationException {
        String sqlQuery = "UPDATE category SET category_name = :categoryName , description = :description WHERE category_name = :oldCategoryName ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("categoryName", entity.getName());
        paramMap.put("description", entity.getDescription());
        paramMap.put("oldCategoryName", entity.getPreviousIdName());

        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, Category.class, paramMap);
        return rowAffected < 0;
    }

    @Override
    public List<Category> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_CATEGORY, Category.class);
    }

    @Override
    public List<Category> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_CATEGORY_RANGE, range, Category.class);
    }

    @Override
    public Category fetchById(String id) {
        return hibernateFacade.fetchEntityById(Category.class, id);
    }

    @Override
    public Boolean remove(Category... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.removeEntities(CATEGORY_TABLE, CATEGORY_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Category... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
