package com.deped.controller.unit;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.model.unit.Unit;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UnitController extends AbstractMainController<Unit, String> {

    private static final String BASE_NAME = "unit";
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
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Unit entity) {
        List<Item> items = SharedData.getItems(false);
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put(BASE_NAME, entity);
        modelMap.put("items", items);
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid @ModelAttribute(BASE_NAME) Unit entity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            HashMap<String, Object> modelMap = new HashMap<>();
            modelMap.put(BASE_NAME, entity);
//            List<Item> items = SharedData.getItems(false);
//            modelMap.put("items", items);
            ModelAndView mav = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
            return mav;
        }
        entity.setCreationDate(new Date());
        ResponseEntity<Unit> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Unit.class);
        ModelAndView mv = postCreateProcessing(Unit.class, response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Unit(), bindingResult, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) String name) {
        ResponseEntity<Unit> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, name, Unit.class);
        ModelAndView mv = renderProcessing(response, name, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String name) {
        ResponseEntity<Unit> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, name, Unit.class);
        Unit unit = response.getBody();
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put(BASE_NAME, unit);
        List<Item> items = SharedData.getItems(false);
        modelMap.put("items", items);
        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) String name, @Valid @ModelAttribute(BASE_NAME) Unit entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(CREATE_VIEW_PAGE, BASE_NAME, entity);
        }

        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, Unit.class);
        ModelAndView mv = postUpdateProcessing(Unit.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new Unit(), bindingResult, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Unit>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Unit>>() {
        });
        ModelAndView mv = postListProcessing(response, "units", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Unit... entity) {
        return null;
    }

    private List<Item> fetchAllItems() {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl;
        restUrl = String.format(FETCH_URL, "item");
        ResponseEntity<List<Item>> response = restTemplate.exchange(restUrl, HttpMethod.POST, null, new ParameterizedTypeReference<List<Item>>() {
        });
        return response.getBody();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        binder.registerCustomEditor(Item.class, "item", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.length() == 0) {
                    return;
                }

                Long departmentId = Long.parseLong(text);
                RestTemplate restTemplate = new RestTemplate();
                String restUrl = String.format(FETCH_BY_ID_URL, "item", departmentId);
                ResponseEntity<Item> response = restTemplate.getForEntity(restUrl, Item.class);
                Item department = response.getBody();
                setValue((text.equals("")) ? null : department);
            }
        });
    }

}
