package com.deped.controller.order;

import com.deped.ResultBean;
import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.form.OrderDetailsForm;
import com.deped.model.Response;
import com.deped.model.ResponseStatus;
import com.deped.model.account.User;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.order.Order;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsState;
import com.deped.model.order.OrderState;
import com.deped.model.supply.Supplier;
import com.deped.model.unit.Unit;
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
import javax.validation.*;
import java.beans.PropertyEditorSupport;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class OrderDetailsController extends AbstractMainController<OrderDetails, Long> {
    private static final String BASE_NAME = "order-details";
    private static final String BASKET = "orderDetailsMap-OrderNo";
    private static final String ORDER = "orderSessionNo";

    //TODO TAKE NOTE THIS MAPPING IS DIFFERENT WITH OTHER CLASSES
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN + URL_SEPARATOR + ID_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN + URL_SEPARATOR + ID_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + URL_SEPARATOR + ID_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String FLOW_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + "-flow";

    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;

    private static final String BASKET_VIEW_PAGE = BASE_NAME + URL_SEPARATOR + "basket" + URL_SEPARATOR + ID_PATTERN;
    private static final String APPROVAL_PAGE = BASE_NAME + URL_SEPARATOR + "approval" + URL_SEPARATOR + ID_PATTERN;
    private static final String REQUISITION_PAGE = BASE_NAME + URL_SEPARATOR + "requisition" + URL_SEPARATOR + ID_PATTERN;
    private static final String ARRIVAL_PAGE = BASE_NAME + URL_SEPARATOR + "arrival" + URL_SEPARATOR + ID_PATTERN;

    private static final String UPDATE_STATE_REST = BASE_NAME + URL_SEPARATOR + "update-state/user/%s/state/%d";

    public enum ActionParam {
        UPDATE_ALL, DELETE_ALL, SAVE_ALL, ORDER_ALL
    }

    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute("order") Order order, @PathVariable(ID_STRING_LITERAL) Long orderId, final RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if (order == null || order.getOrderId() == null || order.getOrderId() != orderId) {
            order = fetchOrder(orderId);
            String redirectUrl = null;
            if (order == null) {
                redirectUrl = "redirect:/order/create";
                return new ModelAndView(redirectUrl);
            }

            OrderState orderState = order.getOrderState();

            switch (orderState) {
                case SAVED:
                    break;
                case PENDING:
                    //You have to Wait For Approval
                    redirectUrl = "redirect:/order/create";
                    return new ModelAndView(redirectUrl);
                case CONSIDERED:
                    //You can Now Order It
                    redirectUrl = "redirect:/order/create";
                case FINALIZED:
                    //This Order has been Arrived properly before
                    redirectUrl = "redirect:/order/create";
                    return new ModelAndView(redirectUrl);

            }

            List<OrderDetails> orderDetailsList = fetchRequestDetails(orderId);
            if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                httpSession.setAttribute(BASKET + order.getOrderId(), putOrderDetailsListIntoMap(new HashSet<>(orderDetailsList)));
            }
        }
        httpSession.setAttribute(ORDER + orderId, order);

        Map<String, Object> modelMap = createOrderDetailsModelMap(orderId, new OrderDetails());
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;

    }

    private Map<String, Object> createOrderDetailsModelMap(Long orderId, OrderDetails orderDetails) {
        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put("orderId", orderId);
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("units", SharedData.getUnits(false));
        modelMap.put("categories", SharedData.getCategories(false));
        modelMap.put("suppliers", SharedData.getSuppliers(false));
        modelMap.put("orderDetail", orderDetails);

        return modelMap;

    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithRedirect(@ModelAttribute("orderEntity") Order orderEntity, @Valid @ModelAttribute("orderDetail") OrderDetails entity, BindingResult bindingResult, HttpSession httpSession, final RedirectAttributes redirectAttributes) {

        Order order = entity.getOrder();
        if (order == null) {
            return null;
        }

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = createOrderDetailsModelMap(order.getOrderId(), entity);
            ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
            return mv;

        }

        Object basketInfo = httpSession.getAttribute(BASKET + order.getOrderId());
        Map<String, OrderDetails> orderDetailsMap;
        if (basketInfo == null) {
            orderDetailsMap = new HashMap<>();
        } else {
            orderDetailsMap = (HashMap<String, OrderDetails>) basketInfo;
        }

        orderDetailsMap.put(SystemUtils.getRandomString(), entity);
        httpSession.setAttribute((BASKET + order.getOrderId()), orderDetailsMap);
        String redirectUrl = String.format("redirect:/order-details/create/%d", order.getOrderId());
        redirectAttributes.addFlashAttribute("order", order);
        return new ModelAndView(redirectUrl);
    }


    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
