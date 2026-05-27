package com.cricket.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import jakarta.annotation.Resource;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class DataSourceConfig {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "hibernate.connection.driver_class";
    private static final String PROPERTY_NAME_DATABASE_URL = "hibernate.connection.local.url";
    private static final String PROPERTY_NAME_MEN_DATABASE_URL = "hibernate.connection.men.url";
    private static final String PROPERTY_NAME_WOMEN_DATABASE_URL = "hibernate.connection.women.url";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));

        DriverManagerDataSource men = new DriverManagerDataSource();
        men.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        men.setUrl(env.getRequiredProperty(PROPERTY_NAME_MEN_DATABASE_URL));
        
        DriverManagerDataSource women = new DriverManagerDataSource();
        women.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        women.setUrl(env.getRequiredProperty(PROPERTY_NAME_WOMEN_DATABASE_URL));
        
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("LOCAL", dataSource);
        targetDataSources.put("MEN", men);
        targetDataSources.put("WOMEN", women);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(dataSource);
        
        return routingDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());

        sessionFactory.setPackagesToScan("com.cricket.model");

        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.hbm2ddl.auto", "none"); 
        prop.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        prop.put("hibernate.id.new_generator_mappings", "false");
        prop.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        return prop;
    }    

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}