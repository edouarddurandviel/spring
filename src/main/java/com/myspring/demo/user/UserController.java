package com.myspring.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.myspring.demo.user.dto.UserResponse;
import com.myspring.demo.user.dto.UserWithRightsResponse;

import org.springframework.security.authentication.AuthenticationManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

     public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository
        ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
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

            return ResponseEntity.status(HttpStatus.OK)
                .body("You are logged in !");



        }catch(BadCredentialsException e){

            SecurityContextHolder.clearContext();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Wrong email or password");

        }
    }

    @GetMapping("/principal")
    public ResponseEntity<UserResponse> principal(@AuthenticationPrincipal UserDetails principal) {
        UserResponse pricipal = userService.getOneUserWithEmail(principal.getUsername());
        return ResponseEntity
            .status(HttpStatus.OK)     
            .body(pricipal);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserWithRightsResponse> getOneUser(@PathVariable Long id) {

        UserWithRightsResponse user = userService.getOneUser(id);

        System.out.println(user);
        
   
        return ResponseEntity
            .status(HttpStatus.OK)     
            .body(user);
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