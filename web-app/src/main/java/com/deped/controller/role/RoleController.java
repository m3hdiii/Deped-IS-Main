package com.deped.controller.role;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.Response;
import com.deped.model.config.client.ClientEnumKey;
import com.deped.model.security.Role;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RoleController extends AbstractMainController<Role, String> {

    private static final String BASE_NAME = "role";
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
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Role entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid @ModelAttribute(BASE_NAME) Role entity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(CREATE_VIEW_PAGE, BASE_NAME, entity);
        }

        entity.setCreationDate(new Date());
        ResponseEntity<Role> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Role.class);
        ModelAndView mv = postCreateProcessing(Role.class, response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Role(), bindingResult, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) String name) {
        ResponseEntity<Role> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, name, Role.class);
        ModelAndView mv = renderProcessing(response, name, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String name) {
        ResponseEntity<Role> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(name), Role.class);
        Role role = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, role);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) String name, @Valid @ModelAttribute(BASE_NAME) Role entity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
        }

        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, Role.class);
        ModelAndView mv = postUpdateProcessing(Role.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new Role(), bindingResult, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        List<Role> roles = SharedData.getRoles(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", roles);
        Map<ClientEnumKey, String> mapConfig = SharedData.getClientConfigsMap(false);
        map.put("baseUrl", mapConfig.get(ClientEnumKey.RESOURCE_BASE_URL));

        ModelAndView mv = new ModelAndView(LIST_VIEW_PAGE, map);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Role... entity) {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
