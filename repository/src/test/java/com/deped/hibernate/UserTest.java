package com.deped.hibernate;

import com.deped.model.account.User;
import com.deped.repository.utils.HibernateFacade;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

import java.util.List;


public class UserTest {

    SessionFactory hibernateSessionFactory;
    Session session = null;

    //    @Test
    public void UserTransactionsTest() {
        try {
            hibernateSessionFactory = HibernateFacade.getSessionFactory();
            session = hibernateSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String username = "mehdi";
        String password = null;
        String firstName = "mehdi";
        String lastName = null;


        String queryStr = "SELCT * FROM user WHERE (username = :username OR :username IS NULL) AND (password = :password OR :password IS NULL) AND (firstName = :firstName OR :firstName IS NULL) AND (lastName = :lastName OR :lastName IS NULL)";
        NativeQuery query = session.createNativeQuery(queryStr);


        List s = query.list();

        User user = null;/*new User("mehdi", "123", AccountStatus.NOT_ACTIVE, "Mehdi", "Afsari",
                null, "mahdi.afsari@gmail.com", "09062658383", "09206110990", Gender.MALE,
                new Date(), new Date(), Position.HEAD, "Niroo Havayi, Tehran", "http://www.google.com",
                null, new Date(), "Amir Afsari", "Niroo Havayi Tehran", "09195541040", null, new Section("Develop", "Developer Section", new Department(), new Date()), null, null, (short) 5, 20, null, null, new City(), new Country());
*/


//        assertTrue();
//        assertTrue(updateUser());
//        assertTrue(saveUser());
//        assertTrue(saveUser());


    }

    private boolean saveUser() {

        return false;
    }

    private boolean deleteUser(User user) {

        return false;
    }

    private boolean editUser(User user) {

        return false;
    }

    private boolean deleteUser(Long userId) {

        return false;
    }


    private User fetchUser(Long userId) {

        return null;
    }
}
