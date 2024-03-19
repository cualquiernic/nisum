package com.nisum.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nisum.jwt.JWTAuthorizationFilter;


@SpringBootApplication
@ComponentScan({"com.nisum.controllers","com.nisum.services", "com.nisum.components"})
@EntityScan("com.nisum.entities")
@EnableJpaRepositories("com.nisum.repositories")
public class NisumApplication {
	
	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources",
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/api-docs/**",
	        "/swagger-ui/**",
	        "/user/create",
	        "/jwt/**"
	};

	public static void main(String[] args) {
		SpringApplication.run(NisumApplication.class, args);
	}
	
	@Bean
    SecurityFilterChain webSecurityConfigSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
	
	

}
