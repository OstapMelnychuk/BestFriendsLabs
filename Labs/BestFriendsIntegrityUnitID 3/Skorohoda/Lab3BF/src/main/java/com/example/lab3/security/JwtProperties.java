package com.example.lab3.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtProperties {
    @Value("${jwt.secret}")
    public static String SECRET = "BFIU3";
    @Value("${jwt.expirationTime}")
    public static int EXPIRATION_TIME = 86400000;
    @Value("${jwt.headerString}")
    public static String HEADER_STRING = "Authorization";
    @Value("${jwt.tokenPrefix}")
    public static String TOKEN_PREFIX = "Bearer ";
}
