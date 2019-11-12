package com.by.byconfig.dynamicDS;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Mybatis配置
 * @author Louis
 * @date Oct 31, 2018
 */
@Configuration
public class MybatisConfig {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private MainDataSourceConfig mainDataSourceConfig;

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        Connection connection = null;
        //置入主数据库连接
        try {
            ComboPooledDataSource mainDs = new ComboPooledDataSource();
            try {
                mainDs.setDriverClass(mainDataSourceConfig.getDriverClass());
                mainDs.setUser(mainDataSourceConfig.getUser());
                mainDs.setPassword(mainDataSourceConfig.getPassword());
                mainDs.setJdbcUrl(mainDataSourceConfig.getUrl());
                mainDs.setAutomaticTestTable("Test");
                mainDs.setAcquireIncrement(3); //当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
                mainDs.setIdleConnectionTestPeriod(60);//每60秒检查所有连接池中的空闲连接
                mainDs.setMaxIdleTime(60); //最大空闲时间,60秒内未使用则连接被丢弃。
                mainDs.setMaxPoolSize(100);//连接池中保留的最大连接数
                DynamicDataSourceContextHolder.dbMap.put("main","main");
                dataSourceMap.put("main", mainDs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //置入ca数据库连接
            String driverClassName = dataSourceConfig.getDriverClass();
            String url = dataSourceConfig.getUrl();
            String username = dataSourceConfig.getUser();
            String password = dataSourceConfig.getPassword();
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT b.dbid,a.gsdm,b.dburl,b.dbloginname,b.dbpassword,b.dbdriver " +
                    "from ca_crm a INNER JOIN ca_db b on a.dbid = b.dbid where b.dbtype='2'  and isnull(b.cpversion,'') not like '%停用%'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                try {
                    DynamicDataSourceContextHolder.dbMap.put(resultSet.getString("gsdm"),resultSet.getString("dbid"));
                    if (!dataSourceMap.containsKey(resultSet.getString("dbid"))) {
                        ComboPooledDataSource ds = new ComboPooledDataSource();
                        ds.setDriverClass(resultSet.getString("dbdriver"));
                        ds.setUser(resultSet.getString("dbloginname"));
                        ds.setPassword(resultSet.getString("dbpassword"));
                        ds.setJdbcUrl(resultSet.getString("dburl"));
                        ds.setAutomaticTestTable("Test");
                        ds.setAcquireIncrement(3); //当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
                        ds.setIdleConnectionTestPeriod(60);//每60秒检查所有连接池中的空闲连接
                        ds.setMaxIdleTime(60); //最大空闲时间,60秒内未使用则连接被丢弃。
                        ds.setMaxPoolSize(100);//连接池中保留的最大连接数
                        dataSourceMap.put(resultSet.getString("dbid"), ds);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        dynamicDataSource.setDataSources(dataSourceMap);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

   @Bean("sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }

}