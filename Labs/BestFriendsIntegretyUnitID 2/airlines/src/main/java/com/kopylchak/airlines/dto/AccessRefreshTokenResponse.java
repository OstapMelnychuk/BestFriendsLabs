package com.kopylchak.airlines.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessRefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
