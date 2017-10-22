package com.deped.controller.item;

import com.deped.controller.AbstractMainController;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.utils.ImageUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ItemController extends AbstractMainController<Item, Long> {

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

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = GET)
    public ModelAndView renderCreatePage(@ModelAttribute("item") Item entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    public ModelAndView createAction(Item entity) {
        return null;
    }

    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createActionWithPic(@PathVariable MultipartFile itemPic, @Valid @ModelAttribute("item") Item entity) {

        entity.setCreationDate(new Date());
        byte[] fileBytes;
        try {
            if (itemPic != null && (fileBytes = itemPic.getBytes()) != null && fileBytes.length != 0) {
                boolean isImage = ImageUtils.isImage(fileBytes);
                if (isImage) {
                    String encodeBase64 = ImageUtils.encodeBase64(fileBytes);
                    entity.setPictureBase64(encodeBase64);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseEntity<Item> response = makeCreateRestRequest(entity, BASE_ENTITY_URL_NAME, HttpMethod.POST, Item.class);
        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Item> response = makeFetchByIdRequest(BASE_ENTITY_URL_NAME, HttpMethod.POST, aLong, Item.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Item> response = makeFetchByIdRequest(BASE_ENTITY_URL_NAME, HttpMethod.POST, aLong, Item.class);
        Item item = response.getBody();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.putAll(getConfigMap());
        modelMap.put(BASE_NAME, item);
        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }

    @Override
    public ModelAndView updateAction(Long aLong, Item entity) {
        return null;
    }


    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateActionWithPic(@PathVariable MultipartFile itemPic, @PathVariable(ID_STRING_LITERAL) Long aLong, @Valid @ModelAttribute(BASE_NAME) Item entity) {

        if (itemPic != null) {
            String itemBase64String = getBase64String(itemPic);
            if (itemBase64String != null) {
                entity.setPictureBase64(itemBase64String);
            }
        }

        entity.setItemId(aLong);
        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_ENTITY_URL_NAME, HttpMethod.POST);
        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Item>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Item>>() {
        });
        ModelAndView mv = listProcessing(response, "itemList", LIST_VIEW_PAGE);
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
}
