package com.deped.service;

import com.deped.config.SharedConfigData;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.ResponseStatus;
import com.deped.model.config.server.ServerEnumKey;
import com.deped.utils.ImageUtils;
import com.deped.utils.SystemUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ServiceUtils implements ConstantServiceValues {

    private static final String USER_DIR_ROOT_PATH = SharedConfigData.getAppConfigs(false).get(ServerEnumKey.RESOURCE_PATH_ON_DISK);

    public static String saveImageIntoDisk(String pictureBase64, String baseFileUrl) {

        String fileName = null;
        if (pictureBase64 != null) {
            byte[] image = ImageUtils.decodeBase64(pictureBase64);
            fileName = SystemUtils.getRandomString() + ImageUtils.getExtension(image);
            try {
                String rootItemPathFolder = USER_DIR_ROOT_PATH.concat(baseFileUrl);
                File rootFolderItemPath = new File(rootItemPathFolder);
                if (!rootFolderItemPath.exists()) {
                    boolean isCreated = rootFolderItemPath.mkdir();
                    if (!isCreated) {
                        throw new NullPointerException("Permission or Access Problem ~");
                    }
                }

                String filePathStr = rootItemPathFolder.concat(File.separator).concat(fileName);
                File file = new File(filePathStr);
                FileUtils.writeByteArrayToFile(file, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baseFileUrl.concat("/").concat(fileName);
        }
        return null;
    }


    public final static Response makeResponse(Boolean isSuccessful, Operation operation, Class<?> entity) {
        String successMessage = "", failureMessage = "";
        switch (operation) {
            case CREATE:
                successMessage = String.format(CREATE_SUCCESSFUL_MESSAGE, entity.getSimpleName());
                failureMessage = String.format(CREATE_FAILURE_MESSAGE, entity.getSimpleName());
                break;
            case DELETE:
                successMessage = String.format(DELETE_SUCCESSFUL_MESSAGE, entity.getSimpleName());
                failureMessage = String.format(DELETE_FAILURE_MESSAGE, entity.getSimpleName());
                break;
            case UPDATE:
                successMessage = String.format(UPDATE_SUCCESSFUL_MESSAGE, entity.getSimpleName());
                failureMessage = String.format(UPDATE_FAILURE_MESSAGE, entity.getSimpleName());
                break;
        }

        if (isSuccessful) {
            return new Response(ResponseStatus.SUCCESSFUL, successMessage);
        } else {
            return new Response(ResponseStatus.FAILED, failureMessage);
        }
    }
}
