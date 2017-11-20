package com.deped.utils;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import javax.activation.MimetypesFileTypeMap;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Base64;

public class ImageUtils {

    public static boolean isImage(byte[] file) {
        try {
            String extension = Magic.getMagicMatch(file, false).getMimeType();
            if (extension != null && extension.startsWith("image")) {
                return true;
            }
        }  catch (MagicException e) {
            e.printStackTrace();
        } catch (MagicParseException e) {
            e.printStackTrace();
        } catch (MagicMatchNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getExtension(byte[] file) {
        String contentType = null;
        try {
            contentType =  Magic.getMagicMatch(file, false).getMimeType();
            if (contentType == null || !contentType.startsWith("image")) {
                throw new IOException("some or and inconsistent file has passed");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (MagicException e) {
            e.printStackTrace();
            return null;
        } catch (MagicParseException e) {
            e.printStackTrace();
            return null;
        } catch (MagicMatchNotFoundException e) {
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
        try {
            byte[] bFile = Files.readAllBytes(new File("C:\\Users\\Colvin\\Desktop\\5f59f60f.jpg").toPath());
            ImageUtils.getExtension(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
