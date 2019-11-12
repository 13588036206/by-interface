package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages ={"com.*",})
@MapperScan(value = {"com.by.dao.mapper","com.youzan.dao.mapper"})
@ServletComponentScan(basePackages = {"com.common.filter"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class ByWebApplication extends SpringBootServletInitializer {
    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ByWebApplication.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(ByWebApplication.class, args);
    }

}
