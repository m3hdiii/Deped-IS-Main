package com.deped.controller;

import com.deped.model.Response;
import com.deped.repository.utils.Range;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMainController<T, ID> implements MainController<T, ID>, ConstantMessages {


    public ResponseEntity<T> makeCreateRestRequest(T entity, String baseRestName, HttpMethod method, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(entity);
        String restUrl = String.format(CREATE_URL, baseRestName);
        ResponseEntity<T> response = restTemplate.exchange(restUrl, method, httpEntity, entityClass);
        return response;
    }

    public ResponseEntity<Response> makeUpdateRestRequest(T entity, String baseName, HttpMethod method) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = makeHttpEntity(entity);
        String restUrl = String.format(UPDATE_URL, baseName);
        ResponseEntity<Response> response = restTemplate.exchange(restUrl, method, httpEntity, Response.class);
        return response;
    }

    public ResponseEntity<List<T>> makeFetchAllRestRequest(String baseName, HttpMethod method, ParameterizedTypeReference<List<T>> typeRef) {
        return makeFetchByRangeRestRequest(baseName, method, null, typeRef);
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

    public ResponseEntity<T> makeFetchByIdRequest(String baseName, HttpMethod method, Long id, Class<T> entityClass) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format(FETCH_BY_ID_URL, baseName, id);
        ResponseEntity<T> response = restTemplate.getForEntity(restUrl, entityClass);
        return response;
    }

    public ResponseEntity<Boolean> makeRemoveRestRequest(T[] entities, String baseName, HttpMethod method) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T[]> httpEntity = new HttpEntity<>(entities, headers);
        String restUrl = String.format(REMOVE_URL, baseName);
        ResponseEntity<Boolean> response = restTemplate.exchange(restUrl, method, httpEntity, Boolean.class);
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

            modelMap.put(baseJSPPageName + "Info", entity);
            modelMap.put(baseJSPPageName + "Id", entityId);

        }
        mv.addAllObjects(modelMap);
        mv.setViewName(createViewPage);
        return mv;
    }


    public ModelAndView listProcessing(ResponseEntity<List<T>> response, String baseJSPPageName, String createViewPage) {
        List<T> list = response.getBody();
        HashMap<String, Object> map = new HashMap<>();
        map.put(baseJSPPageName, list);
        return new ModelAndView(createViewPage, map);
    }
}
