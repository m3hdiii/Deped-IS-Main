package com.deped.controller.place;

import com.deped.controller.AbstractMainController;
import com.deped.model.location.City;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CityController extends AbstractMainController<City, Long> {
    private static final String BASE_NAME = "city";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_MAPPING_BY_COUNTRY_CODE = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;

    @Override
    public ModelAndView renderCreatePage(City entity) {
        return null;
    }

    @Override
    public ModelAndView createAction(City entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ModelAndView renderInfo(Long aLong) {
        return null;
    }

    @Override
    public ModelAndView renderUpdatePage(Long aLong) {
        return null;
    }

    @RequestMapping(value = RENDER_LIST_MAPPING_BY_COUNTRY_CODE, method = RequestMethod.POST)
    public @ResponseBody
    List<City> renderListPage(@PathVariable(ID_STRING_LITERAL) String countryCode) {
        ResponseEntity<List<City>> response = makeFetchRestRequestByForeignKey(BASE_NAME, countryCode, HttpMethod.POST, new ParameterizedTypeReference<List<City>>() {
        });
        List<City> list = response.getBody();
        return list;
    }

    @Override
    public ModelAndView updateAction(Long aLong, City entity, BindingResult bindingResult) {
        return null;
    }


    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = RequestMethod.GET)
    public ModelAndView renderListPage() {
        return null;
    }

    @Override
    public ModelAndView renderListPage(int from, int to) {
        return null;
    }

    @Override
    public ModelAndView removeAction(City... entity) {
        return null;
    }
}
