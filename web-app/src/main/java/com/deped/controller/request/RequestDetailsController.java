package com.deped.controller.request;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.form.RequestDetailsForm;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.model.request.Request;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestStatus;
import com.deped.utils.SystemUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RequestDetailsController extends AbstractMainController<RequestDetails, Long> {

    public enum RequestActionParam {
        UPDATE_ALL, DELETE_ALL, SAVE_ALL, REQUEST_ALL
    }

    private static final String BASE_NAME = "request-details";
    private static final String BASKET = "requestDetailsMap-RequestNo";
    private static final String REQUEST = "requestSessionNo";

    //TODO TAKE NOTE THIS MAPPING IS DIFFERENT WITH OTHER CLASSES
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN + URL_SEPARATOR + ID_PATTERN;
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

    private static final String BASKET_VIEW_PAGE = BASE_NAME + URL_SEPARATOR + "basket" + URL_SEPARATOR + ID_PATTERN;

    public enum ActionParam {
        UPDATE_ALL, DELETE_ALL, SAVE_ALL, ORDER_ALL
    }

    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute("request") Request request, @PathVariable(ID_STRING_LITERAL) Long requestId, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (request == null || request.getRequestId() == null || request.getRequestId() != requestId) {
            RestTemplate restTemplate = new RestTemplate();
            String restUrl = String.format(FETCH_BY_ID_URL, "request", requestId);
            ResponseEntity<Request> response = restTemplate.getForEntity(restUrl, Request.class);
            request = response.getBody();

            if (request == null) {
                final String redirectUrl = "redirect:/request/create";
                return new ModelAndView(redirectUrl);
            }


            Set<RequestDetails> requestDetailsList = request.getRequestDetails();
            if (requestDetailsList != null && !requestDetailsList.isEmpty()) {
                httpSession.setAttribute(BASKET + request.getRequestId(), putRequestDetailsListIntoMap(requestDetailsList));
            }
        }

        if (httpSession.getAttribute(REQUEST + requestId) == null)
            httpSession.setAttribute(REQUEST + requestId, request);

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requestId", requestId);
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("requestDetails", new RequestDetails());

        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;

    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithRedirect(@Valid @ModelAttribute("requestDetail") RequestDetails entity, @Valid @ModelAttribute("requestEntity") Request requestEntity, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        Request request = entity.getRequest();
        if (request == null) {
            return null;
        }

        Object basketInfo = httpSession.getAttribute(BASKET + request.getRequestId());
        Map<String, RequestDetails> requestDetailsMap;
        if (basketInfo == null) {
            requestDetailsMap = new HashMap<>();
        } else {
            requestDetailsMap = (HashMap<String, RequestDetails>) basketInfo;
        }

        requestDetailsMap.put(SystemUtils.getRandomString(), entity);
        httpSession.setAttribute((BASKET + request.getRequestId()), requestDetailsMap);
        String redirectUrl = String.format("redirect:/request-details/create/%d", request.getRequestId());
        //redirectAttributes.addFlashAttribute("request", request);
        return new ModelAndView(redirectUrl);
    }


    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
//        ResponseEntity<List<RequestDetails>> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, List<RequestDetails.class);
//        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
//        return mv;
        return null;
    }

    @Override
    public ModelAndView renderUpdatePage(Long aLong) {
        return null;
    }

    @RequestMapping(value = BASKET_VIEW_PAGE, method = RequestMethod.GET)
    public ModelAndView renderBasket(@PathVariable(ID_STRING_LITERAL) Long requestId, HttpSession httpSession) {
        Object object = httpSession.getAttribute(BASKET + requestId);
        if (object == null) {
            String redirectUrl = String.format("redirect:/request-details/create/%d", requestId);
            return new ModelAndView(redirectUrl);
        }

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requestId", requestId);
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("requestDetailsForm", new RequestDetailsForm());
        ModelAndView mav = new ModelAndView("pages/request-details/basket", modelMap);
        return mav;
    }

    @RequestMapping(value = BASKET_VIEW_PAGE, method = POST)
    public ModelAndView actionOnBasket(@PathVariable(ID_STRING_LITERAL) Long requestId, @RequestParam RequestActionParam actionParam, @ModelAttribute("requestDetailsForm") RequestDetailsForm requestDetailsForm, HttpSession httpSession) {
        Object object = httpSession.getAttribute(BASKET + requestId);
        String requestPage = String.format("redirect:/request-details/create/%d", requestId);

        if (object == null) {
            return new ModelAndView(requestPage);
        }
        Map<String, RequestDetails> sessionRequestDetailsMap = (Map<String, RequestDetails>) object;
        Map<String, RequestDetails> formRequestDetailsMap = requestDetailsForm.getMap();

        String message = null;
        switch (actionParam) {
            case UPDATE_ALL:
                httpSession.setAttribute(BASKET + requestId, formRequestDetailsMap);
                return new ModelAndView(String.format("redirect:/request-details/create/%d", requestId));
            case DELETE_ALL:
                sessionRequestDetailsMap.clear();
                return new ModelAndView(requestPage);
            case SAVE_ALL:
                //TODO save inside database and go to home page
                boolean isSaved = saveRequestDetails(formRequestDetailsMap, true);
                if (isSaved) {
                    message = SUCCESS_MESSAGE;
                    httpSession.removeAttribute(BASKET + requestId);
                    return new ModelAndView("redirect:/dashboard");
                } else {
                    message = FAILURE_MESSAGE;
                    return new ModelAndView("pages/order-details/basket", "failureMessage", message);
                }
            case REQUEST_ALL:
                //TODO save inside database and go to render requested information page
                boolean isRequested = saveRequestDetails(formRequestDetailsMap, false);
                if (isRequested) {
                    message = SUCCESS_MESSAGE;
                    httpSession.removeAttribute(BASKET + requestId);
                    return new ModelAndView("redirect:/dashboard", "successMessage", message);
                } else {
                    message = FAILURE_MESSAGE;
                    return new ModelAndView("pages/order-details/basket", "failureMessage", message);
                }
        }

        return null;
    }

    private boolean saveRequestDetails(Map<String, RequestDetails> map, boolean isForSaveOnly) {
        List<RequestDetails> list = new ArrayList<>(map.values());
        if (!isForSaveOnly) {
            for (RequestDetails od : list) {
                od.setRequestStatus(RequestStatus.PENDING);
            }
        }

        RequestDetails[] requestDetails = list.toArray(new RequestDetails[list.size()]);
        ResponseEntity<Response> response = makeCreateAllRestRequest(requestDetails, BASE_NAME, HttpMethod.POST, RequestDetails.class);
        Response body = response.getBody();
        if (body == null) {
            return false;
        }
        return true;

    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, @ModelAttribute("requestDetailsList") RequestDetails entity) {
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<RequestDetails>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<RequestDetails>>() {
        });
        ModelAndView mv = listProcessing(response, "requests", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Request.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    HttpSession session = request.getSession(false);
                    Long requestId = null;
                    try {
                        requestId = Long.parseLong(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }

                    Request request;
                    Object requestObj = session.getAttribute(REQUEST + requestId);
                    if (requestObj != null) {
                        request = (Request) requestObj;
                    } else {
                        request = new Request();
                        request.setRequestId(requestId);
                    }
                    setValue(request);

                }
            }
        });

        binder.registerCustomEditor(Item.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    try {
                        Long itemId = Long.parseLong(text);
                        Item item = new Item();
                        item.setItemId(itemId);

                        List<Item> items = SharedData.getItems(false);
                        Item discoveredItem = SystemUtils.findElementInList(items, item);

                        setValue(discoveredItem);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }
                }
            }
        });
    }


    private synchronized Map<String, RequestDetails> putRequestDetailsListIntoMap(Set<RequestDetails> requestDetailsSet) {
        Map<String, RequestDetails> requestDetailsMap = new HashMap<>();
        for (Iterator<RequestDetails> it = requestDetailsSet.iterator(); it.hasNext(); ) {
            RequestDetails requestDetails = it.next();
            requestDetailsMap.put(SystemUtils.getRandomString(), requestDetails);
        }
        return requestDetailsMap;
    }


    @Override
    public ModelAndView renderCreatePage(RequestDetails entity) {
        return null;
    }

    @Override
    public ModelAndView createAction(RequestDetails entity) {
        return null;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid RequestDetails... entity) {
        return null;
    }

}