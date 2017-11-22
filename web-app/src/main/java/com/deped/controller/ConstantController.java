package com.deped.controller;

public interface ConstantController {

    String URL_SEPARATOR = "/";
    String REST_CONTEXT_NAME = "rest";
    String ID_PATTERN = "{id}";
    String ID_STRING_LITERAL = "id";
    String FROM_STRING_LITERAL = "from";
    String TO_STRING_LITERAL = "to";
    String CREATE_PATTERN = URL_SEPARATOR + "create";
    String UPDATE_PATTERN = URL_SEPARATOR + "update";
    String RENDER_UPDATE_PATTERN = URL_SEPARATOR + "update" + URL_SEPARATOR + ID_PATTERN;
    String FETCH_PATTERN = URL_SEPARATOR + "list";
    String RANGE_PATTERN = URL_SEPARATOR + "{from}" + URL_SEPARATOR + "{to}";
    String FETCH_BY_ID_PATTERN = URL_SEPARATOR + ID_PATTERN;
    String REMOVE_PATTERN = URL_SEPARATOR + "remove";

    String DASH_SEPARATOR = "-";
    String JSP_PAGES = "pages";
    String CREATE_PAGE = "create" + DASH_SEPARATOR;
    String UPDATE_PAGE = "update" + DASH_SEPARATOR;
    String INFO_PAGE = DASH_SEPARATOR + "info";
    String LIST_PAGE = DASH_SEPARATOR + "list";

}
