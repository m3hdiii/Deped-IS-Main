package com.deped.service.items;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.items.Item;
import com.deped.repository.items.ItemRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import com.deped.utils.ImageUtils;
import com.deped.utils.SystemUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    private static final String USER_DIR_ROOT_PATH = SystemUtils.getRootPath();

    @Override
    public ResponseEntity<Item> create(Item entity) {

        final String baseFileUrl = "items";

        String pictureBase64 = entity.getPictureBase64();
        String fileName = null;
        if (pictureBase64 != null) {
            byte[] image = ImageUtils.decodeBase64(pictureBase64);
            fileName = SystemUtils.getRandomString() + ImageUtils.getExtension(image);
            try {
                String rootItemPathFolder = USER_DIR_ROOT_PATH.concat(File.separator).concat(baseFileUrl);
                File rootFolderItemPath = new File(rootItemPathFolder);
                if (!rootFolderItemPath.exists()) {
                    boolean isCreated = rootFolderItemPath.mkdir();
                    if (!isCreated) {
                        throw new NullPointerException("Permission and Access Problem ~");
                    }
                }

                String directoryAndFile = baseFileUrl.concat("/").concat(fileName);
                String filePathStr = rootItemPathFolder.concat(File.separator).concat(directoryAndFile);
                File file = new File(filePathStr);
                FileUtils.writeByteArrayToFile(file, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        entity.setPicName(fileName);
        Item savedEntity = itemRepository.create(entity);
        ResponseEntity<Item> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Item entity) {
        Boolean isUpdated = itemRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Item.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAll() {
        List<Item> items = itemRepository.fetchAll();
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchByRange(Range range) {
        List<Item> items = itemRepository.fetchByRange(range);
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Item> fetchById(Object id) {
        Item item = itemRepository.fetchById(id);
        ResponseEntity<Item> responseEntity = new ResponseEntity<>(item, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Item... entities) {
        Boolean isRemoved = itemRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Item.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAllGoods() {
        List<Item> items = itemRepository.fetchAllGoods();
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Item>> fetchAllSemiExpendable() {
        List<Item> items = itemRepository.fetchAllSemiExpendable();
        ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, OK);
        return responseEntity;
    }

    public static void main(String[] args) {

        String filePath = USER_DIR_ROOT_PATH.concat(File.separator).concat("items").concat(File.separator).concat("test.jpeg");
        System.out.println(filePath);
    }
}
