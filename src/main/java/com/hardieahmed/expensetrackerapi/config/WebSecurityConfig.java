package com.hardieahmed.expensetrackerapi.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hardieahmed.expensetrackerapi.security.CustomUserDetailsService;
import com.hardieahmed.expensetrackerapi.security.JwtRequestFilter;

@Configuration
public class WebSecurityConfig {
	
	AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
        authenticationManager = authenticationManagerBuilder.build();
		
        http
        	.csrf()
        	.disable()
        	.authorizeHttpRequests().antMatchers("/login", "/register", "/swagger-ui.html").permitAll()
        	.anyRequest().authenticated()
        	.and()
        	.authenticationManager(authenticationManager)
        	.sessionManagement().sessionCreationPolicy(STATELESS);
        
        http
        	.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http
        	.httpBasic();
        
        return http.build();
	}
	
	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter() {
		return new JwtRequestFilter();
		
	}
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	
	
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsManager() {
//		UserDetails hardie = User.withDefaultPasswordEncoder()
//				.username("hardie")
//				.password("123456")
//				.authorities("admin")
//				.build();
//		
//		UserDetails shakha = User.withDefaultPasswordEncoder()
//				.username("shakha").password("123456").authorities("user")
//				.build();
//		return new InMemoryUserDetailsManager(hardie, shakha);
//	}
}
