package com.zenika.stack.oauth2testutils.auth;

import com.zenika.stack.oauth2testutils.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@SpringBootApplication
@EnableAuthorizationServer
public class AuthorizationServer {

	public static final String CLIENT_CREDENTIALS = "client_credentials";
	public static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

	public static final String RESOURCE_READ = "resource.read";
	public static final String RESOURCE_WRITE = "resource.write";


	public static void main(String[] args) {
		System.setProperty("spring.config.name", "auth-server");
		SpringApplication.run(AuthorizationServer.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	AuthorizationServerConfigurer authServerConfig() {
		return new AuthorizationServerConfigurerAdapter() {
			/**
			 * Valid clients must be a "trusted client".
			 */
			@Override
			public void configure(AuthorizationServerSecurityConfigurer security) {
				security.checkTokenAccess("hasAuthority('" + ROLE_TRUSTED_CLIENT + "')");
			}

			/**
			 * Configure access credentials.
			 * <ul>
			 * <li>To access the Resource Server you must submit account-server:secret as
			 * identification and have a token allowing you access as a trusted-client.
			 * <li>To get an authorization token you must submit account-tester:secret as
			 * identification and you will be granted client credentials and allowed access
			 * to anything allowing access to the "account.read" authority.
			 * </ul>
			 */
			@Override
			public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
				// In-memory definitions
				clients.inMemory() //
						.withClient(Constants.ACCOUNT_SERVER) // Resource Server
						.secret("secret") //
						.authorizedGrantTypes(CLIENT_CREDENTIALS) //
						.authorities(ROLE_TRUSTED_CLIENT) //
						.and() //
						.withClient(Constants.ACCOUNT_TESTER_CLIENT) // Client
						.secret("secret")//
						.authorizedGrantTypes(CLIENT_CREDENTIALS) //
						.scopes(RESOURCE_READ, RESOURCE_WRITE);
			}

		};
	}
}
