package com.deped.repository.places;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.location.Country;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class CountryRepositoryImpl implements CountryRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Country create(Country entity) throws DatabaseRolesViolationException {
        return hibernateFacade.saveEntity(Country.class, entity);
    }

    @Override
    public Boolean update(Country entity) throws DatabaseRolesViolationException {
        return hibernateFacade.updateEntity(entity);
    }

    @Override
    public List<Country> fetchAll() {
        SessionFactory factory = HibernateFacade.getSessionFactory();
        Session session = factory.openSession();
        NativeQuery<Country> nativeQuery = session.createNativeQuery("SELECT * FROM country", Country.class);
        return nativeQuery.list();

//        return hibernateFacade.fetchAllEntity(FETCH_ALL_COUNTRIES, Country.class);
    }

    @Override
    public List<Country> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_COUNTRIES_RANGES, range, Country.class);
    }

    @Override
    public Country fetchById(Object id) {
        return hibernateFacade.fetchEntityById(Country.class, id);
    }

    @Override
    public Boolean remove(Country... entities) throws DatabaseRolesViolationException {
        return hibernateFacade.
                removeEntities(COUNTRY_TABLE, COUNTRY_TABLE_ID, entities);
    }

    @Override
    public Boolean createOrUpdateAll(Country... entities) throws DatabaseRolesViolationException {
        return null;
    }
}
