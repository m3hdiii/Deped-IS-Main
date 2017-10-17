package com.deped.restcontroller;

public interface ConstantController {

    String URL_SEPARATOR = "/";
    String CREATE_PATTERN = URL_SEPARATOR + "create";
    String UPDATE_PATTERN = URL_SEPARATOR + "update";
    String FETCH_PATTERN = URL_SEPARATOR + "fetch-all";
    String RANGE_PATTERN = URL_SEPARATOR + "{from}" + URL_SEPARATOR + "{to}";
    String FETCH_BY_ID_PATTERN = URL_SEPARATOR + "{id}";
    String REMOVE_PATTERN = URL_SEPARATOR + "remove";
    String SHOW_PREFIX = URL_SEPARATOR + "show-all";

    String FROM_STRING_LITERAL = "from";
    String TO_STRING_LITERAL = "to";
    String ID_STRING_LITERAL = "id";
}
