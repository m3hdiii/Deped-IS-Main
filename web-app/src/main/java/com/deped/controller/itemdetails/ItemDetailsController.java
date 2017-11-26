package com.deped.controller.itemdetails;

import com.deped.controller.AbstractMainController;
import com.deped.form.ItemDetailsForm;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.*;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ItemDetailsController extends AbstractMainController<ItemDetails, Long> {

    private static final String BASE_NAME = "item-info";
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


    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute ItemDetails entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @RequestMapping(value = "/item-details/create/{requestId}/{itemId}/{quantity}", method = GET)
    public ModelAndView createMultipleItemDetails(@PathVariable("requestId") Long requestId, @PathVariable("itemId") Long itemId, @PathVariable("quantity") Integer quantity, HttpSession session) {
        ModelAndView mav = createModelAndView(itemId, quantity, null);
        return mav;

    }

    private ModelAndView createModelAndView(Long itemId, Integer quantity, ItemDetailsForm itemDetailsForm) {
        if (itemDetailsForm == null) {
            Item discoveredItem = fetchItemById(itemId);
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

    @RequestMapping(value = "/item-details/create/{requestId}/{itemId}/{quantity}", method = POST)
    public ModelAndView createMultipleItemDetails(@PathVariable("requestId") Long requestId, @PathVariable("itemId") Long itemId, @PathVariable("quantity") Integer quantity, @ModelAttribute("itemDetailsForm") ItemDetailsForm itemDetailsForm, BindingResult bindingResult, HttpSession session) {

        checkItemDetailsList(itemDetailsForm.getItemDetailsList(), bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView mav = createModelAndView(itemId, quantity, itemDetailsForm);
            return mav;
        }

        session.setAttribute(String.format("ItemDetails%d%d%d", requestId, itemId, quantity), itemDetailsForm.getItemDetailsList());

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
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<ItemDetails> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, ItemDetails.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<ItemDetails> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, ItemDetails.class);
        ItemDetails itemDetails = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, itemDetails);
    }

    @Override
    public ModelAndView updateAction(Long aLong, ItemDetails entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
        }
        entity.setItemDetailsId(aLong);
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

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        binder.registerCustomEditor(Item.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Item discoveredItem = fetchItemByStringId(text);
                setValue(discoveredItem);
            }
        });
    }
}
