package com.deped.utils;

import org.apache.commons.text.RandomStringGenerator;

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

        String random = randomText.generate(10);
        return random;
    }
}
