package com.epam.finaltask.config;

import com.epam.finaltask.service.JwtService;
import com.epam.finaltask.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JWT Authentication Filter triggered for request: {}", request.getServletPath());

        if (request.getServletPath().contains("/auth") || request.getServletPath().contains("/register")) {
            log.info("Skipping authentication for public path: {}", request.getServletPath());
            proceedWithoutAuthentication(request, response, filterChain);
            return;
        }

        String username = extractAndValidateToken(request);
        if (username != null) {
            log.info("Valid JWT found. Authenticating user: {}", username);
            HttpServletRequest wrappedRequest = new CustomHeaderRequestWrapper(request, username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            log.info("Authentication successful for user: {}", username);
            filterChain.doFilter(wrappedRequest, response);
            return;
        }

        log.warn("No valid JWT found. Proceeding without authentication.");
        proceedWithoutAuthentication(request, response, filterChain);

    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    private String extractAndValidateToken(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (StringUtils.hasText(token) && jwtService.validateJWT(token)) {
            return jwtService.getUsernameFromJWT(token);
        }
        return null;
    }
    private void proceedWithoutAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}
