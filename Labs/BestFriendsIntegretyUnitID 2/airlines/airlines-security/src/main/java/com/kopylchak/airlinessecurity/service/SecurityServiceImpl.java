package com.kopylchak.airlinessecurity.service;

import com.kopylchak.airlines.dto.AccessRefreshTokenResponse;
import com.kopylchak.airlines.dto.AccessTokenResponse;
import com.kopylchak.airlines.dto.SignInRequestDto;
import com.kopylchak.airlines.dto.SignUpRequestDto;
import com.kopylchak.airlines.entity.Employee;
import com.kopylchak.airlines.repository.EmployeeRepository;
import com.kopylchak.airlines.service.JwtTokenUtils;
import com.kopylchak.airlinessecurity.dto.AccessTokenValidationDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import javax.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityServiceImpl implements SecurityService {
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;
    private JwtTokenUtils jwtTokenUtils;

    public SecurityServiceImpl(PasswordEncoder passwordEncoder,
                               ModelMapper modelMapper,
                               EmployeeRepository employeeRepository,
                               JwtTokenUtils jwtTokenUtils) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }


    @Override
    public void signUp(SignUpRequestDto signUpDto) {
        Employee employee = modelMapper.map(signUpDto, Employee.class);

        employee.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        try {
            employeeRepository.save(employee);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data. Check your data and try again");
        }
    }

    @Override
    public AccessRefreshTokenResponse signIn(SignInRequestDto signInDto) {
        Employee employee = employeeRepository.findByPersonalInfoEmail(signInDto.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password or email is incorrect."));

        if (!passwordEncoder.matches(signInDto.getPassword(), employee.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password or email is incorrect.");
        }

        String refreshToken = jwtTokenUtils.generateRefreshToken(employee);
        String accessToken = jwtTokenUtils.generateAccessToken(employee, refreshToken);

        employee.setRefreshToken(refreshToken);

        employeeRepository.save(employee);

        return new AccessRefreshTokenResponse(accessToken, refreshToken);
    }

    @Override
    public AccessTokenResponse getAccessToken(String refreshToken) {
        try {
            Claims claims = jwtTokenUtils.decodeJwt(refreshToken);
            String email = (String) claims.get("email");

            if (!employeeRepository.existsByRefreshTokenAndPersonalInfoEmail(refreshToken, email)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is invalid.");
            }

            Employee employee = employeeRepository.findByPersonalInfoEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is invalid."));

            return new AccessTokenResponse(jwtTokenUtils.generateAccessToken(employee, refreshToken));
        } catch (MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can't generate access token as refresh token " +
                "is invalid or expired.");
        }
    }

    @Override
    public Claims validateAccessToken(String token) {
        try {
            return jwtTokenUtils.decodeJwt(token);
        } catch (MalformedJwtException e) {
            return null;
        }
    }
}
