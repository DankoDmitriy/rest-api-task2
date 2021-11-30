package com.epam.esm.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:mysql.properties")
@EnableWebMvc
public class SpringConfig {
    private final ApplicationContext applicationContext;

    @Value("${mysql.driver}")
    private String mysqlDriver;

    @Value("${mysql.url}")
    private String mysqlUrl;

    @Value("${mysql.user}")
    private String mysqlUserName;

    @Value("${mysql.password}")
    private String mysqlPassword;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mysqlDriver);
        dataSource.setUrl(mysqlUrl);
        dataSource.setUsername(mysqlUserName);
        dataSource.setPassword(mysqlPassword);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
