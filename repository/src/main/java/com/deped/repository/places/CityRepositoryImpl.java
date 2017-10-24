package com.deped.repository.places;

import com.deped.model.location.City;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class CityRepositoryImpl implements CityRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public City create(City entity) {
        return hibernateFacade.saveEntity(City.class, entity);
    }

    @Override
    public Boolean update(City entity) {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<City> fetchAll() {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_CITIES, City.class);
    }

    @Override
    public List<City> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_CITIES_RANGES, range, City.class);
    }

    @Override
    public City fetchById(Object id) {
        return hibernateFacade.fetchEntityById(City.class, id);
    }

    @Override
    public Boolean remove(City... entities) {
        return hibernateFacade.
                removeEntities(CITY_TABLE, CITY_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(City... entities) {
        return null;
    }

    @Override
    public List<City> fetchAllByCountryCode(String countryCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("countryCode", countryCode);
        List<City> list = hibernateFacade.fetchAllByParameterMap(FETCH_ALL_CITIES_BY_COUNTRY_CODE, City.class, map);
        return list;
    }
}
