package com.deped.repository.user;

import com.deped.model.account.User;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private HibernateFacade hibernateFacade;
    private SessionFactory sessionFactory = hibernateFacade.getSessionFactory();

    @Override
    public User create(User entity) {
        User savedUser = hibernateFacade.saveEntity(User.class, entity);
        return savedUser;
    }

    @Override
    public Boolean update(User entity) {
        Boolean isUpdated = hibernateFacade.updateEntity(entity);
        return isUpdated;
    }

    @Override
    public List<User> fetchAll() {
        List<User> users = fetchByRange(null);
        return users;
    }

    @Override
    public List<User> fetchByRange(Range range) {
        List<User> users = hibernateFacade.fetchAllEntity(FETCH_ALL_USERS, range, User.class);
        return users;
    }

    @Override
    public User fetchById(Object entityId) {
        return hibernateFacade.fetchEntityById(User.class, entityId);
    }

    @Override
    public Boolean remove(User... entities) {
        Boolean isDeleted = hibernateFacade.removeEntities(USER_TABLE, USER_TABLE_ID, entities);
        return isDeleted;
    }

    @Override
    public User loginUser(User userInfo, LoginMethod loginMethod) {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        User result = null;
        Transaction tx = null;
        String namedQueryName = null, parameterName = null, parameterValue = null;
        final String password = userInfo.getPassword();
        try {
            tx = hibernateSession.beginTransaction();

            switch (loginMethod) {
                case EMAIL:
                    namedQueryName = "findByEmailPassword";
                    parameterName = "emailAddress";
                    parameterValue = userInfo.getEmailAddress();
                    break;
                case USERNAME:
                    namedQueryName = "findByUsernamePassword";
                    parameterName = "username";
                    parameterValue = userInfo.getUsername();
                    break;
            }

            Query<User> query = hibernateSession.createNamedQuery(namedQueryName, User.class);

            result = query.setParameter(parameterName, parameterValue)
                    .setParameter("password", password)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        } finally {
            if (hibernateSession != null)
                hibernateSession.close();
        }

        return result;
    }

    @Override
    public User fetchByUsername(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        List<User> users = hibernateFacade.fetchAllByParameterMap("fetchUser",User.class, map);
        return users.get(0);

    }
}
