package com.myspring.demo.user.dto;


public record Account(
        String firstname,
        String lastname,
        String email,
        String password
) {}

