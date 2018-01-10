package com.deped.repository;

import java.util.Map;

public class ListParameter {
    String wherePartSection;
    Map<String, Object> parameterMap;

    public ListParameter(String wherePartSection, Map<String, Object> parameterMap) {
        this.wherePartSection = wherePartSection;
        this.parameterMap = parameterMap;
    }

    public String getWherePartSection() {
        return wherePartSection;
    }

    public void setWherePartSection(String wherePartSection) {
        this.wherePartSection = wherePartSection;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}