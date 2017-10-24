package com.deped.form;

import com.deped.model.request.RequestDetails;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsForm {
    private Map<String, RequestDetails> map = new HashMap<>();

    public Map<String, RequestDetails> getMap() {
        return map;
    }

    public void setMap(Map<String, RequestDetails> map) {
        this.map = map;
    }
}
