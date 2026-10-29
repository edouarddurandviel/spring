package com.myspring.demo.rights.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;

public record RightsValidation(

        @NotBlank(message = "Token is required")
        JsonNode token,

        @NotBlank(message = "User ID is required")
        int User_Id
) {}

