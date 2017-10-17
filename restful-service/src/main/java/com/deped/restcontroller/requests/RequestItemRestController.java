package com.deped.restcontroller.requests;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestItemRestController {

    @RequestMapping(value = "/request-item", method = RequestMethod.POST)
    public String itemRequest() {
        return "processing/request-item";
    }

}
