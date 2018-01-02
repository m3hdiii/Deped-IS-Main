package com.deped.repository.places;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.location.Country;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Session session;
        Transaction tr = null;
        List<Country> list = null;

        try {
            session = factory.getCurrentSession();
            tr = session.beginTransaction();

            NativeQuery<Country> nativeQuery = session.createNativeQuery("SELECT * FROM country", Country.class);
            list = nativeQuery.list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public List<Country> fetchByRange(Range range) {
        return hibernateFacade.fetchAllEntity(FETCH_ALL_COUNTRIES_RANGES, range, Country.class);
    }

    @Override
    public Country fetchById(String id) {
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
