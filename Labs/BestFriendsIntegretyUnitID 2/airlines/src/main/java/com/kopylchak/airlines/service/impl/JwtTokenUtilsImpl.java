package com.kopylchak.airlines.service.impl;

import com.kopylchak.airlines.entity.Employee;
import com.kopylchak.airlines.entity.Role;
import com.kopylchak.airlines.service.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtTokenUtilsImpl implements JwtTokenUtils {
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    @Value("${secret-key}")
    private String secretKey;
    @Value("${access-token-expiration-time}")
    private Long accessTokenExpirationTime;
    @Value("${refresh-token-expiration-time}")
    private Long refreshTokenExpirationTime;

    @Override
    public String generateAccessToken(Employee employee, String refreshToken) {
        try {
            decodeJwt(refreshToken);
        } catch (SignatureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can't generate access token as refresh " +
                "token is invalid");
        }

        return generateJwtToken(employee, accessTokenExpirationTime);
    }

    @Override
    public String generateRefreshToken(Employee employee) {
        return generateJwtToken(employee, refreshTokenExpirationTime);
    }

    @Override
    public Claims decodeJwt(String jwt) {
        return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(jwt).getBody();
    }

    @Override
    public List<GrantedAuthority> extractAuthorities(Claims claims) {
        return Stream.of(((String) claims.get("roles")).split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    private String generateJwtToken(Employee employee, long expirationTime) {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", buildRoles(employee.getRoles()));
        claims.put("email", employee.getPersonalInfo().getEmail());

        return Jwts.builder()
            .setId(employee.getId().toString())
            .setIssuedAt(new Date())
            .setClaims(claims)
            .signWith(signatureAlgorithm, signingKey)
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
            .compact();
    }

    private String buildRoles(List<Role> roles) {
        return roles.stream()
            .map(Role::getName)
            .collect(Collectors.joining(","));
    }
}
