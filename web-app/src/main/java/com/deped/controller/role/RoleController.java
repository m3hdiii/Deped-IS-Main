package com.deped.controller.role;

import com.deped.controller.AbstractMainController;
import com.deped.model.Response;
import com.deped.model.security.Role;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RoleController extends AbstractMainController<Role, Long> {

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
        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Role());
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Role> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Role.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Role> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Role.class);
        Role role= response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, role);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) Long aLong, @Valid @ModelAttribute(BASE_NAME) Role entity, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
        }
        entity.setRoleId(aLong);
        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, Role.class);
        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Role>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Role>>() {
        });
        ModelAndView mv = listProcessing(response, "roles", LIST_VIEW_PAGE);
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
