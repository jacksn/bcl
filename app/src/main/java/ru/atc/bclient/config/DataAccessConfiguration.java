package ru.atc.bclient.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySources({
        @PropertySource("classpath:db/db.properties"),
        @PropertySource(value = "classpath:db/${db_config_file}", ignoreResourceNotFound = true)})
@EnableTransactionManagement
@EntityScan("ru.atc.bclient.model")
@EnableJpaRepositories(basePackages = "ru.atc.bclient.repository")
public class DataAccessConfiguration {
}
