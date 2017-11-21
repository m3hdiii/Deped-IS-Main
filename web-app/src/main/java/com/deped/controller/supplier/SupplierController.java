package com.deped.controller.supplier;

import com.deped.controller.AbstractMainController;
import com.deped.model.Response;
import com.deped.model.supply.Supplier;
import com.deped.utils.ImageUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class SupplierController extends AbstractMainController<Supplier, Long> {

    private static final String BASE_NAME = "supplier";
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
    public ModelAndView renderCreatePage(@ModelAttribute(BASE_NAME) Supplier entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;
    }

    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createActionWithPic(@RequestParam MultipartFile supplyPic, @Valid @ModelAttribute(BASE_NAME) Supplier entity, BindingResult bindingResult) {

        String base64Image = checkInfo(supplyPic, bindingResult);
        entity.setPictureBase64(base64Image);

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put(BASE_NAME, entity);
            ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
            return mv;
        }

        entity.setCreationDate(new Date());
        ResponseEntity<Supplier> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Supplier.class);
        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE, BASE_NAME, entity, new Supplier());
        return mv;
    }


    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Supplier> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Supplier.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Supplier> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Supplier.class);
        Supplier supplier = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, supplier);
    }

    private String checkInfo(MultipartFile supplyPic, BindingResult bindingResult) {
        String encodeBase64 = null;
        try {
            byte[] fileBytes = supplyPic.getBytes();
            if (supplyPic != null && fileBytes != null && fileBytes.length != 0) {
                boolean isImage = ImageUtils.isImage(fileBytes);
                if (isImage) {
                    encodeBase64 = ImageUtils.encodeBase64(fileBytes);
                    return encodeBase64;
                } else {
                    bindingResult.addError(new FieldError(Supplier.class.getSimpleName(), "pictureBase64", "Your file is suspicious and it's not an image. Your actions will be logged"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodeBase64;

    }

    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateActionWithPicture(@RequestParam MultipartFile supplyPic, @PathVariable(ID_STRING_LITERAL) Long aLong, @Valid @ModelAttribute Supplier entity, BindingResult bindingResult) {
        entity.setSupplierId(aLong);
        String base64Image = checkInfo(supplyPic, bindingResult);
        entity.setPictureBase64(base64Image);

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, entity);
            return mv;
        }

        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, Supplier.class);
        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Supplier>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Supplier>>() {
        });
        ModelAndView mv = listProcessing(response, "suppliers", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Supplier... entity) {
        return null;
    }

    @Override
    public ModelAndView createAction(Supplier entity, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ModelAndView updateAction(Long aLong, Supplier entity, BindingResult bindingResult) {
        return null;
    }

}

