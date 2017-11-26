package com.deped.tools;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ControllerImageUtils {

    public static String imageUploadProcessing(MultipartFile pic, BindingResult bindingResult) {
        try {
            byte[] fileBytes = pic.getBytes();
            if (pic != null && fileBytes != null && fileBytes.length != 0) {
                boolean isImage = com.deped.utils.ImageUtils.isImage(fileBytes);
                if (isImage) {
                    String encodeBase64 = com.deped.utils.ImageUtils.encodeBase64(fileBytes);
                    return encodeBase64;
                } else {
                    bindingResult.addError(new FieldError("Item", "pictureBase64", "Your file is suspicious and it's not an image. Your actions will be logged"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
