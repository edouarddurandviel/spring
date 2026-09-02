package com.myspring.demo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(

        @NotBlank(message = "Name is required")
        String fullname,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "Company is required")
        Long companyId,

        @NotBlank(message = "password is required")
        String password,

        @NotBlank(message = "position is required")
        String position


) {
}

