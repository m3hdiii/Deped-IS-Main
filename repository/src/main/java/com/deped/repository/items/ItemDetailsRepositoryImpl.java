package com.deped.repository.items;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.order.CaptureInfo;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return null;
    }

    @Override
    public List<ItemDetails> fetchAll() {
        return null;
    }

    @Override
    public List<ItemDetails> fetchByRange(Range range) {
        return null;
    }

    @Override
    public ItemDetails fetchById(String s) {
        return null;
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
}
