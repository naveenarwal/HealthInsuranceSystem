package com.rihis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rihis.filter.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint entryPoint;
	
	@Autowired
	private SecurityFilter filter;
	
	String apiPaths[] = {
			"/api/v1/rihis/caseWorker/login/**","/api/v1/rihis/caseWorker/generatePassword/**","/api/v1/rihis/caseWorker/currentUser/**",
			"/api/v1/rihis/benefitIssuance/**","/api/v1/rihis/caseWorker","/api/v1/rihis/citizen/","/api/v1/rihis/correspondence/",
			"/api/v1/rihis/Pdf/**","/api/v1/rihis/reports/**","/api/v1/rihis/**"
	};
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public SecurityFilterChain configurePath(HttpSecurity http) throws Exception {
		return http.csrf().disable()
			.authorizeRequests().antMatchers(apiPaths).permitAll()
//			.antMatchers("/user/admin").hasAuthority("ADMIN")
//			.antMatchers("/user/customer").hasAuthority("CUSTOMER")
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(entryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();
	}
}
