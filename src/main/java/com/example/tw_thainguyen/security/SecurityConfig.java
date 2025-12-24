package com.example.tw_thainguyen.security;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.tw_thainguyen.security.jwt.JwtAuthTokenFilter;
import com.example.tw_thainguyen.security.jwt.JwtEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final JwtEntryPoint jwtEntryPoint;

    public SecurityConfig(UserDetailsService userDetailsService,
                         JwtAuthTokenFilter jwtAuthTokenFilter,
                         JwtEntryPoint jwtEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthTokenFilter = jwtAuthTokenFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of(
                            "http://localhost:3000",  // ✅ Dev local
                            "http://localhost:5173",  // ✅ Dev local Vite
                            "*"
                    ));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Cho phép GET requests không cần xác thực để user có thể xem
                        .requestMatchers(HttpMethod.GET, "/api/v1/golden-book", "/api/v1/golden-book/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/artifacts", "/api/v1/artifacts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/histories", "/api/v1/histories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/introductions", "/api/v1/introductions/**").permitAll()
                        // Users endpoints vẫn cần xác thực
                        .requestMatchers("/api/v1/users/**").authenticated()
                        // Tất cả các request khác (POST, PUT, DELETE) vẫn cần xác thực
                        .anyRequest().authenticated()
                )
                .sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint(jwtEntryPoint))
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
