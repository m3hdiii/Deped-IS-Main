package com.deped.repository.category;

import com.deped.model.category.Category;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.deped.repository.utils.ConstantValues.*;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Category create(Category entity) {
        return hibernateFacade.saveEntity(Category.class, entity);
    }

    @Override
    public Boolean update(Category entity) {
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
    public Boolean remove(Category... entities) {
        return hibernateFacade.removeEntities(CATEGORY_TABLE, CATEGORY_TABLE_ID, entities);
    }
}
