package com.epam.finaltask.config;

import com.epam.finaltask.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Endpoints accessible by anyone
                        .requestMatchers("/auth/**",
                                "/users/register",
                                "/page/**")
                        .permitAll()

                        .requestMatchers("/vouchers/status/UNREGISTERED",
                                "/vouchers/order",
                                "/users/data")
                        .hasAuthority(Permission.USER_READ.name())

                        // Endpoints accessible by MANAGERs
                        .requestMatchers("/vouchers/list",
                                "/vouchers/changeStatus",
                                "/vouchers/changeHotStatus")
                        .hasAuthority(Permission.MANAGER_UPDATE.name())

                        // All other endpoints require ADMIN role
                        .anyRequest().hasAuthority(Permission.ADMIN_READ.name())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
