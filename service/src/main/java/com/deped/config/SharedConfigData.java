package com.deped.config;

import com.deped.model.config.AppConfigEnum;
import com.deped.model.config.ApplicationConfig;
import com.deped.repository.utils.HibernateFacade;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SharedConfigData {

    private static Map<AppConfigEnum, String> appConfigMap;

    public synchronized static Map<AppConfigEnum, String> getAppConfigs(boolean isDataUpdated) {
        if (appConfigMap == null || isDataUpdated) {
            appConfigMap = new EnumMap<>(AppConfigEnum.class);
            Session session = HibernateFacade.getSessionFactory().openSession();
            NativeQuery<ApplicationConfig> nativeQuery = session.createNativeQuery("SELECT * FROM application_config", ApplicationConfig.class);
            List<ApplicationConfig> configs = nativeQuery.list();
            session.close();
            for (ApplicationConfig ac : configs) {
                appConfigMap.put(ac.getKey(), ac.getValue());
            }
        }

        return appConfigMap;
    }
}
