package com.springboot.milezero.config;

import com.springboot.milezero.service.MyUserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserSecurityService myUserSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/api/vehicle/get-all").permitAll()
                        .requestMatchers("/api/employee/add").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // no Bean def should be added
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(myUserSecurityService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
