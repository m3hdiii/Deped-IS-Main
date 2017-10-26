package com.deped.controller;

import com.deped.model.Response;
import com.deped.model.category.Category;
import com.deped.model.config.client.ClientEnumKey;
import com.deped.model.items.Item;
import com.deped.model.location.City;
import com.deped.model.location.Country;
import com.deped.model.pack.Pack;
import com.deped.model.security.Role;
import com.deped.model.supply.Supplier;
import com.deped.repository.utils.Range;
import com.deped.utils.ImageUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

    public ResponseEntity<T> makeCreateRestRequest(T entity, String baseRestName, HttpMethod method, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(entity);
        String restUrl = String.format(CREATE_URL, baseRestName);
        ResponseEntity<T> response = restTemplate.exchange(restUrl, method, httpEntity, entityClass);
        updateSharedEntities(entityClass);
        return response;
    }

    public ResponseEntity<Response> makeUpdateRestRequest(T entity, String baseName, HttpMethod method, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(entity);
        String restUrl = String.format(UPDATE_URL, baseName);
        ResponseEntity<Response> response = restTemplate.exchange(restUrl, method, httpEntity, Response.class);
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

    public ResponseEntity<List<T>> makeFetchRestRequestByFreignKey(String baseName, String foreignKeyValue, HttpMethod method, ParameterizedTypeReference<List<T>> typeRef) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(null);
        String restUrl = String.format(FETCH_URL, baseName) + URL_SEPARATOR + foreignKeyValue;
        ResponseEntity<List<T>> response = restTemplate.exchange(restUrl, method, httpEntity, typeRef);
        return response;
    }

    public ResponseEntity<T> makeFetchByIdRequest(String baseName, HttpMethod method, Long id, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format(FETCH_BY_ID_URL, baseName, id);
        ResponseEntity<T> response = restTemplate.getForEntity(restUrl, entityClass);
        return response;
    }

    public ResponseEntity<Response> makeRemoveRestRequest(T[] entities, String baseName, HttpMethod method, Class<T> entityClass) {
        String restUrl = String.format(REMOVE_URL, baseName);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, entities, baseName, method, entityClass);
        return response;
    }

    public ResponseEntity<Response> makeCreateAllRestRequest(T[] entities, String baseName, HttpMethod method, Class<T> entityClass) {
        String restUrl = String.format(CREATE_ALL_URL, baseName);
        ResponseEntity<Response> response = makeGenericListRestRequest(restUrl, entities, baseName, method, entityClass);
        return response;
    }

    private ResponseEntity<Response> makeGenericListRestRequest(String restUrl, T[] entities, String baseName, HttpMethod method, Class<T> entityClass) {
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

    public ModelAndView createProcessing(ResponseEntity response, String createViewPage) {
        Map<String, String> responseMap = new HashMap<>();
        ModelAndView mv = new ModelAndView();

        if (response == null || response.getBody() == null) {
            responseMap.put(NOT_CREATED_KEY, FAILURE_MESSAGE);
        } else {
            responseMap.put(SUCCESSFULLY_CREATED_KEY, SUCCESS_MESSAGE);
        }

        mv.addAllObjects(responseMap);
        mv.setViewName(createViewPage);
        return mv;
    }


    public ModelAndView updateProcessing(ResponseEntity<Response> response, String createViewPage) {
        Map<String, String> responseMap = new HashMap<>();

        ModelAndView mv = new ModelAndView();
        Response responseResult;
        if (response == null || response.getBody() == null) {
            responseMap.put(NOT_UPDATED_KEY, FAILURE_MESSAGE);
        } else {
            responseResult = response.getBody();
            switch (responseResult.getResponseStatus()) {
                case SUCCESSFUL:
                    responseMap.put(SUCCESSFULLY_UPDATED_KEY, responseResult.getResponseMessage());
                    break;
                case FAILED:
                    responseMap.put(NOT_UPDATED_KEY, responseResult.getResponseMessage());
                    break;
            }

        }

        mv.addAllObjects(responseMap);
        mv.setViewName(createViewPage);
        return mv;
    }


    public ModelAndView renderProcessing(ResponseEntity<T> response, Long entityId, String baseJSPPageName, String createViewPage) {
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


    public ModelAndView listProcessing(ResponseEntity<List<T>> response, String baseJSPPageName, String createViewPage) {
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

        try {
            Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
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
        }

        if (entityClass == Pack.class) {
            SharedData.getPacks(isUpdated);
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
    }
}
