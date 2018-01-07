package com.deped.controller.request;

import com.deped.ResultBean;
import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.form.BorrowRequestDetailsForm;
import com.deped.form.RequestDetailsForm;
import com.deped.model.Response;
import com.deped.model.ResponseStatus;
import com.deped.model.account.User;
import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.request.Request;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.request.RequestStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.model.tracker.TrackingStatus;
import com.deped.utils.SystemUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    private static final String BORROW_MAPPING = BASE_NAME + URL_SEPARATOR + "borrow" + URL_SEPARATOR + ID_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;
    private static final String FLOW_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + "-flow";
    private static final String SUMMARY_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + "-summary";

    private static final String BASKET_VIEW_PAGE = BASE_NAME + URL_SEPARATOR + "basket" + URL_SEPARATOR + ID_PATTERN;
    private static final String APPROVAL_MAPPING = BASE_NAME + URL_SEPARATOR + "approval" + URL_SEPARATOR + ID_PATTERN;
    private static final String RELEASED_PAGE = BASE_NAME + URL_SEPARATOR + "issue" + URL_SEPARATOR + ID_PATTERN;
    private static final String REQUEST_DETAILS_PAGE = BASE_NAME + URL_SEPARATOR + "info" + URL_SEPARATOR + ID_PATTERN;

    private static final String SUMMARY_PAGE = BASE_NAME + URL_SEPARATOR + "summary" + URL_SEPARATOR + ID_PATTERN;

    private static final String UPDATE_STATUS_REST = BASE_NAME + URL_SEPARATOR + "update-status/user/%s/status/%d";


    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute("request") Request request, @PathVariable(ID_STRING_LITERAL) Long requestId, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (request == null || request.getRequestId() == null || request.getRequestId() != requestId) {

            RestTemplate restTemplate = new RestTemplate();
            String restUrl = String.format(FETCH_BY_ID_URL, "request", requestId);
            ResponseEntity<Request> responseRequest = restTemplate.getForEntity(restUrl, Request.class);
            request = responseRequest.getBody();

            String redirectUrl = null;
            if (request == null) {
                redirectUrl = "redirect:/request/create";
                return new ModelAndView(redirectUrl);
            }

            RequestStatus status = request.getRequestStatus();

            switch (status) {
                case SAVED:
                    break;
                case PENDING:
                    //You have to Wait For Approval
                    redirectUrl = "redirect:/request/create";
                    return new ModelAndView(redirectUrl);
                case CONSIDERED:
                    //You can check the status of your request
                    redirectUrl = "redirect:/request/create";
                    return new ModelAndView(redirectUrl);
                case FINALIZED:
                    //This request has been processed properly before
                    redirectUrl = "redirect:/request/create";
                    return new ModelAndView(redirectUrl);
            }

            List<RequestDetails> requestDetailsList = fetchRequestDetails(requestId);
            if (requestDetailsList != null && !requestDetailsList.isEmpty()) {
                httpSession.setAttribute(BASKET + request.getRequestId(), putRequestDetailsListIntoMap(new HashSet<>(requestDetailsList)));
            }
        }
        httpSession.setAttribute(REQUEST + requestId, request);


        Map<String, Object> modelMap = new HashMap<>(getConfigMap());

        switch (request.getItemType()) {
            case GOODS:
                modelMap.put("itemList", SharedData.getGoods(false));
                break;
            case SEMI_EXPENDABLE:
                modelMap.put("itemList", SharedData.getSemiExpendables(false));
                break;
            case EQUIPMENT:
                modelMap.put("itemList", SharedData.getEquipment(false));
                break;
        }

        modelMap.put("requestId", requestId);
        modelMap.put("requestDetails", new RequestDetails());
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;

    }


    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createActionWithRedirect(@PathVariable(ID_STRING_LITERAL) Long requestId, @Valid @ModelAttribute("requestDetails") RequestDetails entity, BindingResult bindingResult, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        Request request = entity.getRequest();

        if (request == null) {
            bindingResult.addError(new FieldError("RequestDetails", "request", "Something went wrong please contact with your admin"));
        }

        Object basketInfo = httpSession.getAttribute(BASKET + request.getRequestId());
        if (basketInfo != null) {
            HashMap<String, RequestDetails> bInfo = (HashMap<String, RequestDetails>) basketInfo;
            checkIfTheSameItemExisting(bInfo, entity, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<>(getConfigMap());
            modelMap.put("requestId", requestId);
            modelMap.put("itemList", SharedData.getItems(false));
            modelMap.put("requestDetails", entity);
            ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
            return mv;
        }


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

    private void checkIfTheSameItemExisting(HashMap<String, RequestDetails> basket, RequestDetails requestDetails, BindingResult bindingResult) {
        Collection<RequestDetails> requestDetailsList = basket.values();
        for (RequestDetails rd : requestDetailsList) {
            if (rd.getItem().getName().equals(requestDetails.getItem().getName())) {
                bindingResult.addError(new FieldError("RequestDetails", "item", "You can not add the same item twice, but you can edit it"));
                break;
            }
        }
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

        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put("requestId", requestId);
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("requestDetailsForm", new RequestDetailsForm());
        ModelAndView mav = new ModelAndView("pages/request-details/basket", modelMap);
        return mav;
    }

    @RequestMapping(value = BASKET_VIEW_PAGE, method = POST)
    public ModelAndView actionOnBasket(@PathVariable(ID_STRING_LITERAL) Long requestId, @RequestParam RequestActionParam actionParam, @ModelAttribute("requestDetailsForm") RequestDetailsForm requestDetailsForm, final RedirectAttributes redirectAttributes, HttpSession httpSession) {
        Object object = httpSession.getAttribute(BASKET + requestId);
        String requestPage = String.format("redirect:/request-details/create/%d", requestId);

        if (object == null) {
            return new ModelAndView(requestPage);
        }
        Map<String, RequestDetails> sessionBasket = (Map<String, RequestDetails>) object;
        Map<String, RequestDetails> formRequestDetailsMap = requestDetailsForm.getMap();

        String message = null;
        switch (actionParam) {
            case UPDATE_ALL:
                httpSession.setAttribute(BASKET + requestId, formRequestDetailsMap);
                return new ModelAndView(String.format("redirect:/request-details/create/%d", requestId));
            case DELETE_ALL:
                sessionBasket.clear();
                return new ModelAndView(requestPage);
            case SAVE_ALL:
                //TODO save inside database and go to home page
                boolean isSaved = saveRequestDetails(formRequestDetailsMap, true);
                if (isSaved) {
                    message = SUCCESS_MESSAGE;
                    httpSession.removeAttribute(BASKET + requestId);
                    httpSession.removeAttribute(REQUEST + requestId);
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
                    httpSession.removeAttribute(REQUEST + requestId);
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
        String restUrl;
        if (!isForSaveOnly) {
            restUrl = BASE_URL + BASE_NAME + "/request-all";
        } else {
            restUrl = String.format(CREATE_ALL_URL, BASE_NAME);
        }

        RequestDetails[] requestDetails = list.toArray(new RequestDetails[list.size()]);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, requestDetails, HttpMethod.POST, RequestDetails.class);
        Response body = response.getBody();
        if (body == null) {
            return false;
        }
        return true;

    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, @ModelAttribute("requestDetailsList") RequestDetails entity, BindingResult bindingResult) {

        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<RequestDetails>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<RequestDetails>>() {
        });
        ModelAndView mv = postListProcessing(response, "requests", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    //Administrative Tasks
    @RequestMapping(value = APPROVAL_MAPPING, method = GET)
    public ModelAndView approvalActionRender(@PathVariable(ID_STRING_LITERAL) Long requestId) {
        Request request = fetchRequest(requestId);
        ModelAndView requestChecking = requestChecking(request, RequestStatus.PENDING);
        if (requestChecking != null) {
            return requestChecking;
        }

        List<RequestDetails> requestDetailsList = fetchRequestDetails(requestId);
        ModelAndView requestDetailsChecking = requestDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            return requestDetailsChecking;
        }

        RequestDetailsStatus[] nextRequestDetailsStatuses = new RequestDetailsStatus[]{RequestDetailsStatus.APPROVED, RequestDetailsStatus.DISAPPROVED, RequestDetailsStatus.CANCELED};
        return renderActions(
                request,
                new HashSet<>(requestDetailsList),
                nextRequestDetailsStatuses
        );
    }


    @RequestMapping(value = APPROVAL_MAPPING, method = POST)
    public ModelAndView approvalActionSubmit(@ModelAttribute("requestDetailsForm") RequestDetailsForm orderDetailsForm) {
        ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, RequestDetailsStatus.APPROVED);
        Response response = updateResponse.getBody();

        String headTagTitle = "Approval Result";
        String headTagDescription = "Approval Result Summary";
        String heading = "Operation Result";
        String successMessage = response.getResponseStatus() == com.deped.model.ResponseStatus.SUCCESSFUL ? "You Successfully Processed The Approval Process" : null;
        String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Approval Process" : null;

        ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
        ModelAndView mav = createResultPage(resultBean);
        return mav;
    }


    @RequestMapping(value = REQUEST_DETAILS_PAGE, method = GET)
    public ModelAndView showInfo(@PathVariable(ID_STRING_LITERAL) Long requestId) {
        Request request = fetchRequest(requestId);
        List<RequestDetails> requestDetailsList = fetchRequestDetails(requestId);
        ModelAndView mav = renderActions(request, new HashSet<>(requestDetailsList), null);
        return mav;
    }


    public ModelAndView createResultPage(ResultBean resultBean) {
        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put("result", resultBean);
        ModelAndView mav = new ModelAndView("result", modelMap);
        return mav;

    }


    //Administrative Tasks
    @RequestMapping(value = RELEASED_PAGE, method = GET)
    public ModelAndView arrivalActionRender(@PathVariable(ID_STRING_LITERAL) Long requestId) {

        Request request = fetchRequest(requestId);
        ModelAndView requestChecking = requestChecking(request, RequestStatus.CONSIDERED);
        if (requestChecking != null) {
            return requestChecking;
        }

        List<RequestDetails> requestDetailsList = fetchRequestDetails(requestId);
        ModelAndView requestDetailsChecking = requestDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            return requestDetailsChecking;
        }
        RequestDetailsStatus[] nextRequestDetailsStatuses = new RequestDetailsStatus[]{RequestDetailsStatus.RELEASED, RequestDetailsStatus.CANCELED};

        return renderActions(
                request,
                new HashSet<>(requestDetailsList),
                nextRequestDetailsStatuses
        );

    }

    @RequestMapping(value = RELEASED_PAGE, method = POST)
    public ModelAndView arrivalActionSubmit(@ModelAttribute("requestDetailsForm") RequestDetailsForm orderDetailsForm) {
        ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, RequestDetailsStatus.RELEASED);
        Response response = updateResponse.getBody();

        String headTagTitle = "Release Page";
        String headTagDescription = "Release Result Summary";
        String heading = "Release Operation";
        String successMessage = response.getResponseStatus() == com.deped.model.ResponseStatus.SUCCESSFUL ? "You Successfully Processed The Releasing Process" : null;
        String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Releasing Process" : null;

        ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
        ModelAndView mav = createResultPage(resultBean);
        return mav;
    }


    //Administrative Tasks
    @RequestMapping(value = BORROW_MAPPING, method = GET)
    public ModelAndView borrowActionRender(@PathVariable(ID_STRING_LITERAL) Long requestId) {

        Request request = fetchRequest(requestId);
        if (request.getItemType() != ItemType.EQUIPMENT) {
            String newRequestRedirectUrl = "redirect:/dashboard";
            return new ModelAndView(newRequestRedirectUrl);
        }

        ModelAndView requestChecking = requestChecking(request, RequestStatus.CONSIDERED);
        if (requestChecking != null) {
            return requestChecking;
        }

        List<RequestDetails> requestDetailsList = fetchRequestDetails(requestId);
        ModelAndView requestDetailsChecking = requestDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            return requestDetailsChecking;
        }

        RequestDetailsStatus[] nextRequestDetailsStatuses = new RequestDetailsStatus[]{RequestDetailsStatus.RELEASED, RequestDetailsStatus.CANCELED};

        return renderBorrowActions(
                request,
                requestDetailsList,
                nextRequestDetailsStatuses
        );

    }


    private ModelAndView renderBorrowActions(Request request, List<RequestDetails> requestDetailsList, RequestDetailsStatus[] nextStatuses) {
        Date d = new Date();
        List<RequestTracker> requestTrackers = new ArrayList<>();
        for (int i = 0; i < requestDetailsList.size(); i++) {
            RequestDetails requestDetails = requestDetailsList.get(i);
            Integer noOfRequestFromAnItem = requestDetails.getApprovedQuantity();
            if (noOfRequestFromAnItem != null && noOfRequestFromAnItem > 0) {
                Item item = requestDetails.getItem();
                List<ItemDetails> itemDetailsList = getAvailableItemDetails(item);
                RequestTracker requestTracker = new RequestTracker(requestDetails, itemDetailsList);

                if (noOfRequestFromAnItem <= itemDetailsList.size()) {//we have captured info for all approved requests
                    for (int j = 0; j < noOfRequestFromAnItem; j++) {
                        requestTrackers.add(requestTracker);
                    }
                } else {
                    //TODO should check how many of that equipment stock exist and how many has been captured and
                    //TODO then redirect to capture page if there are uncaptured data and then return back here again
                    for (int j = 0; j < itemDetailsList.size(); j++) { //only this much can release
                        requestTrackers.add(requestTracker);
                    }
                }
            }
        }

        BorrowRequestDetailsForm borrowRequestDetailsForm = new BorrowRequestDetailsForm(requestTrackers);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("borrowRequestDetailsForm", borrowRequestDetailsForm);
        modelMap.put("trackingStatuses", TrackingStatus.values());
        return new ModelAndView("pages/borrow-request/create-borrow-request", modelMap);
    }

    private List<ItemDetails> getAvailableItemDetails(Item item) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_BY_ID_URL, "item-details/fetch-by-item-name", item.getName());

        ResponseEntity<List<ItemDetails>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<ItemDetails>>() {
        });
        return response.getBody();
    }

    @RequestMapping(value = BORROW_MAPPING, method = POST)
    public ModelAndView borrowActionSubmit(@PathVariable(ID_STRING_LITERAL) Long requestId, @ModelAttribute("borrowRequestDetailsForm") RequestDetailsForm orderDetailsForm) {
        ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, RequestDetailsStatus.RELEASED);
        Response response = updateResponse.getBody();

        String headTagTitle = "Release Page";
        String headTagDescription = "Release Result Summary";
        String heading = "Release Operation";
        String successMessage = response.getResponseStatus() == com.deped.model.ResponseStatus.SUCCESSFUL ? "You Successfully Processed The Releasing Process" : null;
        String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Releasing Process" : null;

        ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
        ModelAndView mav = createResultPage(resultBean);
        return mav;
    }


    private ModelAndView requestChecking(Request request, RequestStatus mustBeInStatus) {
        String newRequestRedirectUrl;
        if (request == null) {
            //TODO message this request does not exist
            newRequestRedirectUrl = "redirect:/dashboard";
            return new ModelAndView(newRequestRedirectUrl);
        }

        RequestStatus requestStatus = request.getRequestStatus();
        if (requestStatus != mustBeInStatus) {
            //TODO message this request is not in proper state as of now
            newRequestRedirectUrl = "redirect:/dashboard";
            return new ModelAndView(newRequestRedirectUrl);
        }
        return null;
    }

    //Administrative Tasks
    @RequestMapping(value = SUMMARY_PAGE, method = GET)
    public ModelAndView summaryActionRender(@PathVariable(ID_STRING_LITERAL) Long requestId, final RedirectAttributes redirectAttributes) {
        Request request = fetchRequest(requestId);

        if (request == null) {
            return new ModelAndView("redirect:/dashboard");
        }


        List<RequestDetails> requestDetailsList = fetchRequestDetails(requestId);
        ModelAndView requestDetailsChecking = requestDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            redirectAttributes.addFlashAttribute("request", request);
            ModelAndView mav = new ModelAndView();
            final String redirectUrl = String.format("redirect:/request-details/create/%d", request.getRequestId());
            mav.setViewName(redirectUrl);
            return mav;
        }

        Map<String, Object> modelMap = new HashMap<>(getConfigMap());

        modelMap.put("request", request);
        modelMap.put("requestDetailsList", requestDetailsList);
        modelMap.put("requestId", request.getRequestId());
        modelMap.put("relatedRequest", request);
        ModelAndView mv = new ModelAndView(SUMMARY_VIEW_PAGE, modelMap);
        return mv;
    }


    private ModelAndView requestDetailsChecking(List<RequestDetails> requestDetailsList) {
        if (requestDetailsList == null || requestDetailsList.isEmpty()) {
            return new ModelAndView();
        }
        return null;
    }


    private Request fetchRequest(Long requestId) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format(FETCH_BY_ID_URL, "request", requestId);
        ResponseEntity<Request> response = restTemplate.getForEntity(restUrl, Request.class);
        Request request = response.getBody();
        return request;
    }

    private ModelAndView renderActions(Request request, Set<RequestDetails> requestDetailsList, RequestDetailsStatus[] nextStatuses) {

        Map<String, Object> modelMap = new HashMap<>(getConfigMap());

        RequestDetailsForm orderDetailsForm = new RequestDetailsForm();
        orderDetailsForm.setMap(putRequestDetailsListIntoMap(requestDetailsList));
        modelMap.put("requestDetailsForm", orderDetailsForm);

        modelMap.put("requestId", request.getRequestId());
        modelMap.put("relatedRequest", request);
        modelMap.put("nextRequestDetailsStatuses", nextStatuses);
        ModelAndView mv = new ModelAndView(FLOW_VIEW_PAGE, modelMap);
        return mv;
    }


    private ResponseEntity<Response> updateStatusAction(RequestDetailsForm requestDetailsForm, RequestDetailsStatus status) {
        Map<String, RequestDetails> map = requestDetailsForm.getMap();
        Collection<RequestDetails> requestDetailsCollection = map.values();
        RequestDetails[] requestDetailsArray = requestDetailsCollection.toArray(new RequestDetails[requestDetailsCollection.size()]);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestDetails[]> httpEntity = new HttpEntity<>(requestDetailsArray, headers);

        User user = getUserFromSpringSecurityContext();
        String username = user.getUsername();
        String restUrl = String.format((BASE_URL + UPDATE_STATUS_REST), username, status.ordinal());

        ResponseEntity<Response> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, Response.class);
        return response;
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
                Item discoveredItem = fetchItemByStringId(text);
                setValue(discoveredItem);
            }
        });

        binder.registerCustomEditor(ItemDetails.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {

                setValue(null);
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
    public ModelAndView createAction(RequestDetails entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid RequestDetails... entity) {
        return null;
    }

}