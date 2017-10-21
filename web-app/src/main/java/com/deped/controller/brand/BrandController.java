package com.deped.controller.brand;

import com.deped.controller.AbstractMainController;
import com.deped.model.Response;
import com.deped.model.brand.Brand;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class BrandController extends AbstractMainController<Brand, Long> {

    private static final String BASE_NAME = "brand";

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
    public ModelAndView renderCreatePage(@ModelAttribute("brand") Brand entity) {
        ModelAndView mv = new ModelAndView(CREATE_VIEW_PAGE);
        return mv;

    }

    @Override
    public ModelAndView createAction(Brand entity) {
        return null;
    }

    @RequestMapping(value = {CREATE_MAPPING}, method = POST)
    public ModelAndView createActionWithPic(@RequestParam MultipartFile brandPic, @Valid @ModelAttribute("brand") Brand entity) {
        entity.setCreationDate(new Date());
        String base64Encoded = null;
        try {
            byte[] picture = brandPic.getBytes();
            base64Encoded = Base64.getEncoder().encodeToString(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        entity.setLogoPic(base64Encoded);
//        Base64.getDecoder().decode(str)
        ResponseEntity<Brand> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, Brand.class);
        ModelAndView mv = createProcessing(response, CREATE_VIEW_PAGE);
        return mv;
    }


    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Brand> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Brand.class);
        ModelAndView mv = renderProcessing(response, aLong, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Brand> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, aLong, Brand.class);
        Brand item = response.getBody();
        return new ModelAndView(UPDATE_VIEW_PAGE, BASE_NAME, item);
    }

    @Override
    public ModelAndView updateAction(Long aLong, Brand entity) {
        return null;
    }

    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateActionWithPicture(@RequestParam MultipartFile brandPic, @PathVariable(ID_STRING_LITERAL) Long aLong, @Valid @ModelAttribute Brand entity) {
        entity.setBrandId(aLong);
        String base64Encoded = null;
        try {
            byte[] picture = brandPic.getBytes();
            base64Encoded = Base64.getEncoder().encodeToString(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        entity.setLogoPic(base64Encoded);

        //This is actually the update date
        entity.setCreationDate(new Date());
        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST);
        ModelAndView mv = updateProcessing(response, UPDATE_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<Brand>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<Brand>>() {
        });
        ModelAndView mv = listProcessing(response, "brands", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid Brand... entity) {
        return null;
    }
}
