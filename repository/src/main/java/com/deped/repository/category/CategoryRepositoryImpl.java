package com.deped.repository.category;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.category.Category;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return hibernateFacade.updateEntity(entity);
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
    public Category fetchById(Object id) {
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
