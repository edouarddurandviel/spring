package com.myspring.demo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestValidation(

        @NotNull(message = "Company is required")
        Long companyId,

        @NotBlank(message = "Fullname is required")
        String fullname,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Position is required")
        String position
) {}

