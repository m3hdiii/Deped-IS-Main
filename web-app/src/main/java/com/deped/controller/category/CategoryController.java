package com.deped.controller.category;

import com.deped.controller.AbstractMainController;
import com.deped.model.Response;
import com.deped.model.category.Category;
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
public class CategoryController extends AbstractMainController<Category, String> {

    private static final String BASE_NAME = "category";
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
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Category entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid @ModelAttribute(BASE_NAME) Category entity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(CREATE_VIEW_PAGE, BASE_NAME, entity);
        }

        entity.setCreationDate(new Date());
        ResponseEntity<Category> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Category.class);
        ModelAndView mv = postCreateProcessing(Category.class, response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Category(), bindingResult, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) String id) {
        ResponseEntity<Category> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(id), Category.class);
        ModelAndView mv = renderProcessing(response, id, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String id) {
        ResponseEntity<Category> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(id), Category.class);
        Category category = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, category);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) String id, @Valid @ModelAttribute(BASE_NAME) Category entity, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
        }

        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, Category.class);
        ModelAndView mv = postUpdateProcessing(Category.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new Category(), result, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Category>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Category>>() {
        });
        ModelAndView mv = postListProcessing(response, "categories", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Category... entity) {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
