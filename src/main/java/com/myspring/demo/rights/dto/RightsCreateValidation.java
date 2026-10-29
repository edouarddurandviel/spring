package com.myspring.demo.rights.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;

public record RightsCreateValidation(

        @NotBlank(message = "Content is required")
        JsonNode content,

        @NotBlank(message = "User ID is required")
        int User_Id
) {}

