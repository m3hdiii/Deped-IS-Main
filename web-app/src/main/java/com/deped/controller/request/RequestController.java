package com.deped.controller.request;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.model.account.User;
import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.model.request.Request;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.request.RequestStatus;
import com.deped.model.search.RequestSearch;
import com.deped.utils.SystemUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RequestController extends AbstractMainController<Request, Long> {
    private enum ReportType {
        WEB, XML
    }

    private static final String BASE_NAME = "request";

    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String RENDER_UPDATE_MAPPING = BASE_NAME + RENDER_UPDATE_PATTERN;
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_APPROVAL_MAPPING = BASE_NAME + "/approval-list";
    private static final String RENDER_LIST_RELEASE_MAPPING = BASE_NAME + "/release-list";
    private static final String RENDER_LIST_BORROW_MAPPING = BASE_NAME + "/borrow-list";
    private static final String RENDER_LIST_SUMMARY_MAPPING = BASE_NAME + "/summary";
    private static final String RENDER_LIST_USER_MAPPING = BASE_NAME + FETCH_PATTERN + "/user" + FETCH_BY_ID_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String REPORT_LIST = BASE_NAME + URL_SEPARATOR + "report-list";
    private static final String REPORT_LIST_XML = BASE_NAME + URL_SEPARATOR + "report-xml-list";

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;
    private static final String REPORT_LIST_VIEW_PAGE = BASE_SHOW_PAGE + "report" + LIST_PAGE;

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

    @RequestMapping(value = RENDER_LIST_BORROW_MAPPING, method = GET)
    public ModelAndView BorrowListRender() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = BASE_URL + String.format("%s/fetch-considered-equipment", BASE_NAME);
        ResponseEntity<List<Request>> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Request>>() {
        });

        List<Request> requests = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requests", requests);
        modelMap.put("requestUrl", "/request-details/borrow/");
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

    @RequestMapping(value = REPORT_LIST, method = GET)
    public ModelAndView searchListRender(@ModelAttribute("requestSearch") RequestSearch entity, BindingResult bindingResult) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("itemTypes", ItemType.values());
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("userList", SharedData.getUsers(false));
        modelMap.put("statuses", RequestStatus.values());
        modelMap.put("detailsStatuses", RequestDetailsStatus.values());

        ModelAndView mav = new ModelAndView(REPORT_LIST_VIEW_PAGE, modelMap);
        return mav;
    }

    @RequestMapping(value = REPORT_LIST, method = POST, params = "web")
    public ModelAndView searchListAction(@Valid @ModelAttribute("requestSearch") RequestSearch entity, BindingResult bindingResult) {

        String disapprovedListStr = "search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<Request> requestResult = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<Request>>() {
        });

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("requests", requestResult);
        modelMap.put("requestDetailsInfo", "/request-details/info/");
        modelMap.put("itemTypes", ItemType.values());
        modelMap.put("itemList", SharedData.getItems(false));
        modelMap.put("userList", SharedData.getUsers(false));
        modelMap.put("statuses", RequestStatus.values());
        modelMap.put("detailsStatuses", RequestDetailsStatus.values());
        ModelAndView mav = new ModelAndView(REPORT_LIST_VIEW_PAGE, modelMap);
        return mav;

    }

    @RequestMapping(value = REPORT_LIST, method = POST, params = "xml")
    public void searchXmlListAction(@Valid @ModelAttribute("requestSearch") RequestSearch entity, BindingResult bindingResult, HttpServletRequest request,
                                    HttpServletResponse response) {
        String disapprovedListStr = "search-list";
        String url = BASE_URL + BASE_NAME + URL_SEPARATOR + disapprovedListStr;
        List<Request> requestResult = SharedData.fetchAllByUrl(entity, url, new ParameterizedTypeReference<List<Request>>() {
        });
        XSSFWorkbook workbook = new XSSFWorkbook();

        for (Request req : requestResult) {
            XSSFSheet sheet = workbook.createSheet("Request #" + req.getRequestId());
            Object[][] dataTypes = {
                    {"Request ID", "Request Date", "Requested Personnel", "Personnel Message", "Admin Remark", "Item Type", "Request Status"},
                    {req.getRequestId(), req.getRequestDate(), String.format("%s %s", req.getUser().getLastName(), req.getUser().getFirstName()), req.getUserMessage(), req.getAdminNotice(), req.getItemType().getName(), req.getRequestStatus().getName()},
                    {"", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", ""}
            };

            int rowNumber = 0;
            rowNumber = createXml(dataTypes, sheet, rowNumber);

            List<RequestDetails> requestDetailsList = fetchRequestDetails(req.getRequestId());
            for (RequestDetails rd : requestDetailsList) {
                String consideredBy = rd.getConsideredByUser() != null ? String.format("%s %s", rd.getConsideredByUser().getLastName(), rd.getConsideredByUser().getFirstName()) : "";
                String issuedBy = rd.getIssuedByUser() != null ? String.format("%s %s", rd.getIssuedByUser().getLastName(), rd.getIssuedByUser().getFirstName()) : "";

                Object[][] dataTypes2 = {
                        {"Request ID", "Item", "Request Quantity", "Approved Quantity", "Consideration Date",
                                "Release Date", "Supply Office Remark", "Considered By", "Issued By", "Request Details Status",
                                "Disapproval Message", "Cancellation Reason"},
                        {rd.getRequest().getRequestId(), rd.getRequestQuantity(), rd.getApprovedQuantity(),
                                rd.getApprovalDisapprovalDate(), rd.getReleaseDate(), rd.getSupplyOfficeRemark(), consideredBy, issuedBy,
                                rd.getRequestDetailsStatus() != null ? rd.getRequestDetailsStatus().getName() : "", rd.getDisapprovalMessage(),
                                rd.getCancellationReason()
                        },

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
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Request... entity) {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        binder.registerCustomEditor(Date.class, "requestDateFrom", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "requestDateTo", new PropertyEditorSupport() {
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

        binder.registerCustomEditor(List.class, "requestStatuses", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<RequestStatus> statusList = new ArrayList<>();
                String[] statusStr = text.split(",");
                if (statusStr == null || statusStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : statusStr) {
                        try {
                            RequestStatus discoveredStatus = RequestStatus.valueOf(itStr);
                            if (discoveredStatus != null)
                                statusList.add(discoveredStatus);

                        } catch (IllegalArgumentException e) {
                        }
                    }

                    setValue(statusList);
                }
            }
        });


        binder.registerCustomEditor(List.class, "requestDetailsStatuses", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<RequestDetailsStatus> statusList = new ArrayList<>();
                String[] statusDetailsStr = text.split(",");

                if (statusDetailsStr == null || statusDetailsStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : statusDetailsStr) {
                        try {
                            RequestDetailsStatus discoveredStatus = RequestDetailsStatus.valueOf(itStr);
                            if (discoveredStatus != null)
                                statusList.add(discoveredStatus);
                        } catch (IllegalArgumentException e) {
                        }
                    }

                    setValue(statusList);
                }
            }
        });

        binder.registerCustomEditor(List.class, "itemTypes", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                List<ItemType> statusList = new ArrayList<>();
                String[] itemTypeStr = text.split(",");

                if (itemTypeStr == null || itemTypeStr.length == 0)
                    setValue(null);

                else {
                    for (String itStr : itemTypeStr) {
                        try {
                            ItemType itemType = ItemType.valueOf(itStr);
                            if (itemType != null)
                                statusList.add(itemType);

                        } catch (IllegalArgumentException e) {
                        }
                    }

                    setValue(statusList);
                }
            }
        });


        binder.registerCustomEditor(List.class, "requestedUsers", new PropertyEditorSupport() {
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

        binder.registerCustomEditor(List.class, "issuedByUsers", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(fetchUser(text));
            }
        });

        binder.registerCustomEditor(Date.class, "approvalDisapprovalDateFrom", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "approvalDisapprovalDateTo", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "releaseDateFrom", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, "releaseDateTo", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });

//        binder.registerCustomEditor(Item.class, "items", new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) {
//                System.out.println(text);
//            }
//        });
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

    @Override
    public ModelAndView createAction(Request entity, BindingResult bindingResult) {
        return null;
    }

}
