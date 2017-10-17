package com.deped;

import static com.deped.controller.ConstantController.*;
import com.deped.controller.user.UserController;
import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.repository.utils.Range;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class Client {

    String BASE_NAME = "user";
    private static final List<User> USERS = new ArrayList<>();

    static {
        User user = new User();
        user.setUserId(4L);
        user.setUsername("Jooje");
        user.setPassword("123");
        user.setFirstName("Mehdi Star");
        user.setLastName("Mark Richards");
        user.setEmailAddress("ix67@yahoo.com");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("Jooje2");
        user2.setPassword("12344");
        user2.setFirstName("Mehdi Star3w");
        user2.setLastName("Mark Richardsw");
        user2.setEmailAddress("ix637@yahoo.com");

        USERS.add(user);
        USERS.add(user2);

    }

    public static void main(String[] args) {
        makeCreateRestRequest(BASE_URL, USERS);
        makeUpdateRestRequest(BASE_URL, USERS);
        makeFetchAllRestRequest(FETCH_URL);
        makeFetchAllByRangeRestRequest(BASE_URL, new Range(1, 10));
    }

    private static void makeCreateRestRequest(String baseName, List<User> users) {
        for (User user : users) {
            ResponseEntity<User> result = new UserController().makeCreateRestRequest(user, baseName, HttpMethod.POST, User.class);
            System.out.println(result.getStatusCode().toString());
            System.out.println(String.format("user-id: %d and username: %s", result.getBody().getUserId(), result.getBody().getUsername()));
        }
    }

    private static void makeUpdateRestRequest(String baseName, List<User> users) {
        for (User user : users) {
            ResponseEntity<Response> result = new UserController().makeUpdateRestRequest(user, baseName, HttpMethod.POST);
            System.out.println(result.getStatusCode().toString());
            System.out.println(result.getBody().getResponseStatus().toString());
        }
    }

    private static void makeFetchAllRestRequest(String baseNam) {

        ResponseEntity<List<User>> result = new UserController().makeFetchAllRestRequest(baseNam, HttpMethod.POST, new ParameterizedTypeReference<List<User>>() {
        });
        System.out.println(result.getStatusCode());
        for (User user : result.getBody()) {
            System.out.println(result.getStatusCode().toString());
            System.out.println(String.format("user-id: %d and username: %s", user.getUserId(), user.getUsername()));
        }
    }

    private static void makeFetchAllByRangeRestRequest(String baseName, Range range) {
        ResponseEntity<List<User>> result = new UserController().makeFetchByRangeRestRequest(baseName, HttpMethod.POST, range, new ParameterizedTypeReference<List<User>>() {
        });
        System.out.println(result.getStatusCode());
        for (User user : result.getBody()) {
            System.out.println(result.getStatusCode().toString());
            System.out.println(String.format("user-id: %d and username: %s", user.getUserId(), user.getUsername()));
        }
    }
}
