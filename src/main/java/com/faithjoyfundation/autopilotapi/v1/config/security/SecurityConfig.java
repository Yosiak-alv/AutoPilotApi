package com.faithjoyfundation.autopilotapi.v1.config.security;

import com.faithjoyfundation.autopilotapi.v1.config.security.jwt.CustomUserDetailsService;
import com.faithjoyfundation.autopilotapi.v1.config.security.jwt.JwtAuthenticationEntryPoint;
import com.faithjoyfundation.autopilotapi.v1.config.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/departments",
            "/api/v1/repair-statuses",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/bus/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers(WHITE_LIST_URL).permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/repairs/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/workshops/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/cars/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/branches/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/models/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/brands/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                });

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
