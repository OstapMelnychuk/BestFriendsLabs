package com.kopylchak.airlines.service;

import com.kopylchak.airlines.entity.Employee;
import io.jsonwebtoken.Claims;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public interface JwtTokenUtils {
    String generateAccessToken(Employee employee, String refreshToken);

    String generateRefreshToken(Employee employee);

    Claims decodeJwt(String jwt);

    List<GrantedAuthority> extractAuthorities(Claims claims);
}
