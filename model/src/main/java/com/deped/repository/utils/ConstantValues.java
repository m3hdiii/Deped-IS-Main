package com.deped.repository.utils;

public interface ConstantValues {
    //Additional NamedQuery
    String FIND_BY_USERNAME = "findByUsernamePassword";
    String FIND_BY_EMAIL = "findByEmailPassword";

    //NamedQueries
    String FETCH_ALL_ITEMS = "fetchAllItems";
    String FETCH_ALL_ITEMS_BY_TYPE = "fetchAllItemsByType";
    String FETCH_ALL_USERS = "findAllUsers";
    String FETCH_ALL_DEPARTMENTS = "fetchAllDepartments";
    String FETCH_ALL_DEPARTMENT_RANGES = "fetchDepartments";
    String FETCH_ALL_BRANDS = "fetchBrands";
    String FETCH_ALL_BRAND_RANGES = "fetchBrandsByRange";
    String FETCH_ALL_EQUIPMENTS = "fetchEquipments";
    String FETCH_ALL_EQUIPMENTS_RANGE = "fetchEquipmentsByRange";
    String FETCH_ALL_EQUIPMENT_INFO = "fetchEquipmentInfo";
    String FETCH_ALL_EQUIPMENT_INFO_RANGE = "fetchEquipmentInfoByRange";
    String FETCH_ALL_PACKS = "fetchPacks";
    String FETCH_ALL_PACKS_RANGE = "fetchPacksByRange";
    String FETCH_ALL_CATEGORY = "fetchCategories1";
    String FETCH_ALL_CATEGORY_RANGE = "fetchCategoriesByRange";
    String FETCH_ALL_SUPPLY = "fetchAllSupplies";
    String FETCH_ALL_SUPPLY_RANGE = "fetchAllSuppliesByRange";
    String FETCH_ALL_SECTION = "fetchAllSections";
    String FETCH_ALL_SECTION_RANGE = "";


    //Tables and TableId Name
    String USER_TABLE = "user";
    String USER_TABLE_ID = "user_id";
    String DEPARTMENT_TABLE = "department";
    String DEPARTMENT_TABLE_ID = "department_id";
    String BRAND_TABLE = "brand";
    String BRAND_TABLE_ID = "brand_id";
    String EQUIPMENTS_TABLE = "equipment";
    String EQUIPMENTS_TABLE_ID = "equipment_id";
    String EQUIPMENT_INFO_TABLE = "equipment_info";
    String EQUIPMENT_INFO_TABLE_ID = "equipment_info_id";
    String PACK_TABLE = "pack";
    String PACK_TABLE_ID = "pack_id";
    String CATEGORY_TABLE = "category";
    String CATEGORY_TABLE_ID = "category_id";
    String SUPPLY_TABLE = "supply";
    String SUPPLY_TABLE_ID = "supply_id";
    String ITEM_TABLE = "item";
    String ITEM_TABLE_ID = "item_id";
    String SECTION_TABLE = "section";
    String SECTION_TABLE_ID = "section";
}
