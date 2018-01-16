package com.deped.controller.itemdetails;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.form.ItemDetailsBeanForm;
import com.deped.form.ItemDetailsForm;
import com.deped.form.OrderDetailsForm;
import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.items.features.Colour;
import com.deped.model.items.features.Condition;
import com.deped.model.items.features.EquipmentAvailability;
import com.deped.model.items.features.Material;
import com.deped.model.order.CaptureInfo;
import com.deped.model.order.OrderDetails;
import com.deped.model.search.BorrowHistorySearch;
import com.deped.model.search.BorrowSearch;
import com.deped.utils.SystemUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.*;
import java.beans.PropertyEditorSupport;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ItemDetailsController extends AbstractMainController<ItemDetails, String> {

    private static final String BASE_NAME = "item-details";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING_NO_ID = BASE_NAME + "/update";
    private static final String RENDER_RETURN_MAPPING = BASE_NAME + "/return";
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String RETURN_VIEW_PAGE = BASE_SHOW_PAGE + "return-" + BASE_NAME;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;
    private static final String INSERT_DATA = BASE_NAME + URL_SEPARATOR + "insert-data";
    private static final String REPORT_LIST = BASE_NAME + URL_SEPARATOR + "report-list";
    private static final String REPORT_HISTORY_LIST = BASE_NAME + URL_SEPARATOR + "history-report-list";
    private static final String REPORT_LIST_VIEW_PAGE = BASE_SHOW_PAGE + "report" + LIST_PAGE;
    private static final String REPORT_HISTORY_LIST_VIEW_PAGE = BASE_SHOW_PAGE + "history-report" + LIST_PAGE;

    private Validator validator;

    public ItemDetailsController() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute ItemDetails entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @RequestMapping(value = "/item-details/create/{requestId}/{itemName}/{quantity}", method = GET)
    public ModelAndView createMultipleItemDetails(@PathVariable("requestId") Long requestId, @PathVariable("itemName") String itemName, @PathVariable("quantity") Integer quantity, HttpSession session) {
        ModelAndView mav = createModelAndView(itemName, quantity, null);
        return mav;

    }

    private ModelAndView createModelAndView(String itemName, Integer quantity, ItemDetailsForm itemDetailsForm) {
        if (itemDetailsForm == null) {
            Item discoveredItem = fetchItemByName(itemName);
            ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
            for (int i = 0; i < quantity; i++) {
                ItemDetails itemDetails = new ItemDetails();
                itemDetails.setItem(discoveredItem);
                itemDetailsList.add(itemDetails);
            }
            itemDetailsForm = new ItemDetailsForm(itemDetailsList);
        }


        return new ModelAndView("pages/item-details/create-item-details", "itemDetailsForm", itemDetailsForm);
    }

    @RequestMapping(value = "/item-details/create/{requestId}/{itemName}/{quantity}", method = POST)
    public ModelAndView createMultipleItemDetails(@PathVariable("requestId") Long requestId, @PathVariable("itemName") String itemName, @PathVariable("quantity") Integer quantity, @ModelAttribute("itemDetailsForm") ItemDetailsForm itemDetailsForm, BindingResult bindingResult, HttpSession session) {

        checkItemDetailsList(itemDetailsForm.getItemDetailsList(), bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView mav = createModelAndView(itemName, quantity, itemDetailsForm);
            return mav;
        }

        session.setAttribute(String.format("ItemDetails%d%s%d", requestId, itemName, quantity), itemDetailsForm.getItemDetailsList());

        ModelAndView mav = new ModelAndView("redirect:/request-details/issue/" + requestId);

        return mav;
    }

    private void checkItemDetailsList(List<ItemDetails> itemDetailsList, BindingResult result) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        for (ItemDetails itemDetails : itemDetailsList) {

            Set<ConstraintViolation<ItemDetails>> violations = validator.validate(itemDetails);

            for (ConstraintViolation<ItemDetails> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                result.addError(new FieldError("ItemDetails", propertyPath, message));
            }
        }
    }

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createAction(@Valid ItemDetails entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(CREATE_VIEW_PAGE);
        }

        entity.setCreationDate(new Date());
        ResponseEntity<ItemDetails> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, ItemDetails.class);
        ModelAndView mv = postCreateProcessing(ItemDetails.class, response, CREATE_VIEW_PAGE, BASE_NAME, entity, new ItemDetails(), bindingResult, "item details");
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) String officeSerialNumber) {
        ResponseEntity<ItemDetails> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, officeSerialNumber, ItemDetails.class);
        ModelAndView mv = renderProcessing(response, officeSerialNumber, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String officeSerialNumber) {
        return null;
    }


    @RequestMapping(value = RENDER_UPDATE_MAPPING_NO_ID, method = GET)
    public ModelAndView renderUpdateNoIdPage() {
        return new ModelAndView(UPDATE_VIEW_PAGE);
    }

    @RequestMapping(value = RENDER_UPDATE_MAPPING_NO_ID, method = POST, params = "fetchObject")
    public ModelAndView renderUpdateNoIdAction(@RequestParam("searchKeyword") String keyword) {
        ResponseEntity<ItemDetails> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, keyword, ItemDetails.class);
        ItemDetails itemDetails = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("availabilities", EquipmentAvailability.values());
        modelMap.put("conditions", Condition.values());
        modelMap.put("itemDetailsResult", itemDetails);
        if (itemDetails == null) {
            modelMap.put("nothingFound", true);
        }
        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING_NO_ID, method = POST, params = "update-info")
    public ModelAndView updateAction(String officeSerialNumber, @ModelAttribute("itemDetailsResult") ItemDetails entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, "itemDetailsResult", entity);
        }

        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, ItemDetails.class);
        ModelAndView mv = postUpdateProcessing(ItemDetails.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new ItemDetails(), bindingResult, "item details");
        return mv;
    }

    @RequestMapping(value = RENDER_RETURN_MAPPING, method = GET)
    public ModelAndView renderReturnNoIdPage() {
        return new ModelAndView(RETURN_VIEW_PAGE);
    }

    @RequestMapping(value = RENDER_RETURN_MAPPING, method = POST, params = "fetchObject")
    public ModelAndView renderReturnNoIdAction(@RequestParam("searchKeyword") String keyword) {
        ResponseEntity<ItemDetails> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, keyword, ItemDetails.class);
        ItemDetails itemDetails = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("itemDetailsReturn", itemDetails);
        if (itemDetails == null) {
            modelMap.put("nothingFound", true);
        }
        return new ModelAndView(RETURN_VIEW_PAGE, modelMap);
    }

    @RequestMapping(value = RENDER_RETURN_MAPPING, method = POST, params = "return-action")
    public ModelAndView returnAction(String officeSerialNumber, @ModelAttribute("itemDetailsReturn") ItemDetails entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(RETURN_VIEW_PAGE, "itemDetailsResult", entity);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(entity, headers);
        ResponseEntity<Response> response = restTemplate.exchange(BASE_URL.concat("item-details/return/").concat(entity.getOfficeSerialNo()), HttpMethod.POST, httpEntity, Response.class);
        return null;
    }


    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<ItemDetails>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<ItemDetails>>() {
        });
        ModelAndView mv = postListProcessing(response, "itemDetailsList", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid ItemDetails... entity) {
        return null;
    }


    @RequestMapping(value = INSERT_DATA, method = RequestMethod.GET)
    public ModelAndView captureData() {

        List<CaptureInfo> captureInfoList = fetchCaptureInfoByItemTypes(new ItemType[]{ItemType.EQUIPMENT});

        ModelAndView mav = createInsertDataModelAndView(captureInfoList);

        return mav;
    }

    @RequestMapping(value = INSERT_DATA, method = RequestMethod.POST)
    public ModelAndView captureDataAction(@Valid @ModelAttribute("itemDetailsBeanForm") ItemDetailsBeanForm itemDetailsForm, BindingResult bindingResult) {

        List<ItemDetails> subLIst = fetchFilledObjects(itemDetailsForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView mav = captureData();
            return mav;
        }

        String restUrl;
        restUrl = String.format(CREATE_ALL_URL, BASE_NAME);


        ItemDetails[] requestDetails = subLIst.toArray(new ItemDetails[subLIst.size()]);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, requestDetails, HttpMethod.POST, ItemDetails.class);
        Response body = response.getBody();
        ModelAndView mav = new ModelAndView("redirect:/item-details/list");
        return mav;
    }

    private List<ItemDetails> fetchFilledObjects(ItemDetailsBeanForm itemDetailsForm, BindingResult bindingResult) {
        List<ItemDetails> subList = new ArrayList<>();
        List<CaptureInfo> capInfoList = itemDetailsForm.getCaptureInfoList();

        for (CaptureInfo ci : capInfoList) {
            List<ItemDetails> itemDetailsList = ci.getItemDetailsList();
            if (itemDetailsList != null) {
                for (ItemDetails id : itemDetailsList) {
                    if (id.getOfficeSerialNo() != null && !id.getOfficeSerialNo().equals("")) {
                        subList.add(id);
                        addError(id, bindingResult);
                    }
                }
            }
        }

        return subList;
    }

    public BindingResult addError(ItemDetails itemDetails, BindingResult bindingResult) {
        Set<ConstraintViolation<ItemDetails>> violations = validator.validate(itemDetails);

        for (ConstraintViolation<ItemDetails> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            bindingResult.addError(new FieldError("itemDetails", propertyPath, "Invalid " + propertyPath + "(" + message + ")"));
        }
        return bindingResult;
    }

    private List<CaptureInfo> fetchCaptureInfoByItemTypes(ItemType[] itemTypes) {
        Integer ordinals[] = new Integer[itemTypes.length];
        for (int i = 0; i < itemTypes.length; i++) {
            ordinals[i] = itemTypes[i].ordinal();
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer[]> httpEntity = new HttpEntity<>(ordinals, headers);
        String format = AbstractMainController.BASE_URL.concat("%s%s");
        String restUrl = String.format(format, "item-details", "/capture-info");
        ResponseEntity<List<CaptureInfo>> responseDetails = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<CaptureInfo>>() {
        });
        List<CaptureInfo> requestDetailsList = responseDetails.getBody();
        return requestDetailsList;
    }

    private ModelAndView createInsertDataModelAndView(List<CaptureInfo> captureInfoList) {
        Date d = new Date();
        for (int i = 0; i < captureInfoList.size(); i++) {
            CaptureInfo ci = captureInfoList.get(i);
            List<ItemDetails> itemDetailsList = null;
            if (ci.getNumberOfRemainingCapturedItems() > 0) {
                itemDetailsList = new ArrayList<>();
                for (int j = 0; j < ci.getNumberOfRemainingCapturedItems(); j++) {
                    ItemDetails id = new ItemDetails();
                    Item item = new Item();
                    item.setName(ci.getItemName());
                    id.setItem(item);
                    id.setCreationDate(d);

                    itemDetailsList.add(id);
                }
                ci.setItemDetailsList(itemDetailsList);
            }
        }

        ItemDetailsBeanForm itemDetailsBeanForm = new ItemDetailsBeanForm(captureInfoList);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("itemDetailsBeanForm", itemDetailsBeanForm);
        modelMap.put("colours", Colour.values());
        modelMap.put("materials", Material.values());
        modelMap.put("availabilities", EquipmentAvailability.values());
        modelMap.put("conditions", Condition.values());
        return new ModelAndView("pages/item-details/insert-item-info", modelMap);
    }

    private ModelAndView insertEquipmentsInformation2(OrderDetailsForm orderDetailsForm, HttpSession session) {

        Collection<OrderDetails> orderDetailsList = orderDetailsForm.getMap().values();
        List<OrderDetails> orderDetailsSubList = new ArrayList<>();
        for (OrderDetails od : orderDetailsList) {
            if (od.getItem().getItemType() == ItemType.EQUIPMENT) {
                orderDetailsSubList.add(od);
            }
        }

        if (orderDetailsSubList.isEmpty()) {
            return null;
        }

        String attName = "";/*ARRIVAL_OR_PARTIAL_ARRIVAL_BASKET + orderId;*/
        session.setAttribute(attName, orderDetailsForm);
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Item.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Item discoveredItem = fetchItemByStringId(text);
                setValue(discoveredItem);
            }
        });

        binder.registerCustomEditor(Date.class, "creationDate", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = REPORT_LIST, method = GET)
    public ModelAndView searchListRender(@ModelAttribute("borrowSearch") BorrowSearch entity, BindingResult bindingResult) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("colors", Colour.values());
        modelMap.put("conditions", Condition.values());
        modelMap.put("equipmentAvailabilities", EquipmentAvailability.values());
        modelMap.put("materials", Material.values());
        modelMap.put("itemList", SharedData.getEquipment(false));
        modelMap.put("userList", SharedData.getUsers(false));

        ModelAndView mav = new ModelAndView(REPORT_LIST_VIEW_PAGE, modelMap);
        return mav;
    }


    @RequestMapping(value = REPORT_LIST, method = POST, params = "web")
    public ModelAndView searchListAction(@Valid @ModelAttribute("borrowSearch") BorrowSearch entity, BindingResult bindingResult) {

        String disapprovedListStr = "search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<ItemDetails> itemDetailsList = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<ItemDetails>>() {
        });

        ModelAndView mav = searchListRender(entity, bindingResult);
        mav.addObject("itemDetailsList", itemDetailsList);
        return mav;

    }

    @RequestMapping(value = REPORT_LIST, method = POST, params = "xml")
    public void searchXmlListAction(@Valid @ModelAttribute("requestSearch") BorrowSearch entity, BindingResult bindingResult, HttpServletRequest request,
                                    HttpServletResponse response) {
        String disapprovedListStr = "search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<ItemDetails> itemDetailsList = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<ItemDetails>>() {
        });

        XSSFWorkbook workbook = new XSSFWorkbook();

