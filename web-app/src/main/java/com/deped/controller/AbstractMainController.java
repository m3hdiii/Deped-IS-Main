package com.deped.controller;

import com.deped.ResultBean;
import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.model.category.Category;
import com.deped.model.config.client.ClientEnumKey;
import com.deped.model.items.Item;
import com.deped.model.location.City;
import com.deped.model.location.Country;
import com.deped.model.location.office.Department;
import com.deped.model.location.office.Section;
import com.deped.model.order.OrderDetails;
import com.deped.model.request.RequestDetails;
import com.deped.model.security.Role;
import com.deped.model.supply.Supplier;
import com.deped.model.unit.Unit;
import com.deped.repository.utils.Range;
import com.deped.security.UserDetailsServiceImpl;
import com.deped.utils.ImageUtils;
import com.deped.utils.SystemUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMainController<T, ID> implements MainController<T, ID>, ConstantMessages {

    //--------------- REST URLS
    public static final String BASE_URL = SharedData.getRestBaseUrl();
    public static final String CREATE_URL = BASE_URL + "%s/create";
    public static final String UPDATE_URL = BASE_URL + "%s/update";
    public static final String FETCH_URL = BASE_URL + "%s/fetch-all";
    public static final String FETCH_RANGE_URL = BASE_URL + "%s/fetch-all/%d/%d";
    public static final String REMOVE_URL = BASE_URL + "%s/remove";
    public static final String CREATE_ALL_URL = BASE_URL + "%s/create-all";
    public static final String FETCH_BY_ID_URL = BASE_URL + "%s/%s";

    private String createDuplicateMessage = "This %s already exist";
    private String updateDuplicateMessage = "Update failed! This %s already exist";
    private String deleteConstraintViolationMessage = "%s with the same % exist. Please choose another";


    public ResponseEntity<T> makeCreateRestRequest(T entity, String baseRestName, HttpMethod method, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(entity);
        String restUrl = String.format(CREATE_URL, baseRestName);
        ResponseEntity<T> response = null;

        try {
            response = restTemplate.exchange(restUrl, method, httpEntity, entityClass);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatusCode());
        }

        updateSharedEntities(entityClass);
        return response;
    }

    public ModelAndView createResultPage(ResultBean resultBean) {
        Map<String, Object> modelMap = new HashMap<>(getConfigMap());
        modelMap.put("result", resultBean);
        ModelAndView mav = new ModelAndView("result", modelMap);
        return mav;

    }

    public Item fetchItemByStringId(String text) {
        try {
            Item discoveredItem = fetchItemByName(text);
            return discoveredItem;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Item fetchItemByName(String itemName) {
        Item item = new Item();
        item.setName(itemName);

        List<Item> items = SharedData.getItems(false);
        Item discoveredItem = SystemUtils.findElementInList(items, item);
        return discoveredItem;
    }

    protected Category fetchCategoryByStringId(String text) {
        try {
            Category discoveredItem = fetchCategoryByName(text);
            return discoveredItem;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Category fetchCategoryByName(String itemName) {
        Category category = new Category();
        category.setName(itemName);

        List<Category> categories = SharedData.getCategories(false);
        Category discoveredCategory = SystemUtils.findElementInList(categories, category);
        return discoveredCategory;
    }

    public Section fetchSectionByStringId(String text) {
        try {
            Section discoveredItem = fetchSectionByName(text);
            return discoveredItem;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Section fetchSectionByName(String sectionName) {
        Section section = new Section();
        section.setName(sectionName);

        List<Section> items = SharedData.getSections(false);
        Section discoveredItem = SystemUtils.findElementInList(items, section);
        return discoveredItem;
    }

    protected Unit fetchUnitByStringId(String text) {
        try {
            Unit discoveredUnit = fetchUnitByName(text);
            return discoveredUnit;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Unit fetchUnitByName(String itemName) {
        Unit unit = new Unit();
        unit.setName(itemName);

        List<Unit> units = SharedData.getUnits(false);
        Unit discoveredUnit = SystemUtils.findElementInList(units, unit);
        return discoveredUnit;
    }

    protected Supplier fetchSupplierByStringId(String text) {
        try {
            Supplier discoveredSupplier = fetchSupplierByName(text);
            return discoveredSupplier;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Supplier fetchSupplierByName(String itemName) {
        Supplier supplier = new Supplier();
        supplier.setName(itemName);

        List<Supplier> suppliers = SharedData.getSuppliers(false);
        Supplier discoveredSupplier = SystemUtils.findElementInList(suppliers, supplier);
        return discoveredSupplier;
    }



    public ResponseEntity<Response> makeUpdateRestRequest(T entity, String baseName, HttpMethod method, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(entity);
        String restUrl = String.format(UPDATE_URL, baseName);
        ResponseEntity<Response> response;
        try {
            response = restTemplate.exchange(restUrl, method, httpEntity, Response.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatusCode());
        }
        updateSharedEntities(entityClass);
        return response;
    }

    public ResponseEntity<List<T>> makeFetchAllRestRequest(String baseName, HttpMethod method, ParameterizedTypeReference<List<T>> typeRef) {
        return makeFetchByRangeRestRequest(baseName, method, null, typeRef);
    }

    public ResponseEntity<List<?>> makeFetchGenericAllRestRequest(String baseName, HttpMethod method, ParameterizedTypeReference<List<?>> typeRef) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, baseName);
        ResponseEntity<List<?>> response = restTemplate.exchange(restUrl, method, httpEntity, typeRef);
        return response;
    }


    public ResponseEntity<List<T>> makeFetchByRangeRestRequest(String baseName, HttpMethod method, Range range, ParameterizedTypeReference<List<T>> typeRef) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl;
        if (range == null) {
            restUrl = String.format(FETCH_URL, baseName);
        } else {
            restUrl = String.format(FETCH_RANGE_URL, baseName, range.getFrom(), range.getTo());
        }

        ResponseEntity<List<T>> response = restTemplate.exchange(restUrl, method, httpEntity, typeRef);
        return response;
    }

    public ResponseEntity<List<T>> makeFetchRestRequestByForeignKey(String baseName, String foreignKeyValue, HttpMethod method, ParameterizedTypeReference<List<T>> typeRef) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, baseName) + URL_SEPARATOR + foreignKeyValue;
        ResponseEntity<List<T>> response = restTemplate.exchange(restUrl, method, httpEntity, typeRef);
        return response;
    }

    public ResponseEntity<T> makeFetchByIdRequest(String baseName, HttpMethod method, String id, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format(FETCH_BY_ID_URL, baseName, id);
        ResponseEntity<T> response = restTemplate.getForEntity(restUrl, entityClass);
        return response;
    }

    public ResponseEntity<Response> makeRemoveRestRequest(T[] entities, String baseName, HttpMethod method, Class<T> entityClass) {
        String restUrl = String.format(REMOVE_URL, baseName);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, entities, method, entityClass);
        return response;
    }

    public ResponseEntity<Response> makeCreateAllRestRequest(T[] entities, String baseName, HttpMethod method, Class<T> entityClass) {
        String restUrl = String.format(CREATE_ALL_URL, baseName);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, entities, method, entityClass);
        return response;
    }

    public ResponseEntity<Response> makeGenericListRestRequest(String restUrl, T[] entities, HttpMethod method, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T[]> httpEntity = new HttpEntity<>(entities, headers);
        ResponseEntity<Response> response = restTemplate.exchange(restUrl, method, httpEntity, Response.class);
        updateSharedEntities(entityClass);
        return response;
    }

    protected HttpEntity makeHttpEntity(T entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> httpEntity = new HttpEntity<>(entity, headers);
        return httpEntity;
    }


    protected ModelAndView makeHintPage(String jspLocation, String className, String methodName) {
        Map<String, String> map = new HashMap<>();
        map.put("jspLocation", jspLocation);
        map.put("controllerClazz", className);
        map.put("methodName", methodName);
        return new ModelAndView(jspLocation, map);
    }

    public ModelAndView postCreateProcessing(Class<T> entityClass, ResponseEntity response, String createViewPage, String modelName, T oldObject, T freshObject, BindingResult result, String... messagePlaceHolder) {
        Map<String, Object> responseMap = new HashMap<>(getConfigMap());
        ModelAndView mv = new ModelAndView();

        if (response == null || response.getStatusCode() == null) {
            result.addError(new FieldError(entityClass.getSimpleName(), "error", FAILURE_MESSAGE));

        } else {
            HttpStatus status = response.getStatusCode();
            processing(status, result, entityClass, responseMap, modelName, oldObject, freshObject, createDuplicateMessage, messagePlaceHolder);
        }

        mv.addAllObjects(responseMap);
        mv.setViewName(createViewPage);
        return mv;
    }

    public ModelAndView postUpdateProcessing(Class<T> entityClass, ResponseEntity<Response> response, String createViewPage,
                                             String modelName, T oldObject, T freshObject,
                                             BindingResult result, String... messagePlaceHolder) {
        Map<String, Object> responseMap = new HashMap<>(getConfigMap());
        ModelAndView mv = new ModelAndView();

        Response responseResult;
        if (response == null || response.getStatusCode() == null) {
            result.addError(new FieldError(entityClass.getSimpleName(), "error", FAILURE_MESSAGE));

        } else {
            HttpStatus status = response.getStatusCode();
            updateProcessing(status, result, entityClass, responseMap, modelName, oldObject, freshObject, updateDuplicateMessage, messagePlaceHolder);
        }

        mv.addAllObjects(responseMap);
        mv.setViewName(createViewPage);
        return mv;
    }

    private void processing(HttpStatus status, BindingResult result, Class<T> entityClass, Map<String, Object> responseMap, String modelName, T oldObject, T freshObject, String messageFormat, String... messagePlaceHolder) {
        switch (status) {
            case CONFLICT:
                result.addError(new FieldError(entityClass.getSimpleName(), "error", String.format(messageFormat, messagePlaceHolder)));
                responseMap.put(modelName, oldObject);
                break;

            case OK:
                responseMap.put(SUCCESSFULLY_CREATED_KEY, SUCCESS_MESSAGE);
                responseMap.put(modelName, freshObject);
                break;

            default:
                result.addError(new FieldError(entityClass.getSimpleName(), "error", FAILURE_MESSAGE));
                responseMap.put(modelName, oldObject);
                break;
        }
    }

    private void updateProcessing(HttpStatus status, BindingResult result, Class<T> entityClass, Map<String, Object> responseMap, String modelName, T oldObject, T freshObject, String messageFormat, String... messagePlaceHolder) {
        switch (status) {
            case CONFLICT:
                result.addError(new FieldError(entityClass.getSimpleName(), "error", String.format(messageFormat, messagePlaceHolder)));
                responseMap.put(modelName, oldObject);
                break;

            case OK:
                responseMap.put(SUCCESSFULLY_UPDATED_KEY, SUCCESS_MESSAGE);
                responseMap.put(modelName, freshObject);
                break;

            default:
                result.addError(new FieldError(entityClass.getSimpleName(), "error", FAILURE_MESSAGE));
                responseMap.put(modelName, oldObject);
                break;
        }
    }


    public ModelAndView renderProcessing(ResponseEntity<T> response, ID entityId, String baseJSPPageName, String createViewPage) {
        Map<String, Object> modelMap = new HashMap<>();
        ModelAndView mv = new ModelAndView();

        if (response == null || response.getBody() == null) {
            modelMap.put(NOT_UPDATED_KEY, FAILURE_MESSAGE);
        } else {
            T entity = response.getBody();
            modelMap.putAll(getConfigMap());
            modelMap.put(baseJSPPageName + "Info", entity);
            modelMap.put(baseJSPPageName + "Id", entityId);

        }
        mv.addAllObjects(modelMap);
        mv.setViewName(createViewPage);
        return mv;
    }

    protected Map<String, Object> getConfigMap() {
        Map<ClientEnumKey, String> mapConfig = SharedData.getClientConfigsMap(false);
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("baseUrl", mapConfig.get(ClientEnumKey.RESOURCE_BASE_URL));
        return configMap;
    }


    public ModelAndView postListProcessing(ResponseEntity<List<T>> response, String baseJSPPageName, String createViewPage) {
        List<T> list = response.getBody();
        HashMap<String, Object> map = new HashMap<>();
        map.put(baseJSPPageName, list);
        Map<ClientEnumKey, String> mapConfig = SharedData.getClientConfigsMap(false);
        map.put("baseUrl", mapConfig.get(ClientEnumKey.RESOURCE_BASE_URL));
        return new ModelAndView(createViewPage, map);
    }

    protected String getBase64String(MultipartFile multipartFile) {
        byte[] fileBytes;

        try {
            fileBytes = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (fileBytes == null || fileBytes.length == 0) {
            return null;
        }

        boolean isImage = ImageUtils.isImage(fileBytes);
        if (isImage) {
            String encodeBase64 = ImageUtils.encodeBase64(fileBytes);
            return encodeBase64;
        }

        return null;
    }

    protected Date getDate(String dateText) {
        if (dateText == null || dateText.isEmpty()) {
            return null;
        }

        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
            return birthDate;
        } catch (ParseException e) {
        }

        try {
            birthDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateText);
            return birthDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void updateSharedEntities(Class<T> entityClass) {
        boolean isUpdated = true;
        if (entityClass == Item.class) {
            SharedData.getItems(isUpdated);
            SharedData.getGoods(isUpdated);
            SharedData.getSemiExpendables(isUpdated);
            SharedData.getEquipment(isUpdated);
        }

        if (entityClass == Unit.class) {
            SharedData.getUnits(isUpdated);
        }

        if (entityClass == User.class) {
            SharedData.getUsers(isUpdated);
        }

        if (entityClass == Category.class) {
            SharedData.getCategories(isUpdated);
        }

        if (entityClass == Supplier.class) {
            SharedData.getSuppliers(isUpdated);
        }

        if (entityClass == Country.class) {
            SharedData.getCountries(isUpdated);
        }

        if (entityClass == City.class) {
            SharedData.getCities(isUpdated);
        }

        if (entityClass == Role.class) {
            SharedData.getRoles(isUpdated);
        }

        if (entityClass == Department.class) {
            SharedData.getDepartments(isUpdated);
        }

        if (entityClass == Section.class) {
            SharedData.getSections(isUpdated);
        }
    }

    public User getUserFromSpringSecurityContext() {
        User user = null;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            if (principal != null)
                user = ((UserDetailsServiceImpl.CustomSpringSecurityUser) principal).getUser();
        }

        return user;
    }

    public List<RequestDetails> fetchRequestDetails(Long requestId) {
        RestTemplate restTemplate = new RestTemplate();
        //get RequestDetails if there is any
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "request-details").concat("/").concat(requestId + "");
        ResponseEntity<List<RequestDetails>> responseDetails = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<RequestDetails>>() {
        });
        List<RequestDetails> requestDetailsList = responseDetails.getBody();
        return requestDetailsList;
    }

    public List<OrderDetails> fetchOrderDetails(Long orderId) {
        RestTemplate restTemplate = new RestTemplate();
        //get RequestDetails if there is any
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, "order-details").concat("/").concat(orderId + "");
        ResponseEntity<List<OrderDetails>> responseDetails = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<OrderDetails>>() {
        });
        List<OrderDetails> requestDetailsList = responseDetails.getBody();
        return requestDetailsList;
    }
}
