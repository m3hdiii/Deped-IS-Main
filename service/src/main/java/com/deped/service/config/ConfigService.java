package com.deped.service.config;

import com.deped.model.config.server.ServerEnumKey;

import java.util.Map;

public interface ConfigService {

    Map<ServerEnumKey, String> getAppConfigs();
}
