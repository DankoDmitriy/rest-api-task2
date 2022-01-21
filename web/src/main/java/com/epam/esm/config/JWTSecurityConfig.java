package com.epam.esm.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import javax.ws.rs.HttpMethod;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .antMatchers("/api/**").permitAll()
//                        .antMatchers("/api/auth/**").permitAll()
//                        .antMatchers("/api/user").hasAuthority("SCOPE_user:read")
//                        .antMatchers("/api/ADMIN").hasAuthority("SCOPE_user:write")
                        .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt()) - работало
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> doSomething(){
        return new ApplicationListener<AuthenticationSuccessEvent>() {
            @Override
            public void onApplicationEvent(AuthenticationSuccessEvent event) {
                Authentication authentication = event.getAuthentication();
            }
        };
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setPrincipalClaimName("gift_id");
        return jwtAuthenticationConverter;
    }
}
