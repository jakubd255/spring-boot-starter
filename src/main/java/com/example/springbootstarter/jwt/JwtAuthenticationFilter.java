package com.example.springbootstarter.jwt;

import com.example.springbootstarter.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtGenerator jwtManager;
    private final AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String token;
        final String email;

        token = getAccessTokenFromHeader(request);
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        email = jwtManager.extractUsername(token);

        if(!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = authenticationService.userDetailsService().loadUserByUsername(email);

            if(jwtManager.isTokenValid(token, userDetails.getUsername())) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || authHeader.isEmpty() || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }
}
