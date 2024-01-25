package com.school.SBA.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetails;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpsecu) throws Exception {
	 return httpsecu.csrf(csrf->csrf.disable())
			 .authorizeHttpRequests(auth->auth.requestMatchers("/users/register").permitAll()
//					 .requestMatchers("/users/{userId}/schools").hasRole("ADMIN")
					 .anyRequest()
					 .authenticated())
			 .formLogin(Customizer.withDefaults())
			 .build();
		
	}
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetails);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	

}