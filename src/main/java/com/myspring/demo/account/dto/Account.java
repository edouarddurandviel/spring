package com.myspring.demo.account.dto;


public record Account(
        String firstname,
        String lastname,
        String email,
        String password
) {}

