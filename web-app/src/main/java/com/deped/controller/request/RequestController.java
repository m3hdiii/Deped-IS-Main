package com.deped.controller.request;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.account.User;
import com.deped.model.items.ItemType;
import com.deped.model.request.Request;
import com.deped.model.request.RequestStatus;
import com.deped.model.search.RequestSearch;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RequestController extends AbstractMainController<Request, Long> {

    private static final String BASE_NAME = "request";

    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_APPROVAL_MAPPING = BASE_NAME + "/approval-list";
    private static final String RENDER_LIST_RELEASE_MAPPING = BASE_NAME + "/release-list";
    private static final String RENDER_LIST_SUMMARY_MAPPING = BASE_NAME + "/summary";
    private static final String RENDER_LIST_USER_MAPPING = BASE_NAME + FETCH_PATTERN + "/user" + FETCH_BY_ID_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String SEARCH_REQUEST = BASE_NAME + URL_SEPARATOR + "search-list";

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;
    private static final String SEARCH_LIST_VIEW_PAGE = BASE_SHOW_PAGE + "search" + LIST_PAGE;

    private static final String OPERATION_LIST = BASE_SHOW_PAGE + BASE_NAME + "-selected" + LIST_PAGE;


    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute("request") Request entity) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("itemTypes", ItemType.values());
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;

    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithRedirect(@Valid @ModelAttribute("request") Request entity, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("itemTypes", ItemType.values());
            modelMap.put("request", entity);
            ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
            return mv;
        }

        //TODO CREATE WHEN ITEMS ADDED
        entity.setRequestDate(new Date());
        User user = getUserFromSpringSecurityContext();
        entity.setUser(user);

        ResponseEntity<Request> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Request.class);
        Request request = response.getBody();
        redirectAttributes.addFlashAttribute("request", request);
        ModelAndView mav = new ModelAndView();
        final String redirectUrl = String.format("redirect:/request-details/create/%d", request.getRequestId());
        mav.setViewName(redirectUrl);
        return mav;
    }

    @RequestMapping(value = RENDER_LIST_APPROVAL_MAPPING, method = GET)
    public ModelAndView approvalListRender() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, BASE_NAME).concat("/").concat(RequestStatus.PENDING.ordinal() + "");
        ResponseEntity<List<Request>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Request>>() {
        });

        List<Request> requests = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requests", requests);
        modelMap.put("requestUrl", "/request-details/approval/");
        modelMap.put("anchorName", "Handle");
        ModelAndView mav = new ModelAndView(OPERATION_LIST, modelMap);
        return mav;

    }

    @RequestMapping(value = RENDER_LIST_RELEASE_MAPPING, method = GET)
    public ModelAndView releaseListRender() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, BASE_NAME).concat("/").concat(RequestStatus.CONSIDERED.ordinal() + "");
        ResponseEntity<List<Request>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Request>>() {
        });

        List<Request> requests = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requests", requests);
        modelMap.put("requestUrl", "/request-details/issue/");
        modelMap.put("anchorName", "Release");
        ModelAndView mav = new ModelAndView(OPERATION_LIST, modelMap);
        return mav;

    }

    @RequestMapping(value = RENDER_LIST_USER_MAPPING, method = GET)
    public ModelAndView userListRender(@PathVariable(ID_STRING_LITERAL) Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, BASE_NAME).concat("/").concat("user").concat("/").concat(userId + "");
        ResponseEntity<List<Request>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Request>>() {
        });

        List<Request> requests = response.getBody();
        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put("requests", requests);
        modelMap.put("requestUrl", "/request-details/");
        modelMap.put("anchorName", "More Info");
        ModelAndView mav = new ModelAndView(OPERATION_LIST, modelMap);
        return mav;

    }

    @RequestMapping(value = RENDER_LIST_SUMMARY_MAPPING, method = GET)
    public ModelAndView summaryListRender() {
        User user = getUserFromSpringSecurityContext();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, BASE_NAME).concat("/").concat("user").concat("/").concat(user.getUsername());
        ResponseEntity<List<Request>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Request>>() {
        });

        List<Request> requests = response.getBody();
        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put("requests", requests);
        modelMap.put("requestUrl", "/request-details/summary/");
        modelMap.put("anchorName", "More Info");
        ModelAndView mav = new ModelAndView(OPERATION_LIST, modelMap);
        return mav;

    }


    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Request> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(aLong), Request.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    //FIXME
    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Request> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(aLong), Request.class);
        Request item = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, item);
    }


    //FIXME
    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, Request entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
        }
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Request>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Request>>() {
        });
        ModelAndView mv = postListProcessing(response, "requests", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @RequestMapping(value = SEARCH_REQUEST, method = GET)
    public ModelAndView searchListRender(@ModelAttribute("requestSearch") RequestSearch entity, BindingResult bindingResult) {
        return new ModelAndView(SEARCH_LIST_VIEW_PAGE);
    }

    @RequestMapping(value = SEARCH_REQUEST, method = POST)
    public ModelAndView searchListAction(@Valid @ModelAttribute("requestSearch") RequestSearch entity, BindingResult bindingResult) {
        String disapprovedListStr = "disapproved-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<Request> disapprovedList = SharedData.fetchAllByUrl(url, new ParameterizedTypeReference<List<Request>>() {
        });

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("disapprovedRequests", disapprovedList);
        ModelAndView mav = new ModelAndView(disapprovedListStr, modelMap);
        return mav;

    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Request... entity) {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, "requiredDate", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });
    }

    @Override
    public ModelAndView createAction(Request entity, BindingResult bindingResult) {
        return null;
    }

}
