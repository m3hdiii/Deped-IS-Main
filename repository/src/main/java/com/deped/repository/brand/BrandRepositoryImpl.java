package com.deped.repository.brand;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.brand.Brand;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class BrandRepositoryImpl implements BrandRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Brand create(Brand entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Brand.class, entity);
    }

    @Override
    public Boolean update(Brand entity) throws DatabaseRolesViolationException {
        String sqlQuery = "UPDATE brand SET brand_name = :brandName , description = :description , logo_url = :logoUrl WHERE brand_name = :previousBrandName ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("brandName", entity.getName());
        paramMap.put("description", entity.getDescription());
        paramMap.put("logoUrl", entity.getLogoUrl());
        paramMap.put("previousBrandName", entity.getPreviousIdName());

        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, Brand.class, paramMap);
        return rowAffected < 0;
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
    public Brand fetchById(String id) {
        return hibernateFacade.fetchEntityById(Brand.class, id);
    }

    @Override
    public Boolean remove(Brand... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.removeEntities(BRAND_TABLE, BRAND_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Brand... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
