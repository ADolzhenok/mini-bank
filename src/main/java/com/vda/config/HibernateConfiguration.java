package com.vda.config;

import com.vda.account.Account;
import com.vda.user.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory(){
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        config
                .addAnnotatedClasses(User.class, Account.class)
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "root")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true");

        return config.buildSessionFactory();

    }

}
