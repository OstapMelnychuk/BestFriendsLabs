package com.kopylchak.notes.config;

import com.kopylchak.notes.repositories.UserRepository;
import com.kopylchak.notes.validation.UserIdValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public UserIdValidation userIdValidation(UserRepository userRepository) {
        return new UserIdValidation(userRepository);
    }
}
