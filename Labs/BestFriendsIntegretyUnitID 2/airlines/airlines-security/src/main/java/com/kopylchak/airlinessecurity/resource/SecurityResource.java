package com.kopylchak.airlinessecurity.resource;

import com.kopylchak.airlines.dto.AccessRefreshTokenResponse;
import com.kopylchak.airlines.dto.AccessTokenResponse;
import com.kopylchak.airlines.dto.SignInRequestDto;
import com.kopylchak.airlines.dto.SignUpRequestDto;
import com.kopylchak.airlinessecurity.service.SecurityService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityResource {
    private SecurityService securityService;

    public SecurityResource(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequestDto signUpDto) {
        securityService.signUp(signUpDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<AccessRefreshTokenResponse> signIn(@RequestBody SignInRequestDto signInDto) {
        return ResponseEntity.ok(securityService.signIn(signInDto));
    }

    @GetMapping("/accessToken")
    public ResponseEntity<AccessTokenResponse> getAccessToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(securityService.getAccessToken(refreshToken));
    }

    @GetMapping("/accessToken/validate")
    public ResponseEntity<Claims> validateAccessToken(@RequestParam String token) {
        return ResponseEntity.ok(securityService.validateAccessToken(token));
    }
}
