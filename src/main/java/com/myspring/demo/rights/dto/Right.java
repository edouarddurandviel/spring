package com.myspring.demo.rights.dto;


public record Right(
        RightsContent content,
        Long User_Id
) {

        public record RightsContent(
        Role role,
        Routes routes
        ) {}

        public record Role(
                Long id,
                String name
        ) {}

        public record Routes(
                Permission home,
                Permission login,
                Permission profil,
                Permission tanstack,
                Permission createUser,
                Permission homeDetail
        ) {}

        public record Permission(
                boolean edit,
                boolean read
        ) {}
}


      




