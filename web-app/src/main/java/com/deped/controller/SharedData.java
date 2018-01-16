package com.deped.controller;

import com.deped.model.account.User;
import com.deped.model.brand.Brand;
import com.deped.model.category.Category;
import com.deped.model.config.client.ClientConfig;
import com.deped.model.config.client.ClientEnumKey;
import com.deped.model.items.Item;
import com.deped.model.location.City;
import com.deped.model.location.Country;
import com.deped.model.location.office.Department;
import com.deped.model.location.office.Section;
import com.deped.model.security.Role;
import com.deped.model.supply.Supplier;
import com.deped.model.unit.Unit;
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
    private static List<Section> sections;
    private static Map<ClientEnumKey, String> clientConfigsMap;
    private static List<Item> items;
    private static List<Item> goods;
    private static List<Item> semiExpendables;
    private static List<Item> equipment;
    private static List<Unit> packs;
    private static List<Category> categories;
    private static List<Supplier> suppliers;
    private static String restBaseUrl;
    private static List<Brand> brands;
    private static List<User> users;


    public static synchronized void setRestBaseUrl(String restBaseUrl) {
        SharedData.restBaseUrl = restBaseUrl;
    }

    public static synchronized String getRestBaseUrl() {
        return SharedData.restBaseUrl;
    }

    public static synchronized void refreshAll() {
        getItems(true);
        getUnits(true);
        getCategories(true);
        getSuppliers(true);
        getCountries(true);
        getClientConfigsMap(true);
        getCities(true);
        getRoles(true);
        getDepartments(true);
        //getBrands(true);
    }

    public static synchronized List<Item> getItems(boolean dataIsUpdated) {
        if (items == null || dataIsUpdated) {
            items = fetchAll("item", new ParameterizedTypeReference<List<Item>>() {
            });
        }
        return items;
    }


    public static synchronized List<User> getUsers(boolean dataIsUpdated) {
        if (users == null || dataIsUpdated) {
            users = fetchAll("user", new ParameterizedTypeReference<List<User>>() {
            });
        }
        return users;
    }

    public static synchronized List<Item> getGoods(boolean dataIsUpdated) {
        if (goods == null || dataIsUpdated) {
            goods = fetchAll("good", new ParameterizedTypeReference<List<Item>>() {
            });
        }
        return goods;
    }

    public static synchronized List<Item> getSemiExpendables(boolean dataIsUpdated) {
        if (semiExpendables == null || dataIsUpdated) {
            semiExpendables = fetchAll("semi-expendable", new ParameterizedTypeReference<List<Item>>() {
            });
        }
        return semiExpendables;
    }

    public static synchronized List<Item> getEquipment(boolean dataIsUpdated) {
        if (equipment == null || dataIsUpdated) {
            equipment = fetchAll("equipment", new ParameterizedTypeReference<List<Item>>() {
            });
        }
        return equipment;
    }


    public static synchronized List<Unit> getUnits(boolean dataIsUpdated) {
        if (packs == null || dataIsUpdated) {
            packs = fetchAll("unit", new ParameterizedTypeReference<List<Unit>>() {
            });
        }
        return packs;
    }

    public static synchronized List<Category> getCategories(boolean dataIsUpdated) {
        if (categories == null || dataIsUpdated) {
            categories = fetchAll("category", new ParameterizedTypeReference<List<Category>>() {
            });
        }
        return categories;
    }

    public static synchronized List<Supplier> getSuppliers(boolean dataIsUpdated) {
        if (suppliers == null || dataIsUpdated) {
            suppliers = fetchAll("supplier", new ParameterizedTypeReference<List<Supplier>>() {
            });
        }
        return suppliers;
    }

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
            roles = fetchAll("role", new ParameterizedTypeReference<List<Role>>() {
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

    public synchronized static List<Section> getSections(boolean dataIsUpdated) {

        if (sections == null || dataIsUpdated) {
            sections = fetchAll("section", new ParameterizedTypeReference<List<Section>>() {
            });
        }

        return sections;
    }

    public synchronized static List<Brand> getBrands(boolean dataIsUpdated) {

        if (brands == null || dataIsUpdated) {
            brands = fetchAll("brand", new ParameterizedTypeReference<List<Brand>>() {
            });
        }

        return brands;
    }

    public static void main(String[] args) {
        getDepartments(false);
    }

    private static <T> T fetchAll(String baseUrl, ParameterizedTypeReference<T> param) {
        String restUrl = String.format(AbstractMainController.FETCH_URL, baseUrl);
        T result = fetchAllByUrl(restUrl, param);
        return result;
    }

    public static <T> T fetchAllByUrl(String restUrl, ParameterizedTypeReference<T> param) {
        return fetchAllByUrl(null, restUrl, param);
    }

    public static <T> T fetchAllByUrl(Object body, String restUrl, ParameterizedTypeReference<T> param) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(body, headers);
        ResponseEntity<T> response = restTemplate.exchange(restUrl, HttpMethod.POST, httpEntity, param);

        T list = response.getBody();
        return list;
    }

}
