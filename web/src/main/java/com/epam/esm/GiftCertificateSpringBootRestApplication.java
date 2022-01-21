package com.epam.esm;

import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GiftCertificateSpringBootRestApplication implements WebMvcConfigurer {

    private final static String LOCALIZATION_PATCH = "localization/error_messages";
    private final static String KEYCLOAK_ADMIN_URL = "http://localhost:8585/auth";
    private final static String KEYCLOAK_ADMIN_REALM_NAME = "master";
    private final static String KEYCLOAK_ADMIN_NAME = "admin";
    private final static String KEYCLOAK_ADMIN_PASSWORD = "admin";
    private final static String KEYCLOAK_ADMIN_CLIENT_ID = "admin-cli";

    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateSpringBootRestApplication.class);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(LOCALIZATION_PATCH);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    Keycloak initKeycloakWithAdminRole() {
        return Keycloak.getInstance(
                KEYCLOAK_ADMIN_URL,
                KEYCLOAK_ADMIN_REALM_NAME,
                KEYCLOAK_ADMIN_NAME,
                KEYCLOAK_ADMIN_PASSWORD,
                KEYCLOAK_ADMIN_CLIENT_ID);
    }
}
