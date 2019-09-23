package com.by.byconfig.dynamicDS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainDataSourceConfig {
    @Value("${spring.main.datasource.username}")
    private String user;

    @Value("${spring.main.datasource.password}")
    private String password;

    @Value("${spring.main.datasource.url}")
    private String url;

    @Value("${spring.main.datasource.driver-class-name}")
    private String driverClass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
