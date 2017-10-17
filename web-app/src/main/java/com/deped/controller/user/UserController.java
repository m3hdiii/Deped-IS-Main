package com.deped.controller.user;

import com.deped.controller.AbstractMainController;
import com.deped.log.injector.FancyLogger;
import com.deped.model.Response;
import com.deped.model.account.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mehdi on 7/7/17.
 */

@Controller
public class UserController extends AbstractMainController<User, Long> {

    @FancyLogger
    private Logger log;

    private static final String BASE_NAME = "user";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;

    @Override
    @RequestMapping(value = {CREATE_MAPPING}, method = GET)
    public ModelAndView renderCreatePage(@Valid User entity) {
        return new ModelAndView(CREATE_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid User entity) {
        makeCreateRestRequest(entity, REST_CONTEXT_NAME + CREATE_MAPPING, HttpMethod.POST, User.class);
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<User> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, User.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<User> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, User.class);
        User item = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, item);
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, User entity) {
        entity.setUserId(aLong);
        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST);
        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ModelAndView mv = makeHintPage(LIST_VIEW_PAGE, this.getClass().getCanonicalName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid User... entity) {
        return null;
    }
}
