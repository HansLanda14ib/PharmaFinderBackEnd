package com.localisation.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://pharmafinder-app-front-end.vercel.app/")); // Explicitly allow localhost:3000
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll() // allow all requests to the auth API
                .requestMatchers(HttpMethod.GET, "/api/v1/cities/**", "/api/v1/zones/**", "/api/v1/pharmacies/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/pharmaciesgarde/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/pharmaciesgarde/**").hasAnyAuthority("ADMIN","OWNER")
                .requestMatchers(HttpMethod.POST, "/api/v1/pharmaciesgarde/**").hasAnyAuthority("ADMIN","OWNER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/pharmacies/**").hasAnyAuthority("ADMIN","OWNER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/pharmacies/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/cities/**", "/api/v1/zones/**").hasAuthority("ADMIN") // allow only ADMIN to access create endpoints
                .requestMatchers(HttpMethod.PUT, "/api/v1/cities/**", "/api/v1/zones/**").hasAuthority("ADMIN") // allow only ADMIN to access update endpoints
                .requestMatchers(HttpMethod.DELETE, "/api/v1/cities/**", "/api/v1/zones/**").hasAuthority("ADMIN") // allow only ADMIN to access delete endpoints
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }


}