package com.example.springbootstarter.jwt;

import com.example.springbootstarter.cookie.CookieManager;
import com.example.springbootstarter.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter implements JwtExtractor {
    private final JwtGenerator jwtManager;
    private final AuthenticationService authenticationService;
    private final CookieManager cookieManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String token;
        final String email;

        token = extractJwt(request);
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            email = jwtManager.extractUsername(token);
        }
        catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT expired");
            cookieManager.removeCookies(response);
            return;
        }
        catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT");
            cookieManager.removeCookies(response);
            return;
        }

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
}
