package com.myspring.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean 
    public UserDetailsService userDetailsService(
    PasswordEncoder passwordEncoder) {

    UserDetails user = User.builder()
            .username("test")
            .password(passwordEncoder.encode("change-me"))
            .roles("USER")
            .build();

    return new InMemoryUserDetailsManager(user);
}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                HttpMethod.POST,
                "/api/user/create-account",
                "/api/auth/login"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.IF_REQUIRED
            )
        );

    return http.build();
    }



    // @Bean
    // public CompromisedPasswordChecker compromisedPasswordChecker() {
    //     return new HaveIBeenPwnedRestApiPasswordChecker();
    // }

    // static class CompromisedPasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {

    //     private final SimpleUrlAuthenticationFailureHandler defaultFailureHandler = new SimpleUrlAuthenticationFailureHandler(
    //             "/api/auth/login-error");

    //     private final RedirectStrategy redirect = new DefaultRedirectStrategy();

    //     @Override
    //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
    //             AuthenticationException exception) throws IOException, ServletException {
    //         if (exception instanceof CompromisedPasswordException) {
    //             this.redirect.sendRedirect(request, response, "/reset-password");
    //             return;
    //         }
    //         this.defaultFailureHandler.onAuthenticationFailure(request, response, exception);
    //     }

    // }
}