package com.admin.tbot.configuration;

import com.admin.tbot.aspects.LoggingAspect;
import com.admin.tbot.repositories.botApi.BotApiRepository;
import com.admin.tbot.services.botApi.BotApiService;
import com.admin.tbot.botLogics.*;
import com.admin.tbot.repositories.UserListRepository;
import com.admin.tbot.repositories.WeatherApiRepository;
import com.admin.tbot.services.botApi.BotApiButtons;
import com.admin.tbot.services.UserListService;
import com.admin.tbot.services.WeatherApiService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.admin.tbot")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class Config {

    @Value("${hibernate.jdbcUrl}")
    private String hibernateJdbsUrl;

    @Value("${hibernate.user}")
    private String hibernateUser;

    @Value("${hibernate.password}")
    private String hibernatePassword;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BotApiRepository botApiRepository() {
        return new BotApiService();
    }

    @Bean
    public UserListRepository userListRepository() {
        return new UserListService();
    }

    @Bean
    public WeatherApiRepository weatherApiRepository() {
        return new WeatherApiService();
    }

    @Bean
    public BotStatusHandler botStatusHandler() {
        return new BotStatusHandler();
    }

    @Bean
    public BotApiButtons botApiButtons() {
        return new BotApiButtons();
    }

    @Bean
    public ReplyMessagesToExistingUser replyMessagesToExistingUser() {
        return new ReplyMessagesToExistingUser();
    }

    @Bean
    public ReplyMessagesToRegistrationNewUser replyMessagesToRegistrationNewUser() {
        return new ReplyMessagesToRegistrationNewUser();
    }

    @Bean
    public WeatherMessages weatherMessages() {
        return new WeatherMessages();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public DataSource dataSource() {
        final ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl(hibernateJdbsUrl);
        dataSource.setUser(hibernateUser);
        dataSource.setPassword(hibernatePassword);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.admin.tbot.models");
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}