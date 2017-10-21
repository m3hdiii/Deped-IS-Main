package com.deped.model.config;

public class ApplicationConfigs {

    private String resourcePath = System.getProperty("user.dir");

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
