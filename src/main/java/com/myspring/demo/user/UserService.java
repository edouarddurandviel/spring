package com.myspring.demo.user;

import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myspring.demo.user.dto.UserResponse;
import com.myspring.demo.user.dto.UsersValidation;
import com.myspring.demo.user.utils.SecurityUtils;


@Service
public class UserService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponse getOneUser(Long id) {
        Users user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("your User not found")
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
        
        System.out.println(request);

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