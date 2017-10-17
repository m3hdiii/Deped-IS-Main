package com.deped.controller.supply;

import com.deped.controller.AbstractMainController;
import com.deped.model.supply.Supply;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SupplyController extends AbstractMainController<Supply, Long> {

    private static final String BASE_NAME = "supply";
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
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@Valid Supply entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid Supply entity) {
        entity.setCreationDate(new Date());
        ResponseEntity<Supply> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Supply.class);
        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Supply> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Supply.class);
        Supply department = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("supplyInfo", department);
        modelMap.put("supplyId", aLong);
        return new ModelAndView(INFO_VIEW_PAGE, modelMap);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ModelAndView mv = makeHintPage(UPDATE_VIEW_PAGE, this.getClass().getCanonicalName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return mv;
    }

    @Override
    public ModelAndView updateAction(Long aLong, Supply entity) {
        return null;
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
    public ModelAndView removeAction(@Valid Supply... entity) {
        return null;
    }
}
