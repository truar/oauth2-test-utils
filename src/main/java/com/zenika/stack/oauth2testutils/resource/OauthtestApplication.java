package com.zenika.stack.oauth2testutils.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OauthtestApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "resource-server");
        SpringApplication.run(OauthtestApplication.class, args);
    }

    @Bean
    public ResourceServerConfigurer resourceServerConfigurer() {
        return new ResourceServerConfigurer() {
            @Override
            public void configure(ResourceServerSecurityConfigurer cfg) {
                cfg.resourceId("accounts");
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests() // Specify URL restrictions
                        .mvcMatchers(HttpMethod.GET, "/whoami/**")
                        .access("#oauth2.hasScope('resource.read')");
            }
        };
    }
}
