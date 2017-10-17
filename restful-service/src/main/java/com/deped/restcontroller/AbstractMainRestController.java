package com.deped.restcontroller;

import com.deped.model.Operation;

public abstract class AbstractMainRestController<T, ID> implements MainRestController<T, ID> {

    public static final String createUrl(Operation operation, Class<?> clazz) {
        return "";
    }
}
