package com.myspring.demo.token.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TokensCreateValidation(

        @NotBlank(message = "Token is required")
        String token,

        @NotBlank(message = "User ID is required")
        @Size(min = 8)
        Long User_Id
) {}

