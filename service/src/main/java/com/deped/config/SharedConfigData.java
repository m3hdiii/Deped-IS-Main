package com.deped.config;

import com.deped.model.config.server.ServerEnumKey;
import com.deped.model.config.server.ServerConfig;
import com.deped.repository.utils.HibernateFacade;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SharedConfigData {

    private static Map<ServerEnumKey, String> appConfigMap;

    public synchronized static Map<ServerEnumKey, String> getAppConfigs(boolean isDataUpdated) {
        if (appConfigMap == null || isDataUpdated) {
            appConfigMap = new EnumMap<>(ServerEnumKey.class);
            Session session = HibernateFacade.getSessionFactory().openSession();
            NativeQuery<ServerConfig> nativeQuery = session.createNativeQuery("SELECT * FROM application_config", ServerConfig.class);
            List<ServerConfig> configs = nativeQuery.list();
            session.close();
            for (ServerConfig ac : configs) {
                appConfigMap.put(ac.getKey(), ac.getValue());
            }
        }

        return appConfigMap;
    }
}
