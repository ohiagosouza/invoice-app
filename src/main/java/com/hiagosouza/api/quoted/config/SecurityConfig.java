package com.hiagosouza.api.quoted.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.info("Initializing BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST ,"/api/v1/auth/user/login", "/api/v1/user/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                ).formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
