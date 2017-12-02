package com.deped.controller.user;

import com.deped.controller.AbstractMainController;
import com.deped.controller.SharedData;
import com.deped.log.injector.FancyLogger;
import com.deped.model.Response;
import com.deped.model.account.Gender;
import com.deped.model.account.Position;
import com.deped.model.account.User;
import com.deped.model.location.City;
import com.deped.model.location.office.Section;
import com.deped.tools.ControllerImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mehdi on 7/7/17.
 */

@Controller
public class UserController extends AbstractMainController<User, String> {

    @FancyLogger
    private Logger log;

    private static final String BASE_NAME = "user";
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


    @RequestMapping(value = {CREATE_MAPPING}, method = GET)
    public ModelAndView renderCreatePage() {
        Map<String, Object> modelMap = makeCreateModel(new User());
        return new ModelAndView(CREATE_VIEW_PAGE, modelMap);
    }

    public Map<String, Object> makeCreateModel(User user) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("genders", Gender.values());
        modelMap.put("positions", Position.values());
        modelMap.put("countries", SharedData.getCountries(false));
        modelMap.put("departments", SharedData.getDepartments(false));
        modelMap.put("user", user);
        return modelMap;
    }


    @RequestMapping(value = CREATE_MAPPING, method = POST)
    public ModelAndView createActionMain(@RequestParam MultipartFile userPic, @Valid @ModelAttribute(BASE_NAME) User entity, BindingResult bindingResult) {

        String encodeBase64 = ControllerImageUtils.imageUploadProcessing(userPic, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = makeCreateModel(entity);
            return new ModelAndView(CREATE_VIEW_PAGE, modelMap);
        }

        entity.setPictureBase64(encodeBase64);
        entity.setCreationDate(new Date());
        ResponseEntity<User> response = makeCreateRestRequest(entity, BASE_NAME, HttpMethod.POST, User.class);

        ModelAndView mv = postCreateProcessing(User.class, response, CREATE_VIEW_PAGE, BASE_NAME, entity, new User(), bindingResult, BASE_NAME);
        mv.addAllObjects(makeCreateModel(new User()));
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_BY_ID_MAPPING, method = GET)
    public ModelAndView renderInfo(@PathVariable(ID_STRING_LITERAL) String username) {
        ResponseEntity<User> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, username, User.class);
        ModelAndView mv = renderProcessing(response, username, BASE_NAME, INFO_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = GET)
    public ModelAndView renderUpdatePage(@PathVariable(ID_STRING_LITERAL) String username) {
        ResponseEntity<User> response = makeFetchByIdRequest(BASE_NAME, HttpMethod.POST, String.valueOf(username), User.class);
        User user = response.getBody();
        Map<String, Object> modelMap = makeCreateModel(user);

        return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
    }


    @RequestMapping(value = RENDER_UPDATE_MAPPING, method = POST)
    public ModelAndView updateAction(@RequestParam MultipartFile userPic, @PathVariable(ID_STRING_LITERAL) String username, @Valid @ModelAttribute(BASE_NAME) User entity, BindingResult bindingResult) {

        String encodeBase64 = ControllerImageUtils.imageUploadProcessing(userPic, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, Object> modelMap = makeCreateModel(entity);
            return new ModelAndView(UPDATE_VIEW_PAGE, modelMap);
        }

        entity.setPictureBase64(encodeBase64);
        entity.setUsername(username);
        entity.setCreationDate(new Date());

        ResponseEntity<Response> response = makeUpdateRestRequest(entity, BASE_NAME, HttpMethod.POST, User.class);

        ModelAndView mv = postUpdateProcessing(User.class, response, UPDATE_VIEW_PAGE, BASE_NAME, entity, new User(), bindingResult, BASE_NAME);
        mv.addAllObjects(makeCreateModel(entity));
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_MAPPING, method = GET)
    public ModelAndView renderListPage() {
        ResponseEntity<List<User>> response = makeFetchAllRestRequest(BASE_NAME, HttpMethod.POST, new ParameterizedTypeReference<List<User>>() {
        });
        ModelAndView mv = postListProcessing(response, "user", LIST_VIEW_PAGE);
        return mv;
    }

    @Override
    @RequestMapping(value = RENDER_LIST_BY_RANGE_MAPPING, method = GET)
    public ModelAndView renderListPage(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        return new ModelAndView(LIST_VIEW_PAGE);
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = POST)
    public ModelAndView removeAction(@Valid User... entity) {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        binder.registerCustomEditor(Section.class, "section", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    Section section = new Section();
                    section.setName(text);
                    setValue(section);
                }
            }
        });

        binder.registerCustomEditor(City.class, "cityOfBorn", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    City city = new City();
                    city.setCityId(Long.parseLong(text));
                    setValue(city);
                }
            }
        });

        binder.registerCustomEditor(Gender.class, "gender", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    if (text.equals("FEMALE")) {
                        setValue(Gender.FEMALE);
                    } else if (text.equals("MALE")) {
                        setValue(Gender.MALE);
                    } else
                        setValue(null);

                    return;
                }
            }
        });

        binder.registerCustomEditor(Date.class, "birthDate", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(getDate(text));
            }
        });
    }

    @Override
    public ModelAndView renderCreatePage(User entity) {
        return null;
    }

    @Override
    @RequestMapping(value = "dummy", method = POST)
    public ModelAndView createAction(User entity, BindingResult bindingResult) {
        return null;
    }


    @Override
    public ModelAndView updateAction(@PathVariable(ID_STRING_LITERAL) String username, @Valid @ModelAttribute(BASE_NAME) User entity, BindingResult bindingResult) {
        return null;
    }

    public static void main(String[] args) {
        String file1 = "/home/mehdi/Pictures/RED.png";
        String file2 = "/home/mehdi/Pictures/mehdi2.jpg";
        String file3 = "/home/mehdi/Pictures/1171994_o.jpeg";
        String file4 = "/home/mehdi/Pictures/Old-HDD/maps.gif";
        String file5 = "/home/mehdi/Pictures/Old-HDD/pause.png";
        String file6 = "/home/mehdi/Desktop/fff.bmp";
        String[] files = new String[]{file1, file2, file3, file4, file5, file6};

        try {
            for (String path : files) {
                byte[] picture = FileUtils.readFileToByteArray(new File(path));
                String extension = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(picture));

                System.out.println(extension.startsWith("image"));
            }
        } catch (IOException e) {

        }
    }
}
