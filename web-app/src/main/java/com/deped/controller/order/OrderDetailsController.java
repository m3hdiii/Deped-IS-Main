package com.deped.controller.order;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.category.Category;
import com.deped.model.order.Order;
import com.deped.model.order.OrderDetails;
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
import org.springframework.web.context.request.WebRequest;
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
public class OrderDetailsController extends AbstractMainController<ArrayList<OrderDetails>, Long> {
    private static final String BASE_NAME = "order-details";
    private static final String BASKET = "orderBasket-OrderNo";
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


    @Override
    public ModelAndView renderCreatePage(ArrayList<OrderDetails> entity) {
        return null;
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


    @Override
    public ModelAndView createAction(ArrayList<OrderDetails> entity) {
        return null;
    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithRedirect(@Valid @ModelAttribute("orderDetail") OrderDetails entity, @Valid @ModelAttribute("orderEntity") Order orderEntity, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

        Order order = entity.getOrder();
        if (order == null) {
            return null;
        }

        Object basketInfo = httpSession.getAttribute(BASKET + order.getOrderId());
        Map<Integer, OrderDetails> orderDetailsMap;
        if (basketInfo == null) {
            orderDetailsMap = new HashMap<>();
        } else {
            orderDetailsMap = (HashMap<Integer, OrderDetails>) basketInfo;
        }
        Integer size = orderDetailsMap.size();
        orderDetailsMap.put(++size, entity);
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
            String redirectUrl = String.format("redirect:/order-details/%d", orderId);
            return new ModelAndView(redirectUrl);
        }
        ModelAndView mav = new ModelAndView();
        return null;
    }

    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePageWithFlashAttribute(@PathVariable(ID_STRING_LITERAL) Long aLong) {
//        ResponseEntity<List<OrderDetails>> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, List<OrderDetails>.class);
//        List<OrderDetails> item = response.getBody();
//        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, item);
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(Long aLong, ArrayList<OrderDetails> entity) {
        return null;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<ArrayList<OrderDetails>>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<ArrayList<OrderDetails>>>() {
        });
        ModelAndView mv = listProcessing(response, "orders", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid ArrayList<OrderDetails>... entity) {
        return null;
    }


//            modelMap.put("orderId", orderId);
//        modelMap.put("itemList", fetchAllItems());
//        modelMap.put("packs", fetchAllPacks());
//        modelMap.put("categories", fetchAllCategories());
//        modelMap.put("suppliers", fetchAllSuppliers());
//        modelMap.put("orderDetail", new OrderDetails());

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Pack.class, "pack", new PropertyEditorSupport() {
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

        binder.registerCustomEditor(Supplier.class, "supplier", new PropertyEditorSupport() {
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

        binder.registerCustomEditor(Category.class, "category", new PropertyEditorSupport() {
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

        binder.registerCustomEditor(Order.class, "order", new PropertyEditorSupport() {
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
    }


    private synchronized Map<String, OrderDetails> putOrderDetailsListIntoMap(Set<OrderDetails> orderDetailsSet) {
        Map<String, OrderDetails> orderDetailsMap = new HashMap<>();
        int i = 0;
        for (Iterator<OrderDetails> it = orderDetailsSet.iterator(); it.hasNext(); i++) {
            OrderDetails orderDetails = it.next();
            orderDetailsMap.put(SystemUtils.getRandomString(), orderDetails);
        }
        return orderDetailsMap;
    }
}