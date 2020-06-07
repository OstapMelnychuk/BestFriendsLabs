package com.kopylchak.airlines.security;

import com.kopylchak.airlines.service.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    public JwtAuthenticationProvider(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims = jwtTokenUtils.decodeJwt(authentication.getName());

        String email = (String) claims.get("email");
        List<GrantedAuthority> authorities = jwtTokenUtils.extractAuthorities(claims);

        return new UsernamePasswordAuthenticationToken(email, "", authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
