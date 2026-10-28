package com.myspring.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.demo.user.dto.UsersValidation;
import com.myspring.demo.service.UserSessionService;
import com.myspring.demo.token.TokenService;
import com.myspring.demo.token.dto.Token;
import com.myspring.demo.token.dto.TokenResponse;
import com.myspring.demo.user.dto.UserResponse;

import org.springframework.security.authentication.AuthenticationManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    //private final UserSessionService userSessionService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

     public UserController(
            UserService userService,
            TokenService tokenService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            UserSessionService userSessionService
        ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        //this.userSessionService = userSessionService;
    }


    // without @ResponseStatus 201
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UsersValidation request) {
        UserResponse user = userService.signup(request);
    
        return ResponseEntity
            .status(HttpStatus.CREATED)     
            .body(user);
    }

    // login VS logout (in SecurityConfiguration)
    @PostMapping("/login")
    public ResponseEntity<String> login(
        @Valid @RequestBody UsersValidation request,
        HttpServletRequest httpRequest,
        HttpServletResponse httpResponse
    ) {
        try{
            Authentication authentication =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                        request.email(),
                        request.password()
                    )
                );

            SecurityContext context = SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            securityContextRepository.saveContext(
                context, httpRequest, httpResponse
            );

            // inactivité 30 minutes
            //httpRequest.getSession().setMaxInactiveInterval(30 * 60);
            // should not use PRINCIPAL_NAME records
            // Map<String, ? extends Session> connected = userSessionService.findSessions();

    

            // TokenResponse token = tokenService.saveOneToken(
            //     new Token(
            //         httpRequest.getSession().getId(), 
            //         30)
            //     );


            return ResponseEntity.status(HttpStatus.OK)
                .body("You are logged in !");



        }catch(BadCredentialsException e){

            SecurityContextHolder.clearContext();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Wrong email or password");

        }
    }

     @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable Long id) {
        UserResponse user = userService.getOneUser(id);
   
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    // @ResponseStatus 201
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addOneUser(@Valid @RequestBody UsersValidation request) {
        UserResponse user = userService.addOneUser(request);
        return user;
    }


    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse addOneUser(@PathVariable Long id, @Valid @RequestBody UsersValidation request) {
        UserResponse user = userService.updateOneUser(id, request);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResponse deleteOneUser(@PathVariable Long id){
        UserResponse user = userService.deleteOneUser(id);
        return user;
    }

    @DeleteMapping("/delete/{id}/no-response")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneUserNoContent(@PathVariable Long id){
        userService.deleteOneUser(id);
    }
}