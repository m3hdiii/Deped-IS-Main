package com.deped.controller.order;

import com.deped.controller.AbstractMainController;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.order.Order;
import com.deped.model.order.OrderDetails;
import com.deped.model.pack.Pack;
import com.deped.model.supply.Supplier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static final String Order = "orderDetailsBasket";

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


    @Override
    public ModelAndView renderCreatePage(ArrayList<OrderDetails> entity) {
        return null;
    }

    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute("order") Order order, @PathVariable(ID_STRING_LITERAL) Long orderId, final RedirectAttributes redirectAttributes) {

        if (order.getOrderId() == null) {
            RestTemplate restTemplate = new RestTemplate();
            String restUrl = String.format(FETCH_BY_ID_URL, "order", orderId);
            ResponseEntity<Order> response = restTemplate.getForEntity(restUrl, Order.class);
            order = response.getBody();
            if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
                redirectAttributes.addFlashAttribute("order", response.getBody());
                ModelAndView mav = new ModelAndView();
                final String redirectUrl = String.format("redirect:/order-details/update/%d", order.getOrderId());
                mav.setViewName(redirectUrl);
                return mav;
            }
        }

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("relatedOrder", order);
        modelMap.put("itemList", fetchAllItems());
        modelMap.put("packs", fetchAllPacks());
        modelMap.put("categories", fetchAllCategories());
        modelMap.put("suppliers", fetchAllSuppliers());
        modelMap.put("orderDetail", new OrderDetails());

        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        return mv;
    }


    @Override
    public ModelAndView createAction(ArrayList<OrderDetails> entity) {
        return null;
    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithRedirect(@Valid @ModelAttribute("orderDetail") OrderDetails entity, final RedirectAttributes redirectAttributes, HttpSession httpSession) {

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
        orderDetailsMap.put(size, entity);
        httpSession.setAttribute((BASKET + order.getOrderId()), orderDetailsMap);

        return null;
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

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Pack.class, "pack", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    try {
                        Long packId = Long.parseLong(text);
                        Pack pack = new Pack();
                        pack.setPackId(packId);
                        setValue(pack);
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
                        setValue(supplier);
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
                        setValue(category);
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
                    try {
                        Long orderId = Long.parseLong(text);
                        Order order = new Order();
                        order.setOrderId(orderId);
                        setValue(order);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        setValue(null);
                    }
                }
            }
        });
    }


    private List<Item> fetchAllItems() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "item");
        ResponseEntity<List<Item>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Item>>() {
        });
        return response.getBody();
    }

    private List<Pack> fetchAllPacks() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "pack");
        ResponseEntity<List<Pack>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Pack>>() {
        });
        return response.getBody();
    }

    private List<Category> fetchAllCategories() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "category");
        ResponseEntity<List<Category>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Category>>() {
        });
        return response.getBody();
    }

    private List<Supplier> fetchAllSuppliers() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "supplier");
        ResponseEntity<List<Supplier>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Supplier>>() {
        });
        return response.getBody();
    }


}