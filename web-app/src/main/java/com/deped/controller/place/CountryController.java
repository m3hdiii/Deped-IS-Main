package com.deped.controller.place;

import com.deped.controller.AbstractMainController;
import com.deped.model.location.Country;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CountryController extends AbstractMainController<Country, String> {

    private static final String BASE_NAME = "country";
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
    public ModelAndView renderCreatePage(Country entity) {
        return null;
    }

    @Override
    public ModelAndView createAction(Country entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ModelAndView renderInfo(String s) {
        return null;
    }

    @Override
    public ModelAndView renderUpdatePage(String s) {
        return null;
    }

    @Override
    public ModelAndView updateAction(String s, Country entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ModelAndView renderListPage() {
        return null;
    }

    @Override
    public ModelAndView renderListPage(int from, int to) {
        return null;
    }

    @Override
    public ModelAndView removeAction(Country... entity) {
        return null;
    }

    @RequestMapping(value = RENDER_LIST_MAPPING, method = POST)
    public List<Country> renderListPageJson() {
        ResponseEntity<List<Country>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Country>>() {
        });
        List<Country> list = response.getBody();
        return list;
    }
}