//        ResponseEntity<List<OrderDetails>> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, List<OrderDetails.class);
//        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
//        return mv;
        return null;
    }

    @Override
    public ModelAndView renderUpdatePage(Long aLong) {
        return null;
    }

    @RequestMapping(value = BASKET_VIEW_PAGE, method = RequestMethod.GET)
    public ModelAndView renderBasket(@PathVariable(ID_STRING_LITERAL) Long orderId, HttpSession httpSession) {
        ModelAndView mav = getBasketModelAndView(orderId, new OrderDetailsForm(), httpSession);
        return mav;
    }

    private ModelAndView getBasketModelAndView(Long orderId, OrderDetailsForm orderDetailsForm, HttpSession httpSession) {
        Object object = httpSession.getAttribute(BASKET + orderId);
        if (object == null) {
            String redirectUrl = String.format("redirect:/order-details/create/%d", orderId);
            return new ModelAndView(redirectUrl);
        }

        Map<String, Object> modelMap = createOrderDetailsModelMap(orderId, null);
        modelMap.put("orderDetailsForm", orderDetailsForm);
        ModelAndView mav = new ModelAndView("pages/order-details/basket", modelMap);
        return mav;
    }


    @RequestMapping(value = BASKET_VIEW_PAGE, method = POST)
    public ModelAndView actionOnBasket(@PathVariable(ID_STRING_LITERAL) Long orderId, @RequestParam ActionParam actionParam, @ModelAttribute("orderDetailsForm") OrderDetailsForm orderDetailsForm, BindingResult bindingResult, HttpSession httpSession) {

        addValidation(orderDetailsForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView mav = getBasketModelAndView(orderId, orderDetailsForm, httpSession);
            return mav;
        }
        Object object = httpSession.getAttribute(BASKET + orderId);
        final String orderPage = String.format("redirect:/order-details/create/%d", orderId);
        final String dashboardPage = "redirect:/dashboard";

        if (object == null) {
            return new ModelAndView(orderPage);
        }
        Map<String, OrderDetails> sessionOrderDetailsMap = (Map<String, OrderDetails>) object;
        Map<String, OrderDetails> formOrderDetailsMap = orderDetailsForm.getMap();

        String message = null;
        switch (actionParam) {
            case UPDATE_ALL:
                httpSession.setAttribute(BASKET + orderId, formOrderDetailsMap);
                return new ModelAndView(orderPage);
            case DELETE_ALL:
                sessionOrderDetailsMap.clear();
                return new ModelAndView(orderPage);
            case SAVE_ALL:
                //TODO save inside database and go to home page
                boolean isSaved = saveOrderDetails(formOrderDetailsMap, true);
                if (isSaved) {
                    message = SUCCESS_MESSAGE;
                    httpSession.removeAttribute(BASKET + orderId);
                    httpSession.removeAttribute(ORDER + orderId);
                    return new ModelAndView(dashboardPage);
                } else {
                    message = FAILURE_MESSAGE;
                    return new ModelAndView("pages/order-details/basket", "failureMessage", message);
                }
            case ORDER_ALL:
                //TODO save inside database and go to render ordered information page
                boolean isOrdered = saveOrderDetails(formOrderDetailsMap, false);
                if (isOrdered) {
                    message = SUCCESS_MESSAGE;
                    httpSession.removeAttribute(BASKET + orderId);
                    return new ModelAndView("redirect:/dashboard", "successMessage", message);
                } else {
                    message = FAILURE_MESSAGE;
                    return new ModelAndView("pages/order-details/basket", "failureMessage", message);
                }
        }

        return null;
    }

    @RequestMapping(value = APPROVAL_PAGE, method = GET)
    public ModelAndView approvalActionRender(@ModelAttribute("order") Order order, @PathVariable(ID_STRING_LITERAL) Long orderId) {

        Order fetchedOrder = fetchOrder(orderId);
        ModelAndView requestChecking = orderChecking(fetchedOrder, OrderState.PENDING);
        if (requestChecking != null) {
            return requestChecking;
        }

        List<OrderDetails> requestDetailsList = fetchRequestDetails(orderId);
        ModelAndView requestDetailsChecking = orderDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            return requestDetailsChecking;
        }

        OrderDetailsState[] nextRequestDetailsStatuses = new OrderDetailsState[]{OrderDetailsState.APPROVED, OrderDetailsState.DISAPPROVED, OrderDetailsState.CANCELED};
        return renderActions(
                fetchedOrder,
                new HashSet<>(requestDetailsList),
                "Approval Page",
                "Approving Registered Order Page",
                "Approval",
                OrderDetailsState.APPROVED,
                nextRequestDetailsStatuses
        );
    }

    @RequestMapping(value = APPROVAL_PAGE, method = POST)
    public ModelAndView approvalActionSubmit(@ModelAttribute("orderDetailsForm") OrderDetailsForm orderDetailsForm) {
        ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, OrderDetailsState.APPROVED);
        Response response = updateResponse.getBody();

        String headTagTitle = "Approval Result";
        String headTagDescription = "Approval Result Summary";
        String heading = "Operation Result";
        String successMessage = response.getResponseStatus() == ResponseStatus.SUCCESSFUL ? "You Successfully Processed The Approval Process" : null;
        String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Approval Process" : null;

        ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
        ModelAndView mav = createResultPage(resultBean);
        return mav;
    }

    @RequestMapping(value = REQUISITION_PAGE, method = GET)
    public ModelAndView orderedActionRender(@ModelAttribute("order") Order order, @PathVariable(ID_STRING_LITERAL) Long orderId) {

        Order fetchedOrder = fetchOrder(orderId);
        ModelAndView requestChecking = orderChecking(fetchedOrder, OrderState.CONSIDERED);
        if (requestChecking != null) {
            return requestChecking;
        }

        List<OrderDetails> requestDetailsList = fetchRequestDetails(orderId);
        ModelAndView requestDetailsChecking = orderDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            return requestDetailsChecking;
        }

        OrderDetailsState[] nextRequestDetailsStatuses = new OrderDetailsState[]{OrderDetailsState.ORDERED, OrderDetailsState.CANCELED};
        return renderActions(
                fetchedOrder,
                new HashSet<>(requestDetailsList),
                "Requisition Order Page",
                "Requisition Page for Approved Items",
                "Requisition",
                OrderDetailsState.ORDERED,
                nextRequestDetailsStatuses
        );
    }

    @RequestMapping(value = REQUISITION_PAGE, method = POST)
    public ModelAndView orderedActionSubmit(@ModelAttribute("orderDetailsForm") OrderDetailsForm orderDetailsForm) {
        ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, OrderDetailsState.ORDERED);
        Response response = updateResponse.getBody();

        String headTagTitle = "Order Result";
        String headTagDescription = "Order Result Summary";
        String heading = "Operation Result";
        String successMessage = response.getResponseStatus() == ResponseStatus.SUCCESSFUL ? "You Successfully Process The Ordering Process" : null;
        String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Ordering Process" : null;

        ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
        ModelAndView mav = createResultPage(resultBean);
        return mav;
    }

    @RequestMapping(value = ARRIVAL_PAGE, method = GET)
    public ModelAndView arrivalActionRender(@ModelAttribute("order") Order order, @PathVariable(ID_STRING_LITERAL) Long orderId) {
        Order fetchedOrder = fetchOrder(orderId);
        ModelAndView requestChecking = orderChecking(fetchedOrder, OrderState.ORDERED);
        if (requestChecking != null) {
            return requestChecking;
        }

        List<OrderDetails> requestDetailsList = fetchRequestDetails(orderId);
        ModelAndView requestDetailsChecking = orderDetailsChecking(requestDetailsList);
        if (requestDetailsChecking != null) {
            return requestDetailsChecking;
        }

        OrderDetailsState[] nextRequestDetailsStatuses = new OrderDetailsState[]{OrderDetailsState.ARRIVED, OrderDetailsState.NOT_ARRIVED, OrderDetailsState.CANCELED};
        return renderActions(
                fetchedOrder,
                new HashSet<>(requestDetailsList),
                "Arrival Page",
                "Arrival Page for Ordered Items",
                "Arrival",
                OrderDetailsState.ARRIVED,
                nextRequestDetailsStatuses
        );
    }

    public enum OrderActionParam {
        PARTIAL_DELIVERY, FINALIZE_DELIVERY
    }

    //TODO
    @RequestMapping(value = ARRIVAL_PAGE, method = POST)
    public ModelAndView arrivalActionSubmit(@RequestParam OrderActionParam actionParam, @ModelAttribute("orderDetailsForm") OrderDetailsForm orderDetailsForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //TODO VALIDATIONS
        }

        switch (actionParam) {
            case PARTIAL_DELIVERY:
                break;
            case FINALIZE_DELIVERY:
                break;
        }

        if (actionParam == OrderActionParam.PARTIAL_DELIVERY) {
            ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, OrderDetailsState.PARTIALLY_ARRIVED);
            Response response = updateResponse.getBody();

            String headTagTitle = "Partially Arrival Result";
            String headTagDescription = "Partially Arrival Result Summary";
            String heading = "Operation Result";
            String successMessage = response.getResponseStatus() == ResponseStatus.SUCCESSFUL ? "You Successfully Process The Partial Delivery Process" : null;
            String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Partial Delivery Process" : null;

            if (response.getResponseStatus() == ResponseStatus.SUCCESSFUL) {
                SharedData.getItems(true);
            }

            ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
            ModelAndView mav = createResultPage(resultBean);
            return mav;
        }

        if (actionParam == OrderActionParam.FINALIZE_DELIVERY) {
            ResponseEntity<Response> updateResponse = updateStatusAction(orderDetailsForm, OrderDetailsState.ARRIVED);
            Response response = updateResponse.getBody();

            String headTagTitle = "Arrival Result";
            String headTagDescription = "Arrival Result Summary";
            String heading = "Operation Result";
            String successMessage = response.getResponseStatus() == ResponseStatus.SUCCESSFUL ? "You Successfully Process The Arrival Process" : null;
            String failureMessage = response.getResponseStatus() == ResponseStatus.FAILED ? "Something Went Wrong In Arrival Process" : null;

            if (response.getResponseStatus() == ResponseStatus.SUCCESSFUL) {
                SharedData.getItems(true);
            }

            ResultBean resultBean = new ResultBean(headTagTitle, headTagDescription, heading, successMessage, failureMessage);
            ModelAndView mav = createResultPage(resultBean);
            return mav;
        }

        //TODO it won't get here
        return null;
    }

    /**
     * For Canceled, Disapproved or Not Arrived Items
     *
     * @return
     */
    @RequestMapping(value = "testi", method = GET)
    public ModelAndView reconsiderationOrder() {

        List<Order> savedOrder = fetchOrderByStates(OrderState.SAVED);

        OrderDetailsState[] orderDetailsStates = new OrderDetailsState[]{OrderDetailsState.DISAPPROVED, OrderDetailsState.NOT_ARRIVED, OrderDetailsState.CANCELED};
        List<OrderDetails> orderDetails = fetchOrderDetailsByStates(orderDetailsStates);

        return renderActions(
                savedOrder,
                new HashSet<>(orderDetails),
                "Arrival Page",
                "Arrival Page for Ordered Items",
                "Arrival"
        );
    }

    private List<OrderDetails> fetchOrderDetailsByStates(OrderDetailsState... states) {
        Integer ordinals[] = new Integer[states.length];
        for (int i = 0; i < states.length; i++) {
            ordinals[i] = states[i].ordinal();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer[]> httpEntity = new HttpEntity<>(ordinals, headers);
        String restUrl = String.format(FETCH_URL, "order-details").concat("/").concat("by-states");
        ResponseEntity<List<OrderDetails>> responseDetails = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<OrderDetails>>() {
        });

        List<OrderDetails> list = responseDetails.getBody();
        return list;
    }

    private List<Order> fetchOrderByStates(OrderState... states) {
        Integer ordinals[] = new Integer[states.length];
        for (int i = 0; i < states.length; i++) {
            ordinals[i] = states[i].ordinal();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer[]> httpEntity = new HttpEntity<>(ordinals, headers);
        String restUrl = String.format(FETCH_URL, "order").concat("/").concat("by-states");
        ResponseEntity<List<Order>> responseDetails = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Order>>() {
        });

        List<Order> list = responseDetails.getBody();
        return list;
    }


    private Order fetchOrder(Long requestId) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format(FETCH_BY_ID_URL, "order", requestId);
        ResponseEntity<Order> response = restTemplate.getForEntity(restUrl, Order.class);
        Order order = response.getBody();
        return order;
    }

    private List<OrderDetails> fetchRequestDetails(Long requestId) {
        RestTemplate restTemplate = new RestTemplate();
        //get RequestDetails if there is any
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "order-details").concat("/").concat(requestId + "");
        ResponseEntity<List<OrderDetails>> responseDetails = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<OrderDetails>>() {
        });
        List<OrderDetails> requestDetailsList = responseDetails.getBody();
        return requestDetailsList;
    }

    private ModelAndView orderChecking(Order order, OrderState mustBeInStatus) {
        String newRequestRedirectUrl;
        if (order == null) {
            //TODO message this order does not exist
            newRequestRedirectUrl = "redirect:/dashboard";
            return new ModelAndView(newRequestRedirectUrl);
        }

        OrderState orderState = order.getOrderState();
        if (orderState != mustBeInStatus) {
            //TODO message this order is not in proper state as of now
            newRequestRedirectUrl = "redirect:/dashboard";
            return new ModelAndView(newRequestRedirectUrl);
        }
        return null;
    }

    private ModelAndView orderDetailsChecking(List<OrderDetails> orderDetailsList) {
        String newRequestRedirectUrl;
        if (orderDetailsList == null || orderDetailsList.isEmpty()) {
            //TODO message this request does not have any request details which is wrong ...
            newRequestRedirectUrl = "redirect:/dashboard";
            return new ModelAndView(newRequestRedirectUrl);
        }

        return null;
    }

    private ModelAndView renderActions(Order order, Set<OrderDetails> orderDetailsList, String pageTitle, String topHeading, String h1Placeholder, OrderDetailsState currentState, OrderDetailsState[] nextStates) {

        Map<String, Object> modelMap = new HashMap<>(getConfigMap());

        OrderDetailsForm orderDetailsForm = new OrderDetailsForm();
        orderDetailsForm.setMap(putOrderDetailsListIntoMap(orderDetailsList));
        modelMap.put("orderDetailsForm", orderDetailsForm);

        modelMap.put("orderId", order.getOrderId());
        modelMap.put("relatedOrder", order);
        modelMap.put("currentOrderDetailsState", currentState);
        modelMap.put("nextOrderDetailsStates", nextStates);
        modelMap.put("pageTitle", pageTitle);
        modelMap.put("topHeading", topHeading);
        modelMap.put("h1Placeholder", h1Placeholder);

        ModelAndView mv = new ModelAndView(FLOW_VIEW_PAGE, modelMap);
        return mv;

    }

    private ModelAndView renderActions(List<Order> orders, Set<OrderDetails> orderDetailsList, String pageTitle, String topHeading, String h1Placeholder) {

        Map<String, Object> modelMap = new HashMap<>(getConfigMap());

        OrderDetailsForm orderDetailsForm = new OrderDetailsForm();
        orderDetailsForm.setMap(putOrderDetailsListIntoMap(orderDetailsList));
        modelMap.put("orderDetailsForm", orderDetailsForm);
        modelMap.put("orders", orders);
        modelMap.put("pageTitle", pageTitle);
        modelMap.put("topHeading", topHeading);
        modelMap.put("h1Placeholder", h1Placeholder);

        ModelAndView mv = new ModelAndView(FLOW_VIEW_PAGE, modelMap);
        return mv;

    }


    private ResponseEntity<Response> updateStatusAction(OrderDetailsForm orderDetailsForm, OrderDetailsState state) {
        Map<String, OrderDetails> map = orderDetailsForm.getMap();
        Collection<OrderDetails> orderDetailsCollection = map.values();
        OrderDetails[] orderDetailsArray = orderDetailsCollection.toArray(new OrderDetails[orderDetailsCollection.size()]);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderDetails[]> httpEntity = new HttpEntity<>(orderDetailsArray, headers);

        User user = getUserFromSpringSecurityContext();
        String username = user.getUsername();

        String restUrl = String.format((BASE_URL + UPDATE_STATE_REST), username, state.ordinal());

        ResponseEntity<Response> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, Response.class);
        return response;
    }

    private boolean saveOrderDetails(Map<String, OrderDetails> map, boolean isForSaveOnly) {
        List<OrderDetails> list = new ArrayList<>(map.values());
        String restUrl;
        if (!isForSaveOnly) {
            restUrl = BASE_URL + BASE_NAME + "/order-all";
        } else {
            restUrl = String.format(CREATE_ALL_URL, BASE_NAME);
        }

        OrderDetails[] requestDetails = list.toArray(new OrderDetails[list.size()]);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, requestDetails, HttpMethod.POST, OrderDetails.class);
        Response body = response.getBody();
        if (body == null) {
            return false;
        }
        return true;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, @ModelAttribute("orderDetailsList") OrderDetails entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<OrderDetails>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<OrderDetails>>() {
        });
        ModelAndView mv = postListProcessing(response, "orders", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Unit.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    try {
                        Unit pack = new Unit();
                        pack.setName(text);

                        List<Unit> packs = SharedData.getUnits(false);
                        Unit discoveredPack = SystemUtils.findElementInList(packs, pack);
                        setValue(discoveredPack);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }
                }
            }
        });

        binder.registerCustomEditor(Supplier.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    try {
                        Long supplierId = Long.parseLong(text);
                        Supplier supplier = new Supplier();
                        supplier.setSupplierId(supplierId);

                        List<Supplier> suppliers = SharedData.getSuppliers(false);
                        Supplier discoveredSupplier = SystemUtils.findElementInList(suppliers, supplier);

                        setValue(discoveredSupplier);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }
                }
            }
        });

        binder.registerCustomEditor(Category.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    try {
                        Category category = new Category();
                        category.setName(text);

                        List<Category> categories = SharedData.getCategories(false);
                        Category discoveredCategories = SystemUtils.findElementInList(categories, category);

                        setValue(discoveredCategories);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }
                }
            }
        });

        binder.registerCustomEditor(Order.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    HttpSession session = request.getSession(false);
                    Long orderId = null;
                    try {
                        orderId = Long.parseLong(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }

                    Order order;
                    Object orderObj = session.getAttribute(ORDER + orderId);
                    if (orderObj != null) {
                        order = (Order) orderObj;
                    } else {
                        order = new Order();
                        order.setOrderId(orderId);
                    }
                    setValue(order);

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
    }


    private synchronized Map<String, OrderDetails> putOrderDetailsListIntoMap(Set<OrderDetails> orderDetailsSet) {
        Map<String, OrderDetails> orderDetailsMap = new HashMap<>();
        for (Iterator<OrderDetails> it = orderDetailsSet.iterator(); it.hasNext(); ) {
            OrderDetails orderDetails = it.next();
            orderDetailsMap.put(SystemUtils.getRandomString(), orderDetails);
        }
        return orderDetailsMap;
    }

    private void addValidation(OrderDetailsForm orderDetailsForm, BindingResult result) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Map<String, OrderDetails> map = orderDetailsForm.getMap();

        for (Map.Entry<String, OrderDetails> entry : map.entrySet()) {
            OrderDetails orderDetails = entry.getValue();
            String key = entry.getKey();
            Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

            for (ConstraintViolation<OrderDetails> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                // Add JSR-303 errors to BindingResult
                // This allows Spring to display them in view via a FieldError
                result.addError(new FieldError("OrderDetails", propertyPath, message));
            }

        }

    }


    @Override
    public ModelAndView renderCreatePage(OrderDetails entity) {
        return null;
    }

    @Override
    public ModelAndView createAction(OrderDetails entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid OrderDetails... entity) {
        return null;
    }

}