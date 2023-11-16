package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class securityConfig {

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private String baseUrl = "http://localhost:8080/" ;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((authorize) -> {authorize
			.requestMatchers(HttpMethod.GET, baseUrl + "userAPI/greeting").permitAll()
			.requestMatchers(HttpMethod.GET, baseUrl + "userAPI/getBooks/**").hasAuthority("ROLE_USER") // Protect the endpoint
			.requestMatchers(HttpMethod.POST, baseUrl + "userAPI/addBook").hasAuthority("ROLE_USER") // Protect the endpoint
			.requestMatchers(HttpMethod.PUT, baseUrl + "userAPI/updateBooks/**").hasAuthority("ROLE_USER") // Protect the endpoint
			.requestMatchers(HttpMethod.DELETE, baseUrl + "userAPI/deleteBooks/**").hasAuthority("ROLE_ADMIN") // Protect the endpoint	
			.anyRequest().authenticated();
		})
		.csrf(csrf->csrf.disable())
		.httpBasic(Customizer.withDefaults());
		return http.build();
		
				
	}

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();

		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("USER","ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}

}
