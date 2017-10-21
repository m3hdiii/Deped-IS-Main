package com.deped.utils;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

public class ImageUtils {

    public static boolean isImage(byte[] file) {
        try {
            String extension = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(file));
            if (extension != null && extension.startsWith("image")) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getExtension(byte[] file) {
        String contentType;
        try {
            contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(file));
            if (contentType == null || !contentType.startsWith("image")) {
                throw new IOException("some or and inconsistent file has passed");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType imageMimeType = null;
        try {
            imageMimeType = allTypes.forName(contentType);
        } catch (MimeTypeException e) {
            e.printStackTrace();
        }
        String imageExtension = imageMimeType.getExtension();
        return imageExtension;

    }

    public static String encodeBase64(byte[] fileByteArray) {
        String base64Encoded = Base64.getEncoder().encodeToString(fileByteArray);
        return base64Encoded;
    }

    public static byte[] decodeBase64(String base64File) {
        byte[] byteArray = Base64.getDecoder().decode(base64File);
        return byteArray;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }
}
