package com.springboot.milezero.config;

import com.springboot.milezero.enums.Role;
import com.springboot.milezero.service.MyUserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final MyUserSecurityService myUserSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize

                        // Auth API
                        .requestMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
                        .requestMatchers("/api/auth/login").authenticated() // Should authenticate user at login

                        .requestMatchers("/api/vehicle/get-all").permitAll()
                        .requestMatchers("/api/employee/add").permitAll()

                        // Customer API
                        .requestMatchers("/api/booking/add/me").hasAuthority(Role.CUSTOMER.name())
                        .requestMatchers("/api/booking/get-by-customer/me").hasAuthority(Role.CUSTOMER.name())
                        .requestMatchers("/api/customer/get/me").hasAuthority(Role.CUSTOMER.name())
                        .requestMatchers("/api/booking/cancel/{id}").hasAuthority(Role.CUSTOMER.name())
                        .requestMatchers("/api/customer/update/me").hasAuthority(Role.CUSTOMER.name())

                        //Employee API
                        .requestMatchers("/api/booking/get-by-branch/me").hasAnyAuthority(Role.EMPLOYEE.name(), Role.MANAGER.name())
                        .requestMatchers("/api/booking/change-status/no-show/{id}").hasAnyAuthority(Role.EMPLOYEE.name(), Role.MANAGER.name())
                        .anyRequest().permitAll()

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic authentication with default settings (username and password)
        ;
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
