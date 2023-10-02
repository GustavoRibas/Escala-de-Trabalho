package com.example.escalaCidada.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.escalaCidada.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

		@Autowired
		private UsuarioService usuarioService;
		
		@Bean
	    static PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
		
		@Bean
		AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
			return auth.getAuthenticationManager();
		}
		
		@Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        return http.csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests((authorize) -> authorize
	                		//recursos
	                		.requestMatchers("/css/**").permitAll()
	                		.requestMatchers("/img/**").permitAll()
	                		.requestMatchers("/midia/**").permitAll()
	                		
	                		.requestMatchers(HttpMethod.GET,"/login/**").permitAll()
	                		
	                		
	                        .requestMatchers("/dashboard", "/workscheduleList/**").hasAnyAuthority("Administrador","Usuario") // Acesso para "user" e "admin"
	                        .requestMatchers("/new-employee", "/new-shift", "/new-workschedule", "/edit-workschedule").hasAuthority("Administrador") // Apenas "admin"
	                		.anyRequest().authenticated())
	                .formLogin(form -> form
	                        .loginPage("/login")
	                        .loginProcessingUrl("/login")
	                        .defaultSuccessUrl("/dashboard")
	                        .permitAll())
	                .logout(logout -> logout
	                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                        .permitAll())
	                .build();
		}
		
		@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
	    }
}
