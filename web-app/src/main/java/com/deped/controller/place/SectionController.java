package com.deped.controller.place;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.Response;
import com.deped.model.location.office.Department;
import com.deped.model.location.office.Section;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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
public class SectionController extends AbstractMainController<Section, Long> {

    private static final String BASE_NAME = "section";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_LIST_MAPPING_BY_COUNTRY_CODE = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Section entity) {
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put(BASE_NAME, entity);
        modelMap.put("departments", fetchAllDepartment());
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid @ModelAttribute(BASE_NAME) Section entity, BindingResult bindingResult) {
        List<Department> departments = fetchAllDepartment();
        if (bindingResult.hasErrors()) {
            HashMap<String, Object> modelMap = new HashMap<>();
            modelMap.put(BASE_NAME, entity);
            modelMap.put("departments", departments);
            return new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        }
        entity.setCreationDate(new Date());
        ResponseEntity<Section> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Section.class);

        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Section());
        mv.addObject("departments", departments);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Section> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Section.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Section> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Section.class);
        Section section = response.getBody();
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put(BASE_NAME, section);
        List<Department> departments = fetchAllDepartment();
        modelMap.put("departments", departments);
        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) Long aLong, @Valid @ModelAttribute(BASE_NAME) Section entity, BindingResult bindingResult) {

        List<Department> departments = fetchAllDepartment();

        if (bindingResult.hasErrors()) {
            HashMap<String, Object> modelMap = new HashMap<>();
            modelMap.put("departments", departments);
            modelMap.put(BASE_NAME, entity);
            return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
        }
        entity.setSectionId(aLong);
        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, Section.class);

        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        mv.addObject("departments", departments);
        return mv;
    }


    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Section>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Section>>() {
        });
        ModelAndView mv = listProcessing(response, "sections", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Section... entity) {
        return null;
    }

    private List<Department> fetchAllDepartment() {
        return SharedData.getDepartments(false);
    }

    @RequestMapping(value = RENDER_LIST_MAPPING_BY_COUNTRY_CODE, method = RequestMethod.POST)
    public @ResponseBody
    List<Section> renderListPage(@PathVariable(ID_STRING_LITERAL) Long departmentId) {
//        List<Department> departments = SharedData.getDepartments(false);
//        for (Department dep : departments) {
//            if (dep.getDepartmentId() == departmentId) {
//                return dep.getSections();
//            }
//        }
//        return null;
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        binder.registerCustomEditor(Department.class, "department", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.length() == 0) {
                    return;
                }

                Long departmentId = Long.parseLong(text);
                RestTemplate restTemplate = new RestTemplate();
                String restUrl = String.format(FETCH_BY_ID_URL, "department", departmentId);
                ResponseEntity<Department> response = restTemplate.getForEntity(restUrl, Department.class);
                Department department = response.getBody();
                setValue((text.equals("")) ? null : department);
            }
        });


    }
}
