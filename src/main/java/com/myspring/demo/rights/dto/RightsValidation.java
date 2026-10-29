
package com.myspring.demo.rights.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RightsValidation(

        @NotNull(message = "Rights are required")
        @Valid
        RightsContent content
) {

    public record RightsContent(

            @NotNull(message = "Role is required")
            @Valid
            Role role,

            @NotNull(message = "Route is required")
            @Valid
            Routes routes
    ) {}

    public record Role(

            @NotNull(message = "id is required")
            Long id,

            @NotBlank(message = "name is required")
            String name
    ) {}

    public record Routes(

            @NotNull(message = "home is required")
            @Valid
            Permission home,

            @NotNull(message = "login is required")
            @Valid
            Permission login,

            @NotNull(message = "profil is required")
            @Valid
            Permission profil,

            @NotNull(message = "tanstack is required")
            @Valid
            Permission tanstack,

            @NotNull(message = "createUser is required")
            @Valid
            Permission createUser,

            @NotNull(message = "homeDetail is required")
            @Valid
            Permission homeDetail
    ) {}

    public record Permission(
            boolean edit,
            boolean read
    ) {}

    public RightsContent getRights() {
        return content;
    }
}
