package com.deped.utils;

import org.apache.commons.text.RandomStringGenerator;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class SystemUtils {

    public static String getRootPath() {
        String path = System.getProperty("user.dir");
        return path;
    }

    public static String getRandomString() {
        RandomStringGenerator randomText = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();

        String random =
                randomText.generate(10)
                        .concat("-")
                        .concat(String.valueOf(new Date().getTime()));
        return random;
    }


    /**
     * This Method will work only if you have override the equal method in related Object properly
     *
     * @param elements
     * @param e
     * @param <E>
     * @return
     */
    public static <E> E findElementInList(List<E> elements, E e) {
        for (E element : elements) {
            if (element.equals(e)) {
                return element;
            }
        }
        return null;
    }

    public static boolean isAlphaNumeric(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]*$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }

        return true;
    }

}
