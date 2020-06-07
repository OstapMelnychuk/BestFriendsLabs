package com.kopylchak.airlinessecurity.service;

import com.kopylchak.airlines.dto.AccessRefreshTokenResponse;
import com.kopylchak.airlines.dto.AccessTokenResponse;
import com.kopylchak.airlines.dto.SignInRequestDto;
import com.kopylchak.airlines.dto.SignUpRequestDto;
import com.kopylchak.airlinessecurity.dto.AccessTokenValidationDto;
import io.jsonwebtoken.Claims;

public interface SecurityService {
    void signUp(SignUpRequestDto signUpDto);

    AccessRefreshTokenResponse signIn(SignInRequestDto signInDto);

    AccessTokenResponse getAccessToken(String refreshToken);

    Claims validateAccessToken(String token);
}
