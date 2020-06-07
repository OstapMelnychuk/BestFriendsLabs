package com.kopylchak.airlines.service;

import com.kopylchak.airlines.dto.AccessRefreshTokenResponse;
import com.kopylchak.airlines.dto.AccessTokenResponse;
import com.kopylchak.airlines.dto.SignInRequestDto;
import com.kopylchak.airlines.dto.SignUpRequestDto;

public interface SecurityService {
    void signUp(SignUpRequestDto signUpDto);

    AccessRefreshTokenResponse signIn(SignInRequestDto signInDto);

    AccessTokenResponse getAccessToken(String refreshToken);
}
