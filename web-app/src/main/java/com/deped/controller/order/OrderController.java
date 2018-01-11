package com.deped.controller.order;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.account.User;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.order.*;
import com.deped.model.search.OrderSearch;
import com.deped.model.supply.Supplier;
import com.deped.model.unit.Unit;
import com.deped.utils.SystemUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

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
    private static final String REPORT_LIST = BASE_NAME + URL_SEPARATOR + "report-list";

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;
    private static final String REPORT_LIST_VIEW_PAGE = BASE_SHOW_PAGE + "report" + LIST_PAGE;
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
        List<Order> orderList = fetchByState(OrderState.ORDERED, OrderState.PARTIALLY_ARRIVED);
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


    private List<Order> fetchByState(OrderState... orderState) {
        Integer ordinals[] = new Integer[orderState.length];
        for (int i = 0; i < orderState.length; i++) {
            ordinals[i] = orderState[i].ordinal();
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
        modelMap.put("orderUrl", "/order-details/");
        modelMap.put("anchorName", "More Info");
        ModelAndView mav = new ModelAndView(ORDER_CONSIDERATION_PAGE, modelMap);
        return mav;

    }

    @RequestMapping(value = REPORT_LIST, method = GET)
    public ModelAndView searchListRender(@ModelAttribute("orderSearch") OrderSearch entity, BindingResult bindingResult) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("userList", SharedData.getUsers(false));
        modelMap.put("schedules", Schedule.values());
        modelMap.put("orderStates", OrderState.values());
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("categoryList", SharedData.getCategories(false));
        modelMap.put("unitList", SharedData.getUnits(false));
        modelMap.put("orderDetailsStateList", OrderDetailsState.values());
        modelMap.put("suppliers", SharedData.getSuppliers(false));

        ModelAndView mav = new ModelAndView(REPORT_LIST_VIEW_PAGE, modelMap);
        return mav;
    }

    @RequestMapping(value = REPORT_LIST, method = POST, params = "web")
    public ModelAndView searchListAction(@Valid @ModelAttribute("orderSearch") OrderSearch entity, BindingResult bindingResult) {

        String strSuffix = "search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + strSuffix;
        List<Order> requestResult = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<Order>>() {
        });

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("orders", requestResult);
        modelMap.put("orderDetailsInfo", "/order-details/info/");
        ModelAndView mav = new ModelAndView(REPORT_LIST_VIEW_PAGE, modelMap);
        return mav;

    }

    @RequestMapping(value = REPORT_LIST, method = POST, params = "xml")
    public void searchXmlListAction(@Valid @ModelAttribute("orderSearch") OrderSearch entity, BindingResult bindingResult,
                                    HttpServletResponse response) {
        String disapprovedListStr = "search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<Order> requestResult = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<Order>>() {
        });
        XSSFWorkbook workbook = new XSSFWorkbook();

        for (Order or : requestResult) {
            XSSFSheet sheet = workbook.createSheet("Request #" + or.getOrderId());
            Object[][] dataTypes = {
                    /*{"Request ID", "Request Date", "Requested Personnel", "Personnel Message", "Admin Remark", "Item Type", "Request Status"},
                    {or.getRequestId(), or.getRequestDate(), String.format("%s %s", or.getUser().getLastName(), or.getUser().getFirstName()), or.getUserMessage(), or.getAdminNotice(), or.getItemType().getName(), or.getRequestStatus().getName()},
                    {"", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", ""}*/
            };

            int rowNumber = 0;
            rowNumber = createXml(dataTypes, sheet, rowNumber);

            List<OrderDetails> orderDetailsList = fetchOrderDetails(or.getOrderId());
            for (OrderDetails rd : orderDetailsList) {
                String consideredBy = rd.getConsideredByUser() != null ? String.format("%s %s", rd.getConsideredByUser().getLastName(), rd.getConsideredByUser().getFirstName()) : "";
                //String issuedBy = rd.getIssuedByUser() != null ? String.format("%s %s", rd.getIssuedByUser().getLastName(), rd.getIssuedByUser().getFirstName()) : "";

                Object[][] dataTypes2 = {
                        {"Request ID", "Item", "Request Quantity", "Approved Quantity", "Consideration Date",
                                "Release Date", "Supply Office Remark", "Considered By", "Issued By", "Request Details Status",
                                "Disapproval Message", "Cancellation Reason"}
                                /*,
                        {rd.getRequest().getRequestId(), rd.getRequestQuantity(), rd.getApprovedQuantity(),
                                rd.getApprovalDisapprovalDate(), rd.getReleaseDate(), rd.getSupplyOfficeRemark(), consideredBy, issuedBy,
                                rd.getRequestDetailsStatus() != null ? rd.getRequestDetailsStatus().getName() : "", rd.getDisapprovalMessage(),
                                rd.getCancellationReason()
                        }
                        ,*/

                };

                rowNumber = createXml(dataTypes2, sheet, rowNumber);
            }
        }


        String mimeType = "application/xml";

        response.setContentType(mimeType);
