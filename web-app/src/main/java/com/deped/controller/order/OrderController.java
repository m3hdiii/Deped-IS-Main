package com.deped.controller.order;

import com.deped.controller.AbstractMainController;
import com.deped.model.account.User;
import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.model.order.Schedule;
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
public class OrderController extends AbstractMainController<Order, Long> {

    private static final String BASE_NAME = "order";

    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;

    private static final String RENDER_LIST_APPROVAL_MAPPING = BASE_NAME + "/approval-list";
    private static final String RENDER_LIST_REQUISITION_MAPPING = BASE_NAME + "/requisition-list";
    private static final String RENDER_LIST_ARRIVAL_MAPPING = BASE_NAME + "/arrival-list";
    private static final String RENDER_LIST_USER_MAPPING = BASE_NAME + FETCH_PATTERN + "/user" + FETCH_BY_ID_PATTERN;

    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;

    private static final String ORDER_CONSIDERATION_PAGE = BASE_SHOW_PAGE + BASE_NAME + "-selected" + LIST_PAGE;


    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Order entity) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("schedules", Schedule.values());

        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;

    }

    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createActionWithRedirect(@Valid @ModelAttribute(BASE_NAME) Order entity, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("schedules", Schedule.values());
            modelMap.put(BASE_NAME, entity);
            ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
            return mv;
        }
        entity.setOrderDate(new Date());

        User user = getUserFromSpringSecurityContext();
        entity.setUser(user);

        ResponseEntity<Order> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Order.class);
        Order order = response.getBody();
        redirectAttributes.addFlashAttribute("order", order);
        ModelAndView mav = new ModelAndView();
        final String redirectUrl = String.format("redirect:/order-details/create/%d", order.getOrderId());
        mav.setViewName(redirectUrl);
        return mav;
    }

    @RequestMapping(value = RENDER_LIST_APPROVAL_MAPPING, method = GET)
    public ModelAndView approvalListRender() {
        List<Order> orderList = fetchByState(OrderState.PENDING);

        ModelAndView mav = createListModelMapping(
                orderList,
                ORDER_CONSIDERATION_PAGE,
                "/order-details/approval/",
                "View Details",
                "Approval List Page",
                "Approval List Page For Orders",
                "Approval"
        );

        return mav;
    }

    @RequestMapping(value = RENDER_LIST_REQUISITION_MAPPING, method = GET)
    public ModelAndView requisitionListRender() {
        List<Order> orderList = fetchByState(OrderState.CONSIDERED);
        ModelAndView mav = createListModelMapping(
                orderList,
                ORDER_CONSIDERATION_PAGE,
                "/order-details/requisition/",
                "View Details",
                "Requisition List Page",
                "Requisition List Page For Orders",
                "Requisition"
        );

        return mav;
    }

    @RequestMapping(value = RENDER_LIST_ARRIVAL_MAPPING, method = GET)
    public ModelAndView releaseListRender() {
        List<Order> orderList = fetchByState(OrderState.ORDERED);
        ModelAndView mav = createListModelMapping(
                orderList,
                ORDER_CONSIDERATION_PAGE,
                "/order-details/arrival/",
                "View Details",
                "Arrival List Page",
                "Arrival List Page For Orders",
                "Arrival"
        );
        return mav;
    }

    private ModelAndView createListModelMapping(List<Order> orderList, String jspPagePath, String detailsInfoLinkName, String anchorName, String pageTitle, String topHeading, String h1Placeholder) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("orders", orderList);
        modelMap.put("orderUrl", detailsInfoLinkName);
        modelMap.put("anchorName", anchorName);
        modelMap.put("pageTitle", pageTitle);
        modelMap.put("topHeading", topHeading);
        modelMap.put("h1Placeholder", h1Placeholder);
        ModelAndView mav = new ModelAndView(ORDER_CONSIDERATION_PAGE, modelMap);
        return mav;
    }


    private List<Order> fetchByState(OrderState orderState) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, BASE_NAME).concat("/").concat(orderState.ordinal() + "");
        ResponseEntity<List<Order>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Order>>() {
        });
        return response.getBody();
    }


    @RequestMapping(value = RENDER_LIST_USER_MAPPING, method = GET)
    public ModelAndView userListRender(@PathVariable(ID_STRING_LITERAL) Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, BASE_NAME).concat("/").concat("user").concat("/").concat(userId + "");
        ResponseEntity<List<Order>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Order>>() {
        });

        List<Order> requests = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requests", requests);
        modelMap.put("orderUrll", "/order-details/");
        modelMap.put("anchorName", "More Info");
        ModelAndView mav = new ModelAndView(ORDER_CONSIDERATION_PAGE, modelMap);
        return mav;

    }


    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Order> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(aLong), Order.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Order> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(aLong), Order.class);
        Order item = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, item);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, Order entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Order>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Order>>() {
        });
        ModelAndView mv = postListProcessing(response, "orders", LIST_VIEW_PAGE);
        return mv;
    }


    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Order... entity) {
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
    public ModelAndView createAction(Order entity, BindingResult bindingResult) {
        return null;
    }
}
