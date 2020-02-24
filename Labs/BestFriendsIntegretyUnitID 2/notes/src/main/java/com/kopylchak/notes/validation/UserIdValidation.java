package com.kopylchak.notes.validation;

import com.kopylchak.notes.repositories.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserIdValidation implements ConstraintValidator<UserIdConstraint, Long> {
    private UserRepository userRepository;

    @Autowired
    public UserIdValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return userRepository.existsById(value);
    }
}