//        response.setContentLength(workbook.get);

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "request-report.xlsx");
        response.setHeader(headerKey, headerValue);

        OutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            workbook.write(outStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private int createXml(Object[][] datatypes, XSSFSheet sheet, int rowNumber) {

        System.out.println("Creating excel");

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNumber++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        return rowNumber;
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

    public List<User> fetchUser(String text) {
        List<User> userList = new ArrayList<>();
        String[] usersStr = text.split(",");

        for (String itStr : usersStr) {
            User dummy = new User();
            dummy.setUsername(itStr);
            User discoveredUser = SystemUtils.findElementInList(SharedData.getUsers(false), dummy);
            if (discoveredUser != null)
                userList.add(discoveredUser);
        }

        return userList;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, "requiredDate", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });


        //==========================
        binder.registerCustomEditor(List.class, "users", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(fetchUser(text));
            }
        });

        binder.registerCustomEditor(List.class, "consideredByUsers", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(fetchUser(text));
            }
        });

        binder.registerCustomEditor(List.class, "orderedByUsers", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(fetchUser(text));
            }
        });

        binder.registerCustomEditor(List.class, "receivedByUsers", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(fetchUser(text));
            }
        });
        //===========

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(List.class, "items", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<Item> itemList = new ArrayList<>();
                String[] itemsStr = text.split(",");
                if (itemsStr == null || itemsStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : itemsStr) {
                        Item discoveredItem = fetchItemByStringId(itStr);
                        if (discoveredItem != null)
                            itemList.add(discoveredItem);
                    }

                    setValue(itemList);
                }
            }
        });

        binder.registerCustomEditor(List.class, "categories", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<Category> categoryList = new ArrayList<>();
                String[] categoriesStr = text.split(",");
                if (categoriesStr == null || categoriesStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : categoriesStr) {
                        Category discoveredCategory = fetchCategoryByStringId(itStr);
                        if (discoveredCategory != null)
                            categoryList.add(discoveredCategory);
                    }

                    setValue(categoryList);
                }
            }
        });

        binder.registerCustomEditor(List.class, "units", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<Unit> unitList = new ArrayList<>();
                String[] unitStr = text.split(",");
                if (unitStr == null || unitStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : unitStr) {
                        Unit discoveredUnit = fetchUnitByStringId(itStr);
                        if (discoveredUnit != null)
                            unitList.add(discoveredUnit);
                    }

                    setValue(unitList);
                }
            }
        });

        binder.registerCustomEditor(List.class, "suppliers", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<Supplier> supplierList = new ArrayList<>();
                String[] supplierStr = text.split(",");
                if (supplierStr == null || supplierStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : supplierStr) {
                        Supplier discoveredSupplier = fetchSupplierByStringId(itStr);
                        if (discoveredSupplier != null)
                            supplierList.add(discoveredSupplier);
                    }

                    setValue(supplierList);
                }
            }
        });
    }

    @Override
    public ModelAndView createAction(Order entity, BindingResult bindingResult) {
        return null;
    }
}
