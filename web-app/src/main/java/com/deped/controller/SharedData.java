package com.deped.controller;

import com.deped.model.config.client.ClientConfig;
import com.deped.model.config.client.ClientEnumKey;
import com.deped.model.location.City;
import com.deped.model.location.Country;
import com.deped.model.location.office.Department;
import com.deped.model.security.Role;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SharedData {

    private static List<Country> countries;
    private static List<City> philippineCities;
    private static List<Role> roles;
    private static List<Department> departments;
    private static Map<ClientEnumKey, String> clientConfigsMap;

    public synchronized static List<Country> getCountries(boolean dataIsUpdated) {
        if (countries == null || dataIsUpdated) {
            countries = fetchAll("country", new ParameterizedTypeReference<List<Country>>() {
            });
        }
        return countries;
    }

    public synchronized static Map<ClientEnumKey, String> getClientConfigsMap(boolean dataIsUpdated) {
        if (clientConfigsMap == null || dataIsUpdated) {
            List<ClientConfig> clientConfigList = fetchAll("config", new ParameterizedTypeReference<List<ClientConfig>>() {
            });

            clientConfigsMap = new EnumMap<>(ClientEnumKey.class);
            for (ClientConfig cc : clientConfigList) {
                clientConfigsMap.put(cc.getKey(), cc.getValue());
            }
        }

        return clientConfigsMap;
    }

    public synchronized static List<City> getCities(boolean dataIsUpdated) {
        if (philippineCities == null || dataIsUpdated) {
            if (countries == null || dataIsUpdated) {
                philippineCities = fetchAll("city", new ParameterizedTypeReference<List<City>>() {
                });
            }
        }

        return philippineCities;
    }


    public synchronized static List<Role> getRoles(boolean dataIsUpdated) {
        if (roles == null || dataIsUpdated) {
            roles = fetchAll("roles", new ParameterizedTypeReference<List<Role>>() {
            });
        }
        return roles;
    }


    public synchronized static List<Department> getDepartments(boolean dataIsUpdated) {

        if (departments == null || dataIsUpdated) {
            departments = fetchAll("department", new ParameterizedTypeReference<List<Department>>() {
            });
        }

        return departments;
    }

    public static void main(String[] args) {
        getDepartments(false);
    }

    private static <T> T fetchAll(String baseUrl, ParameterizedTypeReference<T> param) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = String.format(ConstantController.FETCH_URL, baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(null, headers);
        ResponseEntity<T> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, param);

        T list = response.getBody();
        return list;
    }
}
