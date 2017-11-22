package com.deped.service.config;

import com.deped.config.SharedConfigData;
import com.deped.model.config.server.ServerEnumKey;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Override
    public Map<ServerEnumKey, String> getAppConfigs() {
        return SharedConfigData.getAppConfigs(false);
    }
}
