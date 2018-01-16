package com.deped.repository.items;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.account.User;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.items.features.Colour;
import com.deped.model.items.features.Condition;
import com.deped.model.items.features.EquipmentAvailability;
import com.deped.model.items.features.Material;
import com.deped.model.order.CaptureInfo;
import com.deped.model.search.BorrowHistorySearch;
import com.deped.model.search.BorrowSearch;
import com.deped.repository.ListParameter;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemDetailsRepositoryImpl implements ItemDetailsRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public ItemDetails create(ItemDetails entity) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public Boolean update(ItemDetails entity) throws DatabaseRolesViolationException {

        String officeSerialNo = entity.getOfficeSerialNo();
        EquipmentAvailability availability = entity.getEquipmentAvailability();
        Condition condition = entity.getCondition();
        if (entity == null || officeSerialNo == null || availability == null || condition == null) {
            return null;
        }

        String query = "UPDATE item_details SET equipment_availability = :equipmentAvailability, equipment_condition = :equipmentCondition WHERE office_serial_no = :officeSerialNo";
        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Transaction tx = null;
        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery<ItemDetails> nativeQuery = hibernateSession.createNativeQuery(query, ItemDetails.class);
            nativeQuery.setParameter("equipmentAvailability", entity.getEquipmentAvailability().toString());
            nativeQuery.setParameter("equipmentCondition", entity.getCondition().toString());
            nativeQuery.setParameter("officeSerialNo", entity.getOfficeSerialNo());
            nativeQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return false;
        }

        return true;
    }

    @Override
    public List<ItemDetails> fetchAll() {
        return hibernateFacade.fetchAllEntity("getAll", ItemDetails.class);
    }

    @Override
    public List<ItemDetails> fetchByRange(Range range) {
        return null;
    }

    @Override
    public ItemDetails fetchById(String id) {
        return hibernateFacade.fetchEntityById(ItemDetails.class, id);
    }

    @Override
    public Boolean remove(ItemDetails... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public Boolean createOrUpdateAll(ItemDetails... entities) throws DatabaseRolesViolationException {
        Boolean isDone = hibernateFacade.createOrUpdateAll(ItemDetails.class, entities);
        return isDone;
    }

    @Override
    public List<CaptureInfo> fetchToBeCaptureInfo(ItemType[] itemTypes) {
//        String fetchQuery = "SELECT order_details_constant_key AS 'order_details', count(*) AS inserted_equipment, (order_details.total_quantity_arrived_no - count(*)) AS must_insert, \n" +
//                "order_details.total_quantity_arrived_no AS arrived_number , total_quantity_requested_no,  item_item_name AS 'item_name', item.pic_name AS pic_name, item.quantity AS item_quantity\n" +
//                "FROM item_details JOIN order_details USING(order_details_constant_key) JOIN item ON order_details.item_item_name = item.item_name\n" +
//                "WHERE order_details_constant_key IN (SELECT order_details_constant_key FROM order_details INNER JOIN item on order_details.item_item_name = item_name WHERE item_type IN ( %s ) AND order_order_id = :orderId \n" +
//                "ORDER BY order_details_state)  group by order_details_constant_key";

        String fetchQuery = "select count(item_details.item_name), (cast(quantity as signed) - count(item_details.item_name)), item.quantity, item.item_name, item.pic_name, item.quantity AS tmp FROM item LEFT JOIN item_details using(item_name) WHERE item_type IN (%s) GROUP BY item_name";
        StringBuilder sb = new StringBuilder();
        for (ItemType it : itemTypes) {
            sb
                    .append("'")
                    .append(it.toString())
                    .append("' ,");
        }

        String formatValue = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String query = String.format(fetchQuery, formatValue);

        Session hibernateSession = null;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;
        List<Object[]> list;
        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery nativeQuery = hibernateSession.createNativeQuery(query);

//            nativeQuery.setParameter("orderId", orderId);
            list = nativeQuery.list();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        }

        List<CaptureInfo> captureInfoList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Object[] o : list) {


                Long numberOfCapturedItems = Long.valueOf(o[0] + "");
                Long numberOfRemainingCapturedItems = Long.valueOf(o[1] + "");
                Long totalQuantityRequested = Long.valueOf(o[2] + "");
                String itemName = o[3] + "";
                String pictureName = o[4] + "";
                Long numberOfStockInSupplyOffice = Long.valueOf(o[5] + "");
                captureInfoList.add(new CaptureInfo(numberOfCapturedItems, numberOfRemainingCapturedItems,
                        totalQuantityRequested, itemName, pictureName, numberOfStockInSupplyOffice));
            }
        }

        return captureInfoList;
    }

    @Override
    public List<ItemDetails> fetchAllByItemName(String itemName) {

        String query = "SELECT * FROM item_details WHERE item_name = :itemName AND equipment_condition = 'OK' AND equipment_availability = 'AVAILABLE'";
        Map<String, Object> map = new HashMap<>();
        map.put("itemName", itemName);
        List<ItemDetails> list = hibernateFacade.fetchAllEntityBySqlQuery(query, null, ItemDetails.class, map);
        return list;
    }


    @Override
    public List<ItemDetails> fetchAllByOrderIdAndItemType(Long orderId, ItemType[] itemTypes) {
        String fetchQuery = "SELECT * FROM item_details JOIN order_details USING(order_details_constant_key) WHERE order_details_constant_key IN (SELECT order_details_constant_key FROM order_details INNER JOIN item on order_details.item_item_name = item_name WHERE item_type IN ( %s  ) AND order_order_id = :orderId ORDER BY order_details_state)";
        StringBuilder sb = new StringBuilder();
        for (ItemType it : itemTypes) {
            sb
                    .append("'")
                    .append(it.toString())
                    .append("' ,");
        }

        String formatValue = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String query = String.format(fetchQuery, formatValue);

        Session hibernateSession = null;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;

        List<ItemDetails> list;
        try {
            tx = hibernateSession.beginTransaction();
            NativeQuery<ItemDetails> nativeQuery = hibernateSession.createNativeQuery(query, ItemDetails.class);
            nativeQuery.setParameter("orderId", orderId);
            list = nativeQuery.list();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null)
                tx.rollback();
            return null;
        }

        return list;
    }


    public List<ItemDetails> itemDetailsSearch(BorrowSearch borrowSearch) {

        if (borrowSearch == null)
            return null;

        boolean isOfficialSerialNoEmpty = isEmpty(borrowSearch.getOfficeSerialNo());
        boolean isColorListEmpty = isEmpty(borrowSearch.getColours());
        boolean isConditionListEmpty = isEmpty(borrowSearch.getConditions());
        boolean isPurchasePriceFromEmpty = isEmpty(borrowSearch.getPurchasePriceFrom());
        boolean isPurchasePriceToEmpty = isEmpty(borrowSearch.getPurchasePriceTo());
        boolean isEquipmentAvailabilityListEmpty = isEmpty(borrowSearch.getEquipmentAvailabilities());
        boolean isEquipmentSerialNumberEmpty = isEmpty(borrowSearch.getEquipmentSerialNo());
        boolean isMaterialListEmpty = isEmpty(borrowSearch.getMaterials());
        boolean isWeightInGramFromEmpty = isEmpty(borrowSearch.getWeightInGramFrom());
        boolean isWeightInGramToEmpty = isEmpty(borrowSearch.getWeightInGramTo());
        boolean isLifeSpanFromEmpty = isEmpty(borrowSearch.getLifeSpanFrom());
        boolean isLifeSpanToEmpty = isEmpty(borrowSearch.getLifeSpanTo());
        boolean isItemListEmpty = isEmpty(borrowSearch.getItems());
        boolean isOwnByListEmpty = isEmpty(borrowSearch.getOwnBy());

        boolean[] emptyList = new boolean[]{
                isOfficialSerialNoEmpty, isColorListEmpty, isConditionListEmpty, isPurchasePriceFromEmpty,
                isPurchasePriceToEmpty, isEquipmentAvailabilityListEmpty, isEquipmentSerialNumberEmpty,
                isMaterialListEmpty, isWeightInGramFromEmpty, isWeightInGramToEmpty, isLifeSpanFromEmpty,
                isLifeSpanToEmpty, isItemListEmpty, isOwnByListEmpty
        };

        StringBuilder sb = new StringBuilder("SELECT * FROM item_details");

        String where = null;
        for (boolean b : emptyList) {
            if (!b) {
                where = " WHERE\n";
                break;
            }
        }


        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;
        List<ItemDetails> list = null;

        if (where == null) {

            try {
                tx = hibernateSession.beginTransaction();
                NativeQuery<ItemDetails> query = hibernateSession.createNativeQuery(sb.toString(), ItemDetails.class);
                list = query.list();
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return list;
            }


            return list;

        } else {
            sb.append(where);

            ListParameter colorListParameter = isColorListEmpty ? null : createListParameter(borrowSearch.getColours(), "colour", Colour.class);
            ListParameter conditionListParameter = isConditionListEmpty ? null : createListParameter(borrowSearch.getConditions(), "equipmentCondition", Condition.class);
            ListParameter equipmentAvailabilityListParameter = isEquipmentAvailabilityListEmpty ? null : createListParameter(borrowSearch.getEquipmentAvailabilities(), "equipmentAvailability", EquipmentAvailability.class);
            ListParameter materialListParameter = isMaterialListEmpty ? null : createListParameter(borrowSearch.getMaterials(), "material", Material.class);
            ListParameter itemListParameter = isItemListEmpty ? null : createListParameter(borrowSearch.getItems(), "item", Item.class);
            ListParameter ownByListParameter = isOwnByListEmpty ? null : createListParameter(borrowSearch.getOwnBy(), "ownBy", User.class);


            sb
                    .append(isOfficialSerialNoEmpty ? "" : "(office_serial_number LIKE :officeSerialNo) AND\n")
                    .append(isColorListEmpty ? "" : String.format("(colour IN ( %s )) AND\n", colorListParameter.getWherePartSection()))
                    .append(isConditionListEmpty ? "" : String.format("(equipment_condition IN ( %s )) AND\n", conditionListParameter.getWherePartSection()))
                    .append(isPurchasePriceFromEmpty ? "" : "(purchase_price >= :purchasePriceFrom) AND\n")
                    .append(isPurchasePriceToEmpty ? "" : "(purchase_price < :purchasePriceTo) AND\n")
                    .append(isEquipmentAvailabilityListEmpty ? "" : String.format("(equipment_availability IN ( %s )) AND\n", equipmentAvailabilityListParameter.getWherePartSection()))
                    .append(isEquipmentSerialNumberEmpty ? "" : "(equipment_serial_number LIKE :equipmentSerialNumber) AND\n")
                    .append(isMaterialListEmpty ? "" : String.format("(material IN ( %s )) AND\n", materialListParameter.getWherePartSection()))
                    .append(isWeightInGramFromEmpty ? "" : "(weight_in_gram >= :weightInGramFrom) AND\n")
                    .append(isWeightInGramToEmpty ? "" : "(weight_in_gram >= :weightInGramTo) AND\n")
                    .append(isLifeSpanFromEmpty ? "" : "(life_span >= :lifeSpanFrom) AND\n")
                    .append(isLifeSpanToEmpty ? "" : "(life_span >= :lifeSpanTo) AND\n")
                    .append(isItemListEmpty ? "" : String.format("(item_name IN ( %s )) AND\n", itemListParameter.getWherePartSection()))
                    .append(isOwnByListEmpty ? "" : String.format("(owns_by IN ( %s ))\n", ownByListParameter.getWherePartSection()));


            try {
                tx = hibernateSession.beginTransaction();
                String strQuery = sb.toString().trim();
                if (strQuery.substring(strQuery.lastIndexOf(" ") + 1).equals("AND")) {
                    strQuery = strQuery.substring(0, strQuery.lastIndexOf("AND"));
                }

                NativeQuery<ItemDetails> query = hibernateSession.createNativeQuery(strQuery, ItemDetails.class);

                ListParameter[] listParameters = new ListParameter[]{
                        colorListParameter, conditionListParameter, equipmentAvailabilityListParameter, materialListParameter,
                        itemListParameter, ownByListParameter
                };

                Map<String, Object> parameterMap = new HashMap<>();
                for (int i = 0; i < listParameters.length; i++) {
                    ListParameter lpTemp = listParameters[i];
                    if (lpTemp != null) {
                        parameterMap.putAll(lpTemp.getParameterMap());
                    }
                }

                if (!isOfficialSerialNoEmpty) {
                    parameterMap.put("officeSerialNo", "%" + borrowSearch.getOfficeSerialNo() + "%");
                }

                if (!isPurchasePriceFromEmpty) {
                    parameterMap.put("purchasePriceFrom", borrowSearch.getPurchasePriceFrom());
                }

                if (!isPurchasePriceToEmpty) {
                    parameterMap.put("purchasePriceTo", borrowSearch.getPurchasePriceTo());
                }

                if (!isEquipmentSerialNumberEmpty) {
                    parameterMap.put("equipmentSerialNumber", "%" + borrowSearch.getEquipmentSerialNo() + "%");
                }

                if (!isWeightInGramFromEmpty) {
                    parameterMap.put("weightInGramFrom", borrowSearch.getWeightInGramFrom());
                }

                if (!isWeightInGramToEmpty) {
                    parameterMap.put("weightInGramTo", borrowSearch.getWeightInGramTo());
                }

                if (!isLifeSpanFromEmpty) {
                    parameterMap.put("lifeSpanFrom", borrowSearch.getLifeSpanFrom());
                }

                if (!isLifeSpanToEmpty) {
                    parameterMap.put("lifeSpanTo", borrowSearch.getLifeSpanTo());
                }

                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.setParameter(key, value);
                }

                list = query.list();
                tx.commit();

            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return null;
            }


            return list;
        }

    }

    @Override
    public List<BorrowItem> borrowItemSearch(BorrowHistorySearch entity) {
        if (entity == null) {
            return null;
        }

        String officeSerialNo = entity.getOfficeSerialNo();
        String username = entity.getUsername();
        Date borrowDateFrom = entity.getBorrowDateFrom();
        Date borrowDateTo = entity.getBorrowDateTo();
        Date returnDateFrom = entity.getReturnDateFrom();
        Date returnDateTo = entity.getReturnDateTo();

        boolean isOfficeSerialNoEmpty = isEmpty(officeSerialNo);
        boolean isUsernameEmpty = isEmpty(username);
        boolean isBorrowDateFromEmpty = isEmpty(borrowDateFrom);
        boolean isBorrowDateToEmpty = isEmpty(borrowDateTo);
        boolean isReturnDateFromEmpty = isEmpty(returnDateFrom);
        boolean isReturnDateToEmpty = isEmpty(returnDateTo);

        boolean[] emptyList = new boolean[]{
                isOfficeSerialNoEmpty, isUsernameEmpty, isBorrowDateFromEmpty, isBorrowDateToEmpty
                , isReturnDateFromEmpty, isReturnDateToEmpty
        };

        StringBuilder sb = new StringBuilder("SELECT * FROM borrow_item");

        String where = null;
        for (boolean b : emptyList) {
            if (!b) {
                where = " WHERE\n";
                break;
            }
        }

        Session hibernateSession;
        try {
            hibernateSession = hibernateFacade.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction tx = null;
        List<BorrowItem> list = null;

        if (where == null) {
            try {
                tx = hibernateSession.beginTransaction();
                NativeQuery<BorrowItem> query = hibernateSession.createNativeQuery(sb.toString(), BorrowItem.class);
                list = query.list();
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return list;
            }


            return list;

        } else {
            sb.append(where);
            sb
                    .append(isOfficeSerialNoEmpty ? "" : "( item_office_serial_no LIKE :officeSerialNo ) AND\n")
                    .append(isUsernameEmpty ? "" : "( username LIKE :username ) AND\n")
                    .append(isBorrowDateFromEmpty ? "" : "( date_borrowed >= :borrowDateFrom ) AND\n")
                    .append(isBorrowDateToEmpty ? "" : "( date_borrowed < :borrowDateTo ) AND\n")
                    .append(isReturnDateFromEmpty ? "" : "( date_return >= :returnDateFrom ) AND\n")
                    .append(isReturnDateToEmpty ? "" : "( date_return < :returnDateTo )");


            try {
                tx = hibernateSession.beginTransaction();
                String strQuery = sb.toString().trim();
                if (strQuery.substring(strQuery.lastIndexOf(" ") + 1).equals("AND")) {
                    strQuery = strQuery.substring(0, strQuery.lastIndexOf("AND"));
                }

                NativeQuery<BorrowItem> query = hibernateSession.createNativeQuery(strQuery, BorrowItem.class);

                Map<String, Object> parameterMap = new HashMap<>();
                if (!isOfficeSerialNoEmpty) {
                    parameterMap.put("officeSerialNo", "%" + entity.getOfficeSerialNo() + "%");
                }

                if (!isUsernameEmpty) {
                    parameterMap.put("username", entity.getUsername());
                }

                if (!isBorrowDateFromEmpty) {
                    parameterMap.put("borrowDateFrom", entity.getBorrowDateFrom());
                }

                if (!isBorrowDateToEmpty) {
                    parameterMap.put("borrowDateTo", entity.getBorrowDateTo());
                }

                if (!isReturnDateFromEmpty) {
                    parameterMap.put("returnDateFrom", entity.getReturnDateFrom());
                }

                if (!isReturnDateToEmpty) {
                    parameterMap.put("returnDateTo", entity.getReturnDateTo());
                }

                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.setParameter(key, value);
                }

                list = query.list();
                tx.commit();

            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null)
                    tx.rollback();
                return null;
            }

        }

        return list;
    }


    private ListParameter createListParameter(List objects, String baseParamKey, Class<?> clazz) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<String, Object> parameterMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < objects.size(); i++) {
            String key = baseParamKey + i;
            if (clazz == User.class)
                parameterMap.put(key, ((User) objects.get(i)).getUsername());

            if (clazz == Item.class)
                parameterMap.put(key, ((Item) objects.get(i)).getName());

            if (clazz == Colour.class ||
                    clazz == Condition.class ||
                    clazz == EquipmentAvailability.class ||
                    clazz == Material.class)
                parameterMap.put(key, (objects.get(i)).toString());

            sb.append(":" + key + " , ");
        }

        String tmp = sb.toString();
        String wherePartSection = tmp.substring(0, (tmp.lastIndexOf(",") - 1));

        ListParameter listParameter = new ListParameter(wherePartSection, parameterMap);
        return listParameter;
    }

    private boolean isEmpty(Object object) {
        if (object == null)
            return true;

        if (object instanceof Date) {
            return false;
        }

        if (object instanceof Number)
            return false;

        if (object instanceof String) {
            String s = (String) object;
            if (s.isEmpty()) {
                return true;
            }
            return false;
        }

        if (object instanceof List) {
            List s = (List) object;
            if (s.isEmpty()) {
                return true;
            }
            return false;
        }

        return true;
    }


}
