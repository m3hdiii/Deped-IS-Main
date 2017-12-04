package com.deped.repository.items;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.repository.utils.HibernateFacade;
import com.deped.repository.utils.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deped.repository.utils.ConstantValues.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @Autowired
    private HibernateFacade hibernateFacade;

    @Override
    public Item create(Item entity) throws DatabaseRolesViolationException {
        Item savedItem = hibernateFacade.saveEntity(Item.class, entity);
        return savedItem;
    }

    @Override
    public Boolean update(Item entity) throws DatabaseRolesViolationException {
        String sqlQuery = "UPDATE item SET item_name = :itemName , description = :description, item_type = :itemType , function_type = :functionType , threshold = :threshold , pic_name = :picName WHERE item_name = :oldItemName ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("itemName", entity.getName());
        paramMap.put("description", entity.getDescription());
        paramMap.put("itemType", entity.getItemType().toString());
        paramMap.put("functionType", entity.getFunctionType().toString());
        paramMap.put("threshold", entity.getThreshold());
        paramMap.put("picName", entity.getPicName());
        paramMap.put("oldItemName", entity.getPreviousIdName());

        int rowAffected = hibernateFacade.updateEntitySqlQuery(sqlQuery, Item.class, paramMap);
        return rowAffected < 0;
    }

    @Override
    public List<Item> fetchAll() {
        List<Item> items = hibernateFacade.fetchAllEntity(FETCH_ALL_ITEMS, Item.class);
        return items;
    }

    @Override
    public List<Item> fetchByRange(Range range) {
        List<Item> items = hibernateFacade.fetchAllEntity(FETCH_ALL_ITEMS, range, Item.class);
        return items;
    }

    @Override
    public Item fetchById(String id) {
        Item item = hibernateFacade.fetchEntityById(Item.class, id);
        return item;
    }

    @Override
    public Boolean remove(Item... entities) throws DatabaseRolesViolationException {
        Boolean isItemDeleted = hibernateFacade.removeEntities(ITEM_TABLE, ITEM_TABLE_ID, entities);
        return isItemDeleted;
    }

    @Override
    public Boolean createOrUpdateAll(Item... entities) throws DatabaseRolesViolationException {
        return null;
    }

    @Override
    public List<Item> fetchAllByItemType(ItemType itemType) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("itemType", itemType);
        List<Item> items = hibernateFacade.fetchAllByParameterMap(FETCH_ALL_ITEMS_BY_TYPE, Item.class, parameterMap);
        return items;
    }
}