//        for (ItemDetails req : itemDetailsList) {
//            XSSFSheet sheet = workbook.createSheet("Request #" + req.getRequestId());
//            Object[][] dataTypes = {
//                    {"Request ID", "Request Date", "Requested Personnel", "Personnel Message", "Admin Remark", "Item Type", "Request Status"},
//                    {req.getRequestId(), req.getRequestDate(), String.format("%s %s", req.getUser().getLastName(), req.getUser().getFirstName()), req.getUserMessage(), req.getAdminNotice(), req.getItemType().getName(), req.getRequestStatus().getName()},
//                    {"", "", "", "", "", "", ""},
//                    {"", "", "", "", "", "", ""}
//            };
//
//            int rowNumber = 0;
//            rowNumber = createXml(dataTypes, sheet, rowNumber);
//
//            List<RequestDetails> requestDetailsList = fetchRequestDetails(req.getRequestId());
//            for (RequestDetails rd : requestDetailsList) {
//                String consideredBy = rd.getConsideredByUser() != null ? String.format("%s %s", rd.getConsideredByUser().getLastName(), rd.getConsideredByUser().getFirstName()) : "";
//                String issuedBy = rd.getIssuedByUser() != null ? String.format("%s %s", rd.getIssuedByUser().getLastName(), rd.getIssuedByUser().getFirstName()) : "";
//
//                Object[][] dataTypes2 = {
//                        {"Request ID", "Item", "Request Quantity", "Approved Quantity", "Consideration Date",
//                                "Release Date", "Supply Office Remark", "Considered By", "Issued By", "Request Details Status",
//                                "Disapproval Message", "Cancellation Reason"},
//                        {rd.getRequest().getRequestId(), rd.getRequestQuantity(), rd.getApprovedQuantity(),
//                                rd.getApprovalDisapprovalDate(), rd.getReleaseDate(), rd.getSupplyOfficeRemark(), consideredBy, issuedBy,
//                                rd.getRequestDetailsStatus() != null ? rd.getRequestDetailsStatus().getName() : "", rd.getDisapprovalMessage(),
//                                rd.getCancellationReason()
//                        },
//
//                };
//
//                rowNumber = createXml(dataTypes2, sheet, rowNumber);
//            }
//        }
    }

    @RequestMapping(value = REPORT_HISTORY_LIST, method = GET)
    public ModelAndView historySearchListRender(@ModelAttribute("borrowHistorySearch") BorrowHistorySearch entity) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("userList", SharedData.getUsers(false));
        ModelAndView mav = new ModelAndView(REPORT_HISTORY_LIST_VIEW_PAGE, modelMap);
        return mav;
    }

    @RequestMapping(value = REPORT_HISTORY_LIST, method = POST, params = "web")
    public ModelAndView historySearchListAction(@Valid @ModelAttribute("borrowHistorySearch") BorrowHistorySearch entity, BindingResult bindingResult) {

        String disapprovedListStr = "history-search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<BorrowItem> borrowItemList = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<BorrowItem>>() {
        });

        ModelAndView mav = historySearchListRender(entity);
        mav.addObject("borrowItemList", borrowItemList);
        return mav;

    }

    @RequestMapping(value = REPORT_HISTORY_LIST, method = POST, params = "xml")
    public void historySearchXmlListAction(@Valid @ModelAttribute("borrowHistorySearch") BorrowHistorySearch entity, BindingResult bindingResult, HttpServletRequest request,
                                           HttpServletResponse response) {
        String disapprovedListStr = "history-search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        /*List<BorrowItem> borrowItemList = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<BorrowItem>>() {
        });*/

        XSSFWorkbook workbook = new XSSFWorkbook();

