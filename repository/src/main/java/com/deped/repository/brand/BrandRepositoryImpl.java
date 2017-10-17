package com.deped.repository.brand;

import com.deped.model.brand.Brand;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.BRAND_TABLE;
import static com.deped.repository.utils.ConstantValues.BRAND_TABLE_ID;
import static com.deped.repository.utils.ConstantValues.FETCH_ALL_BRANDS;

public class BrandRepositoryImpl implements BrandRepositoryInt {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Brand create(Brand entity) {
        return hibernateFacade.saveEntity(Brand.class, entity);
    }

    @Override
    public Boolean update(Brand entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Brand> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_BRANDS, Brand.class);
    }

    @Override
    public List<Brand> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_BRANDS, range, Brand.class);
    }

    @Override
    public Brand fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Brand.class, id);
    }

    @Override
    public Boolean remove(Brand... entities) {
        return hibernateFacade.removeEntities(BRAND_TABLE, BRAND_TABLE_ID, entities);
    }
}
