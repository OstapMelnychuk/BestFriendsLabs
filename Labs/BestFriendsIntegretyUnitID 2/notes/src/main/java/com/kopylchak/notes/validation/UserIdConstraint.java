package com.kopylchak.notes.validation;

import com.kopylchak.notes.constants.ValidationErrorMessages;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UserIdValidation.class)
@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdConstraint {
    String message() default ValidationErrorMessages.USER_DOESNT_EXIST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}