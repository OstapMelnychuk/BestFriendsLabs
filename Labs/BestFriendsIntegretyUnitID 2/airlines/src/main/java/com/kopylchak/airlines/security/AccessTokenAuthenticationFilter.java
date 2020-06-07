package com.kopylchak.airlines.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;

    @Autowired
    public AccessTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    @SuppressWarnings("NullableProblems") HttpServletResponse httpServletResponse,
                                    @SuppressWarnings("NullableProblems") FilterChain filterChain)
        throws ServletException, IOException {
        String token = Optional.ofNullable(httpServletRequest.getHeader("Authorization"))
            .filter(t -> t.startsWith("Bearer "))
            .map(t -> t.substring(7))
            .orElse(null);

        if (token != null) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(token, null));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (MalformedJwtException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Malformed JWT token.");
            } catch (ExpiredJwtException e) {
                logger.info("Token is expired.");
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
