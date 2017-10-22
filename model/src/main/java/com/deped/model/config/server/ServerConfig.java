package com.deped.model.config.server;

import javax.persistence.*;

@Table(name = "application_config")
@Entity
public class ServerConfig {

    @Id
    @Column(name = "key")
    @Enumerated(EnumType.STRING)
    private ServerEnumKey key;

    @Column(name = "value")
    private String value;

    public ServerConfig() {
    }

    public ServerConfig(ServerEnumKey key, String value) {
        this.key = key;
        this.value = value;
    }

    public ServerEnumKey getKey() {
        return key;
    }

    public void setKey(ServerEnumKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
