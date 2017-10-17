package ru.atc.bclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:db/db.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.atc.bclient.repository")
public class DataAccessConfiguration {
}
