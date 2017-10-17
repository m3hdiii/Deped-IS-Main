package com.deped.controller.pack;

import com.deped.controller.AbstractMainController;
import com.deped.model.Response;
import com.deped.model.pack.Pack;
import com.deped.model.items.Item;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
public class PackController extends AbstractMainController<Pack, Long> {

    private static final String BASE_NAME = "pack";
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
    public ModelAndView renderCreatePage(@Valid @ModelAttribute("pack") Pack entity) {
        List<Item> items = fetchAllItems();
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put(BASE_NAME, entity);
        modelMap.put("items", items);
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid @ModelAttribute("pack") Pack entity) {
        entity.setCreationDate(new Date());
        ResponseEntity<Pack> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Pack.class);
        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Pack> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Pack.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Pack> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Pack.class);
        Pack pack = response.getBody();
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put(BASE_NAME, pack);
        List<Item> items = fetchAllItems();
        modelMap.put("items", items);
        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) Long aLong, @ModelAttribute(BASE_NAME) Pack entity) {
        entity.setPackId(aLong);
        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST);
        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Pack>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Pack>>() {
        });
        ModelAndView mv = listProcessing(response, "packs", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Pack... entity) {
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
