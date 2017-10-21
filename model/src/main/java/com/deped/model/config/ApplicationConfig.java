package com.deped.model.config;

import javax.persistence.*;

@Table(name = "application_config")
@Entity
public class ApplicationConfig {

    @Id
    @Column(name = "key")
    @Enumerated(EnumType.STRING)
    private AppConfigEnum key;

    @Column(name = "value")
    private String value;

    public ApplicationConfig() {
    }

    public ApplicationConfig(AppConfigEnum key, String value) {
        this.key = key;
        this.value = value;
    }

    public AppConfigEnum getKey() {
        return key;
    }

    public void setKey(AppConfigEnum key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
