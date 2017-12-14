package com.deped.controller.item;

import com.deped.controller.AbstractMainController;
import com.deped.exception.ResponseErrorMessages;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.model.items.features.FunctionType;
import com.deped.tools.ControllerImageUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ItemController extends AbstractMainController<Item, String> {

    private static final String BASE_NAME = "item";
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

    private static final String BASE_ENTITY_URL_NAME = "item";

    private static final ResponseErrorMessages responseErrorMessages = ResponseErrorMessages.getInstance(Item.class, new String[]{""});

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Item entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createActionWithPic(@PathVariable MultipartFile itemPic, @Valid @ModelAttribute(BASE_NAME) Item entity, BindingResult bindingResult) {
        String encodeBase64 = ControllerImageUtils.imageUploadProcessing(itemPic, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE, BASE_NAME, entity);
            return mv;
        }

        entity.setPictureBase64(encodeBase64);
        entity.setCreationDate(new Date());
        entity.setQuantity(0);
        ResponseEntity<Item> response = makeCreateRestRequest(entity, BASE_ENTITY_URL_NAME, HttpMethod.POST, Item.class);

        ModelAndView mv = postCreateProcessing(Item.class, response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Item(), bindingResult, "item");
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) String name) {
        ResponseEntity<Item> response = makeFetchByIdRequest(BASE_ENTITY_URL_NAME, HttpMethod.POST, String.valueOf(name), Item.class);
        ModelAndView mv = renderProcessing(response, name, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String name) {
        ResponseEntity<Item> response = makeFetchByIdRequest(BASE_ENTITY_URL_NAME, HttpMethod.POST, name, Item.class);
        Item item = response.getBody();

        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put(BASE_NAME, item);
        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }

    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateActionWithPic(@PathVariable MultipartFile itemPic, @PathVariable(ID_STRING_LITERAL) String name, @Valid @ModelAttribute(BASE_NAME) Item entity, BindingResult bindingResult) {
        String encodeBase64 = ControllerImageUtils.imageUploadProcessing(itemPic, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<>(getConfigMap());
            modelMap.put(BASE_NAME, entity);
            ModelAndView mav = new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
            return mav;
        }
        entity.setPictureBase64(encodeBase64);
        entity.setCreationDate(new Date());

        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_ENTITY_URL_NAME, HttpMethod.POST, Item.class);
        ModelAndView mv = postUpdateProcessing(Item.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new Item(), bindingResult, BASE_NAME);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Item>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Item>>() {
        });
        ModelAndView mv = postListProcessing(response, "itemList", LIST_VIEW_PAGE);
        return mv;
    }


    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(Item... entity) {
        return null;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        binder.registerCustomEditor(ItemType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null) {
                    setValue(null);
                    return;
                }

                try {
                    ItemType itemType = ItemType.valueOf(text);
                    setValue(itemType);
                    return;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                setValue(null);
            }
        });

        binder.registerCustomEditor(FunctionType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null) {
                    setValue(null);
                    return;
                }

                try {
                    FunctionType itemType = FunctionType.valueOf(text);
                    setValue(itemType);
                    return;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                setValue(null);
            }
        });

    }

    @Override
    public ModelAndView createAction(Item entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ModelAndView updateAction(String name, Item entity, BindingResult bindingResult) {
        return null;
    }
}
