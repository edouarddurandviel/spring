package com.myspring.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // is injected throught @Autowire
    /////////////////////////////////////
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder argonPasswordEncoder() {
        // default built in configuration
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        // return new Argon2PasswordEncoder(
        //     16,       // salt: 16 bytes
        //     32,       // hash: 32 bytes
        //     1,        // parallelism
        //     19456,    // memory: 19 MiB
        //     2         // iterations
        // );
    }
    /////////////////////////////////////

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        SecurityContextRepository repository
    ) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .securityContext(context ->
                context.securityContextRepository(repository)
            )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                HttpMethod.POST,
                "/api/user/signup",
                "/api/user/login",
                "/api/user/lostPassword"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.IF_REQUIRED
            )
        ).logout(logout -> logout
            .logoutUrl("/api/user/logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler((request, response, authentication) ->
                response.setStatus(200)
            )
        );

    return http.build();
    }
}