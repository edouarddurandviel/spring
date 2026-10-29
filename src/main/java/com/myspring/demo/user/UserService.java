package com.myspring.demo.user;

import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.demo.rights.Rights;
import com.myspring.demo.rights.dto.RightsResponse;
import com.myspring.demo.user.dto.UserResponse;
import com.myspring.demo.user.dto.UserWithRightsResponse;
import com.myspring.demo.user.dto.UsersValidation;
import com.myspring.demo.user.utils.SecurityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class UserService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public UserService(
        UsersRepository repository, 
        PasswordEncoder passwordEncoder, 
        ObjectMapper objectMapper
    ){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }


    @Transactional(readOnly = true)
    public UserWithRightsResponse getOneUser(Long id) {
        Users user = repository.findWithRightsById(id).orElseThrow(
            () -> new RuntimeException("your User not found")
        );

        Rights rights = user.getRights();

        RightsResponse perms = objectMapper.convertValue(
            rights.getRightsContent(),
            RightsResponse.class
        );
       
        return new UserWithRightsResponse(
            user.getUserId(),
            user.getUserEmail(),
            perms
        );
    }

    public UserResponse getOneUserWithEmail(String email) {
        Users user = repository.getOneUserWithEmail(email).orElseThrow(
            () -> new RuntimeException("Email could not held finding the user.")
        );
        return new UserResponse(
            user.getUserId(),
            user.getUserEmail()
        );
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = repository.findAll()
        .stream()
        .map((user) -> new UserResponse( 
                    user.getUserId(),
                    user.getUserEmail()
        )).toList();
        return users;
    }

    public UserResponse signup(UsersValidation request) {

        if(repository.alreadyCreated(request.email())){
            throw new RuntimeException("User Allready exists");
        }

        
        byte[] saltInbytes = SecurityUtils.randomBytes(16);
        String salt = HexFormat.of().formatHex(saltInbytes);

        Users user = new Users();
        user.setUserEmail(request.email());
        user.setUserSalt(salt);
        user.setUserPassword(passwordEncoder.encode(request.password()));

        Users createdUser = repository.save(user);

        return new UserResponse(
            createdUser.getUserId(),
            createdUser.getUserEmail()
        );
    }


    public UserDetails login(UsersValidation request) throws UsernameNotFoundException {

        Users user = repository.getOneUserWithEmail(request.email()).orElseThrow(
            () -> new RuntimeException("User account does not exist")
        );

        if(!passwordEncoder.matches(request.password(), user.getUserPassword())){
            throw new RuntimeException("Wrong password");
        }

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUserEmail())
            .password(user.getUserPassword())
            .roles("USER")
            .build();
          
    }

     public UserResponse logout(UsersValidation request) {

        Users user = repository.getOneUserWithEmail(request.email()).orElseThrow(
            () -> new RuntimeException("User account does not exist")
        );

        if(!passwordEncoder.matches(request.password(), user.getUserPassword())){
            throw new RuntimeException("Wrong password");
        }

        return new UserResponse(
            user.getUserId(),
            user.getUserEmail()
        );
    }


    public UserResponse addOneUser(UsersValidation request) {

        Users user = new Users();
       
        user.setUserPassword(request.password());
        user.setUserEmail(request.email());

        Users createdUser = repository.save(user);

        return new UserResponse(
            createdUser.getUserId(),
            createdUser.getUserEmail()
        );
    }

    public UserResponse updateOneUser(Long id, UsersValidation request) {
        Users user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        user.setUserPassword(request.password());
        user.setUserEmail(request.email());

        Users updatedUser = repository.save(user);

        return new UserResponse(
            updatedUser.getUserId(),
            updatedUser.getUserEmail()
        );
    }

    public UserResponse deleteOneUser(Long id) {
        Users user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        repository.delete(user);

        return new UserResponse(
            user.getUserId(),
            user.getUserEmail()
        );
    }

    public void deleteOneUserNoContent(Long id) {
        Users user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );
        repository.delete(user);
    }

}