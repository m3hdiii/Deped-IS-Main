package com.deped.controller.login;

import com.deped.controller.ConstantController;
import com.deped.model.Response;
import com.deped.model.ResponseStatus;
import com.deped.model.account.User;
import com.deped.utils.NetworkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginController {

    private static final String LOGIN = "login";
    private static final String HOME = "home";
    private static final String INDEX = "index";
    private static final String MAIN = "main";
    private static final String FORGOT_PASSWORD_PAGE = "center/forgot-password";
    private static final String CHANGE_PASSWORD_PAGE = "center/change-password";
    private static final String FORBIDDEN_PAGE = "center/forbidden";

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"", HOME, INDEX, MAIN, LOGIN}, method = RequestMethod.GET)
    public String renderLogin(@ModelAttribute("user") User user) {
        return "center/login";
    }

    @RequestMapping(value = "forgot-password", method = RequestMethod.GET)
    public ModelAndView forgotPassword(@ModelAttribute("user") User user) {
        ModelAndView mav = new ModelAndView(FORGOT_PASSWORD_PAGE, "user", user);
        return mav;
    }

    @RequestMapping(value = "forgot-password", method = RequestMethod.POST)
    public ModelAndView forgotPasswordAction(@ModelAttribute("user") User user, BindingResult result) {
        addValidation(user, result);
        Map<String, Object> modelMap = new HashMap<>();
        ModelAndView mav = new ModelAndView(FORGOT_PASSWORD_PAGE);

        String baseUrl = getBaseUrl();
        if (baseUrl == null) {
            result.addError(new FieldError(User.class.getSimpleName(), "undefined", "Server address problem!"));
        }

        if (result.hasErrors()) {
            modelMap.put("user", user);
            mav.addAllObjects(modelMap);
            return mav;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] variables = {getBaseUrl(), user.getEmailAddress()};
        HttpEntity<String[]> httpEntity = new HttpEntity<>(variables, headers);

        String restUrl = ConstantController.BASE_URL + "user/reset-password/";
        ResponseEntity<Response> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, Response.class);

        Response responseObject = response.getBody();
        if (responseObject == null || responseObject.getResponseStatus() == ResponseStatus.FAILED) {
            result.addError(new FieldError(User.class.getSimpleName(), "undefined", "Something has gone wrong. Contact your administrator!"));
            modelMap.put("user", user);
            mav.addAllObjects(modelMap);
            return mav;
        }

        modelMap.put("success", "Please check your Email in order to recover your password");
        modelMap.put("user", user);
        mav.addAllObjects(modelMap);
        return mav;
    }


    @RequestMapping(value = "change-password", method = RequestMethod.GET)
    public ModelAndView changePasswordPage(@RequestParam("id") Long userId, @RequestParam("token") String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] variables = {String.valueOf(userId), token};
        HttpEntity<String[]> httpEntity = new HttpEntity<>(variables, headers);

        String restUrl = ConstantController.BASE_URL + "user/check-token/";
        ResponseEntity<Response> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, Response.class);

        Response responseObject = response.getBody();
        ResponseStatus status = responseObject.getResponseStatus();

        Map<String, Object> modelMap = new HashMap<>();

        if (status == ResponseStatus.FAILED) {
            modelMap.put("failure", responseObject.getResponseMessage());
            modelMap.put("returnName", "Go Back!");
            modelMap.put("returnUrl", "/");
            return new ModelAndView(FORBIDDEN_PAGE, modelMap);

        } else {
            modelMap.put("user", new User());
            ModelAndView mav = new ModelAndView(CHANGE_PASSWORD_PAGE, modelMap);
            return mav;
        }

    }

    @RequestMapping(value = "change-password", method = RequestMethod.POST)
    public ModelAndView changePasswordPageAction(@RequestParam("id") Long userId, @RequestParam("token") String token, @ModelAttribute("user") User user, BindingResult result) {

        addValidationPasswordMach(user, result);
        Map<String, Object> modelMap = new HashMap<>();
        if (result.hasErrors()) {
            modelMap.put("user", user);
            ModelAndView mav = new ModelAndView(CHANGE_PASSWORD_PAGE, modelMap);
            return mav;
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] variables = {String.valueOf(userId), token, user.getPassword()};
        HttpEntity<String[]> httpEntity = new HttpEntity<>(variables, headers);

        String restUrl = ConstantController.BASE_URL + "user/change-password/";
        ResponseEntity<Response> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, Response.class);

        Response responseObject = response.getBody();
        if (responseObject.getResponseStatus() == ResponseStatus.FAILED) {
            result.addError(new FieldError(User.class.getSimpleName(), "password", responseObject.getResponseMessage()));
            modelMap.put("user", user);
            ModelAndView mav = new ModelAndView(CHANGE_PASSWORD_PAGE, modelMap);
            return mav;
        }

        modelMap.clear();
        ModelAndView mav = new ModelAndView("redirect:/?pcs=your password successfully changed");
        return mav;

    }

    private void addValidationPasswordMach(User user, BindingResult result) {
        String password = user.getPassword();
        String rePassword = user.getRepassword();
        if (password == null || rePassword == null || password.isEmpty() || rePassword.isEmpty()) {
            result.addError(new FieldError(User.class.getSimpleName(), "password", "Your password must not be blank"));
            return;
        }

        if (!password.equals(rePassword)) {
            result.addError(new FieldError(User.class.getSimpleName(), "password", "Your passwords does not match"));
            return;
        }
    }

    private void addValidation(User user, BindingResult result) {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        if (!isInternetConnected()) {
            result.addError(new FieldError(User.class.getSimpleName(), "undefined", "Presently there is not available internet within the organization, Please try later!"));
            return;
        }

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "emailAddress");

        for (ConstraintViolation<User> violation : violations) {
            String message = violation.getMessage();
            result.addError(new FieldError(User.class.getSimpleName(), "emailAddress", message));
            return;
        }
    }

    private boolean isInternetConnected() {
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("google.com", 80);
        try {
            sock.connect(address, 10000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(new LoginController().getBaseUrl());
    }

    public String getBaseUrl() {
        InetAddress network = null;
        try {
            network = NetworkUtils.getLocalHostLANAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String host = network.getHostAddress();

        if (host == null) {
            return null;
        }


        String formatUrl = "%s://%s%s%s";
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String url = String.format(formatUrl, request.getScheme(), host, serverPort, request.getContextPath());
        return url;
    }


}
