package com.zenika.stack.oauth2testutils.resource.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@Order(1)
public class AuthenticationManagerProvider {

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
}