//        for (ItemDetails req : itemDetailsList) {
//            XSSFSheet sheet = workbook.createSheet("Request #" + req.getRequestId());
//            Object[][] dataTypes = {
//                    {"Request ID", "Request Date", "Requested Personnel", "Personnel Message", "Admin Remark", "Item Type", "Request Status"},
//                    {req.getRequestId(), req.getRequestDate(), String.format("%s %s", req.getUser().getLastName(), req.getUser().getFirstName()), req.getUserMessage(), req.getAdminNotice(), req.getItemType().getName(), req.getRequestStatus().getName()},
//                    {"", "", "", "", "", "", ""},
//                    {"", "", "", "", "", "", ""}
//            };
//
//            int rowNumber = 0;
//            rowNumber = createXml(dataTypes, sheet, rowNumber);
//
//            List<RequestDetails> requestDetailsList = fetchRequestDetails(req.getRequestId());
//            for (RequestDetails rd : requestDetailsList) {
//                String consideredBy = rd.getConsideredByUser() != null ? String.format("%s %s", rd.getConsideredByUser().getLastName(), rd.getConsideredByUser().getFirstName()) : "";
//                String issuedBy = rd.getIssuedByUser() != null ? String.format("%s %s", rd.getIssuedByUser().getLastName(), rd.getIssuedByUser().getFirstName()) : "";
//
//                Object[][] dataTypes2 = {
//                        {"Request ID", "Item", "Request Quantity", "Approved Quantity", "Consideration Date",
//                                "Release Date", "Supply Office Remark", "Considered By", "Issued By", "Request Details Status",
//                                "Disapproval Message", "Cancellation Reason"},
//                        {rd.getRequest().getRequestId(), rd.getRequestQuantity(), rd.getApprovedQuantity(),
//                                rd.getApprovalDisapprovalDate(), rd.getReleaseDate(), rd.getSupplyOfficeRemark(), consideredBy, issuedBy,
//                                rd.getRequestDetailsStatus() != null ? rd.getRequestDetailsStatus().getName() : "", rd.getDisapprovalMessage(),
//                                rd.getCancellationReason()
//                        },
//
//                };
//
//                rowNumber = createXml(dataTypes2, sheet, rowNumber);
//            }
//        }
    }


    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, "creationDateFrom", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "creationDateTo", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "borrowDateFrom", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "borrowDateTo", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "returnDateFrom", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "returnDateTo", new PropertyEditorSupport() {
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

        binder.registerCustomEditor(List.class, "ownBy", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(fetchUser(text));
            }
        });
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
}