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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // is injected throught @Autowire
    /////////////////////////////////////
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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