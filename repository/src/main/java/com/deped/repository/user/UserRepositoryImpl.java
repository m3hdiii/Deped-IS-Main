package com.deped.repository.user;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.account.User;
import com.deped.model.security.PasswordResetToken;
import com.deped.model.security.TokenState;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
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
    public User create(User entity) throws DatabaseRolesViolationException {
        User savedUser = hibernateFacade.saveEntity(User.class, entity);
        return savedUser;
    }

    @Override
    public Boolean update(User entity) throws DatabaseRolesViolationException {
        StringBuilder sb = new StringBuilder("UPDATE user SET ");
        sb
                .append("username = :username , ")
                .append("password = :password , ")
                .append("account_status = :accountStatus , ")
                .append("first_name = :firstName , ")
                .append("last_name = :lastName , ")
                .append("middle_name = :middleName , ")
                .append("email_address = :emailAddress , ")
                .append("phone_no1 = :phoneNo1 , ")
                .append("phone_no2 = :phoneNo2 , ")
                .append("gender = :gender , ")
                .append("birth_date = :birthDate , ")
                .append("position = :position , ")
                .append("address = :address , ")
                .append("website = :website , ")
                .append("referrer_name = :referrerName , ")
                .append("referrer_address = :referrerAddress , ")
                .append("referrer_phone_no1 = :referrerPhone1 , ")
                .append("referrer_phone_no2 = :referrerPhone2 , ")
                .append("section_name = :sectionName , ")
                .append("city_of_born = :cityOfBorn , ")
                .append("pic_url = :picUrl ")
                .append("WHERE username = :previousUsername");
        String sqlQuery = sb.toString();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", entity.getUsername());
        paramMap.put("password", entity.getPassword());
        paramMap.put("accountStatus", entity.getAccountStatus() != null ? entity.getAccountStatus().toString() : null);
        paramMap.put("firstName", entity.getFirstName());
        paramMap.put("lastName", entity.getLastName());
        paramMap.put("middleName", entity.getMiddleName());
        paramMap.put("emailAddress", entity.getEmailAddress());
        paramMap.put("phoneNo1", entity.getPhoneNo1());
        paramMap.put("phoneNo2", entity.getPhoneNo2());
        paramMap.put("gender", entity.getGender() != null ? entity.getGender().toString() : null);
        paramMap.put("birthDate", entity.getBirthDate());
        paramMap.put("position", entity.getPosition() != null ? entity.getPosition().toString() : null);
        paramMap.put("address", entity.getAddress());
        paramMap.put("website", entity.getWebsite());
        paramMap.put("referrerName", entity.getReferrerName());
        paramMap.put("referrerAddress", entity.getReferrerAddress());
        paramMap.put("referrerPhone1", entity.getReferrerPhoneNo1());
        paramMap.put("referrerPhone2", entity.getReferrerPhoneNo2());
        paramMap.put("sectionName", entity.getSection() != null ? entity.getSection().getName() : null);
        paramMap.put("cityOfBorn", entity.getCityOfBorn() != null ? entity.getCityOfBorn().getCityId() : null);
        paramMap.put("picUrl", entity.getPicUrl());
        paramMap.put("previousUsername", entity.getPreviousIdUsername());

        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, User.class, paramMap);
        return rowAffected < 0;
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
    public User fetchById(String entityId) {
        return hibernateFacade.fetchEntityById(User.class, entityId);
    }

    @Override
    public Boolean remove(User... entities) throws DatabaseRolesViolationException {
        Boolean isDeleted = hibernateFacade.removeEntities(USER_TABLE, USER_TABLE_ID, entities);
        return isDeleted;
    }

    @Override
    public Boolean createOrUpdateAll(User... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public User loginUser(User userInfo, LoginMethod loginMethod) {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionFactory.getCurrentSession();
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
        }

        return result;
    }

    @Override
    public User fetchByUsername(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        List<User> users = hibernateFacade.fetchAllByParameterMap("fetchUser", User.class, map);
        return users.get(0);

    }

    @Override
    public User fetchByEmail(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("emailAddress", email);
        List<User> users = hibernateFacade.fetchAllByParameterMap("fetchUserByEmail", User.class, map);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public PasswordResetToken createPasswordResetTokenForUser(PasswordResetToken passwordResetToken) throws DatabaseRolesViolationException {
        PasswordResetToken entity = hibernateFacade.saveEntity(PasswordResetToken.class, passwordResetToken);
        return entity;
    }

    @Override
    public Boolean removePasswordResetToken(PasswordResetToken passwordResetToken) {
        return hibernateFacade.removeEntities("password_reset_token", "id", passwordResetToken);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
//        String query = "SELECT * FROM password_reset_token WHERE token = ':token' AND NOW() BETWEEN expiry_date AND (expiry_date + INTERVAL :period DAY)";
        String query = "SELECT * FROM password_reset_token WHERE token = :token";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("token", token);
        List<PasswordResetToken> result = hibernateFacade.fetchAllEntityBySqlQuery(query, null, PasswordResetToken.class, parameterMap);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public boolean changePasswordByToken(String username, String token, String newPassword, int period) throws DatabaseRolesViolationException {
        Session hibernateSession = null;
        try {
            hibernateSession = sessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;

        try {
            tx = hibernateSession.beginTransaction();


            String userPasswordUpdateQuery = "UPDATE `user` SET password = :pass WHERE user_name = :username";
            NativeQuery<User> userUpdate = hibernateSession.createNativeQuery(userPasswordUpdateQuery, User.class);
            userUpdate.setParameter("pass", newPassword);
            userUpdate.setParameter("username", username);
            int userRowNoAffected = userUpdate.executeUpdate();

            String resetTokenUpdateQuery = "UPDATE password_reset_token SET token_state = :newTokenState WHERE username = :username AND token = :token AND token_state = :currentTokenState AND NOW() BETWEEN creation_date AND (creation_date + INTERVAL 3 DAY)";
            NativeQuery<PasswordResetToken> passwordResetTokenUpdate = hibernateSession.createNativeQuery(resetTokenUpdateQuery, PasswordResetToken.class);
            passwordResetTokenUpdate.setParameter("username", username);
            passwordResetTokenUpdate.setParameter("token", token);
            passwordResetTokenUpdate.setParameter("currentTokenState", TokenState.AVAILABLE.toString());
            passwordResetTokenUpdate.setParameter("newTokenState", TokenState.USED.toString());
            int passwordResetTokenRowAffected = passwordResetTokenUpdate.executeUpdate();

            if (passwordResetTokenRowAffected != 1 || userRowNoAffected != 1) {
                throw new Exception("Password update has encountered a problem");
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        }

        return true;
    }
}
