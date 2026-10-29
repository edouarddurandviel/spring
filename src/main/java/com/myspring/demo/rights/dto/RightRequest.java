package com.myspring.demo.rights.dto;


public record RightRequest(
        RightsContent content
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

        public RightsContent getRightsContent(){
                return content;
        }
}


      




