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
        log.info("JWT Authentication Filter triggered");
        if(request.getServletPath().contains("/auth") || request.getServletPath().contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtFromRequest(request);

        if (StringUtils.hasText(token) && jwtService.validateJWT(token)) {
            String username = jwtService.getUsernameFromJWT(token);

            // Створюємо обгортку з кастомним заголовком
            HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
                private final Map<String, String> headers = new HashMap<>();

                {
                    headers.put("X-User-Name", username); // Додаємо заголовок
                }

                @Override
                public String getHeader(String name) {
                    return headers.containsKey(name)
                            ? headers.get(name)
                            : super.getHeader(name);
                }

                @Override
                public Enumeration<String> getHeaders(String name) {
                    return headers.containsKey(name)
                            ? Collections.enumeration(Collections.singletonList(headers.get(name)))
                            : super.getHeaders(name);
                }

                @Override
                public Enumeration<String> getHeaderNames() {
                    Set<String> names = new LinkedHashSet<>(headers.keySet());
                    Collections.list(super.getHeaderNames()).forEach(names::add);
                    return Collections.enumeration(names);
                }
            };

            // Додаємо аутентифікацію
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Продовжуємо ланцюжок з обгорнутим запитом
            filterChain.doFilter(wrappedRequest, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
