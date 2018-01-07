package com.deped.controller.itemdetails;

import com.deped.controller.AbstractMainController;
import com.deped.form.ItemDetailsBeanForm;
import com.deped.form.ItemDetailsForm;
import com.deped.form.OrderDetailsForm;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.items.features.Colour;
import com.deped.model.items.features.Condition;
import com.deped.model.items.features.EquipmentAvailability;
import com.deped.model.items.features.Material;
import com.deped.model.order.CaptureInfo;
import com.deped.model.order.OrderDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    private static final String RENDER_LIST_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String RENDER_LIST_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String RENDER_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String BASE_SHOW_PAGE = JSP_PAGES + URL_SEPARATOR + BASE_NAME + URL_SEPARATOR;
    private static final String CREATE_VIEW_PAGE = BASE_SHOW_PAGE + CREATE_PAGE + BASE_NAME;
    private static final String INFO_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + INFO_PAGE;
    private static final String UPDATE_VIEW_PAGE = BASE_SHOW_PAGE + UPDATE_PAGE + BASE_NAME;
    private static final String LIST_VIEW_PAGE = BASE_SHOW_PAGE + BASE_NAME + LIST_PAGE;
    private static final String INSERT_DATA = BASE_NAME + URL_SEPARATOR + "insert-data";


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
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String officeSerialNumber) {
        ResponseEntity<ItemDetails> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(officeSerialNumber), ItemDetails.class);
        ItemDetails itemDetails = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, itemDetails);
    }

    @Override
    public ModelAndView updateAction(String officeSerialNumber, ItemDetails entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
        }
        entity.setOfficeSerialNo(officeSerialNumber);
        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, ItemDetails.class);
        ModelAndView mv = postUpdateProcessing(ItemDetails.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new ItemDetails(), bindingResult, "item details");
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ModelAndView mv = makeHintPage(LIST_VIEW_PAGE, this.getClass().getCanonicalName(), Thread.currentThread().getStackTrace()[1].getMethodName());
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

        List<ItemDetails> subLIst = fetchFilledObjects(itemDetailsForm);
        String restUrl;

        restUrl = String.format(CREATE_ALL_URL, BASE_NAME);


        ItemDetails[] requestDetails = subLIst.toArray(new ItemDetails[subLIst.size()]);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, requestDetails, HttpMethod.POST, ItemDetails.class);
        Response body = response.getBody();
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    private List<ItemDetails> fetchFilledObjects(ItemDetailsBeanForm itemDetailsForm) {
        List<ItemDetails> subList = new ArrayList<>();
        List<CaptureInfo> capInfoList = itemDetailsForm.getCaptureInfoList();
        for (CaptureInfo ci : capInfoList) {
            List<ItemDetails> itemDetailsList = ci.getItemDetailsList();
            if (itemDetailsList != null) {
                for (ItemDetails id : itemDetailsList) {
                    if (id.getOfficeSerialNo() != null && !id.getOfficeSerialNo().equals("")) {
                        subList.add(id);
                    }
                }
            }
        }

        return subList;
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
    }
}