package com.deped.controller.order;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.form.OrderDetailsForm;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.order.Order;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderState;
import com.deped.model.pack.Pack;
import com.deped.model.supply.Supplier;
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
public class OrderDetailsController extends AbstractMainController<OrderDetails, Long> {
    private static final String BASE_NAME = "order-details";
    private static final String BASKET = "orderDetailsMap-OrderNo";
    private static final String ORDER = "orderSessionNo";

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
    public ModelAndView renderCreatePage(@ModelAttribute("order") Order order, @PathVariable(ID_STRING_LITERAL) Long orderId, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (order == null || order.getOrderId() == null || order.getOrderId() != orderId) {
            RestTemplate restTemplate = new RestTemplate();
            String restUrl = String.format(FETCH_BY_ID_URL, "order", orderId);
            ResponseEntity<Order> response = restTemplate.getForEntity(restUrl, Order.class);
            order = response.getBody();

            if (order == null) {
                final String redirectUrl = "redirect:/order/create";
                return new ModelAndView(redirectUrl);
            }


            Set<OrderDetails> orderDetailsList = order.getOrderDetails();
            if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                httpSession.setAttribute(BASKET + order.getOrderId(), putOrderDetailsListIntoMap(orderDetailsList));
            }
        }

        if (httpSession.getAttribute(ORDER + orderId) == null)
            httpSession.setAttribute(ORDER + orderId, order);

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("orderId", orderId);
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("packs", SharedData.getPacks(false));
        modelMap.put("categories", SharedData.getCategories(false));
        modelMap.put("suppliers", SharedData.getSuppliers(false));
        modelMap.put("orderDetail", new OrderDetails());

        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;

    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithRedirect(@Valid @ModelAttribute("orderDetail") OrderDetails entity, @Valid @ModelAttribute("orderEntity") Order orderEntity, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        Order order = entity.getOrder();
        if (order == null) {
            return null;
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
        //redirectAttributes.addFlashAttribute("order", order);
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
        Object object = httpSession.getAttribute(BASKET + orderId);
        if (object == null) {
            String redirectUrl = String.format("redirect:/order-details/create/%d", orderId);
            return new ModelAndView(redirectUrl);
        }

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("orderId", orderId);
        modelMap.put("orderDetailsForm", new OrderDetailsForm());
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("packs", SharedData.getPacks(false));
        modelMap.put("categories", SharedData.getCategories(false));
        modelMap.put("suppliers", SharedData.getSuppliers(false));
        ModelAndView mav = new ModelAndView("pages/basket/basket", modelMap);
        return mav;
    }

    @RequestMapping(value = BASKET_VIEW_PAGE, method = POST)
    public ModelAndView actionOnBasket(@PathVariable(ID_STRING_LITERAL) Long orderId, @RequestParam ActionParam actionParam, @ModelAttribute("orderDetailsForm") OrderDetailsForm orderDetailsForm, HttpSession httpSession) {
        Object object = httpSession.getAttribute(BASKET + orderId);
        String orderPage = String.format("redirect:/order-details/create/%d", orderId);

        if (object == null) {
            return new ModelAndView(orderPage);
        }
        Map<String, OrderDetails> sessionOrderDetailsMap = (Map<String, OrderDetails>) object;
        Map<String, OrderDetails> formOrderDetailsMap = orderDetailsForm.getMap();

        String message = null;
        switch (actionParam) {
            case UPDATE_ALL:
                httpSession.setAttribute(BASKET + orderId, formOrderDetailsMap);
                return new ModelAndView("redirect:/order-details/create/%d" + orderId);
            case DELETE_ALL:
                sessionOrderDetailsMap.clear();
                return new ModelAndView(orderPage);
            case SAVE_ALL:
                //TODO save inside database and go to home page
                boolean isSaved = saveOrderDetails(formOrderDetailsMap, true);
                if (isSaved) {
                    message = SUCCESS_MESSAGE;
                    httpSession.removeAttribute(BASKET + orderId);
                    return new ModelAndView("redirect:/dashboard");
                } else {
                    message = FAILURE_MESSAGE;
                    return new ModelAndView("pages/basket/basket", "failureMessage", message);
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
                    return new ModelAndView("pages/basket/basket", "failureMessage", message);
                }
        }

        return null;
    }

    private boolean saveOrderDetails(Map<String, OrderDetails> map, boolean isForSaveOnly) {
        List<OrderDetails> list = new ArrayList<>(map.values());
        for (OrderDetails od : list) {
            if (isForSaveOnly) {
                od.setOrderState(OrderState.REGISTERED);
            } else { //is for order
                od.setOrderState(OrderState.ORDERING);
            }
        }

        OrderDetails[] orderDetails = list.toArray(new OrderDetails[list.size()]);
        ResponseEntity<Boolean> response = makeCreateAllRestRequest(orderDetails, BASE_NAME, HttpMethod.POST, OrderDetails.class);
        Boolean body = response.getBody();
        if (body == null) {
            return false;
        }
        return body;

    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, @ModelAttribute("orderDetailsList") OrderDetails entity) {
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<OrderDetails>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<OrderDetails>>() {
        });
        ModelAndView mv = listProcessing(response, "orders", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Pack.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    try {
                        Long packId = Long.parseLong(text);
                        Pack pack = new Pack();
                        pack.setPackId(packId);

                        List<Pack> packs = SharedData.getPacks(false);
                        Pack discoveredPack = SystemUtils.findElementInList(packs, pack);
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
                        Long categoryId = Long.parseLong(text);
                        Category category = new Category();
                        category.setCategoryId(categoryId);

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


    private synchronized Map<String, OrderDetails> putOrderDetailsListIntoMap(Set<OrderDetails> orderDetailsSet) {
        Map<String, OrderDetails> orderDetailsMap = new HashMap<>();
        for (Iterator<OrderDetails> it = orderDetailsSet.iterator(); it.hasNext(); ) {
            OrderDetails orderDetails = it.next();
            orderDetailsMap.put(SystemUtils.getRandomString(), orderDetails);
        }
        return orderDetailsMap;
    }


    @Override
    public ModelAndView renderCreatePage(OrderDetails entity) {
        return null;
    }

    @Override
    public ModelAndView createAction(OrderDetails entity) {
        return null;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid OrderDetails... entity) {
        return null;
    }

}