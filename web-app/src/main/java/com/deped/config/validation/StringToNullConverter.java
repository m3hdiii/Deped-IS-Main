package com.deped.config.validation;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToNullConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;

        }
        return source;
    }
}