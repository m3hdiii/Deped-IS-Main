package com.deped.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {

    private static final String PAGES = "pages";

    @RequestMapping(value = PAGES, method = RequestMethod.GET)
    public String indexPage() {
        return PAGES;
    }
}
