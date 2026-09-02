package com.myspring.demo.user;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myspring.demo.user.dto.AccountValidation;
import com.myspring.demo.user.dto.UserRequestValidation;
import com.myspring.demo.user.dto.UserResponse;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public UserResponse getOneUser(Long id) {
        User user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("your User not found")
        );
        return new UserResponse(
            user.getUserId(),
            user.getUserFullName(),
            user.getUserEmail()
        );
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = repository.findAll()
        .stream()
        .map((user) -> new UserResponse( 
                    user.getUserId(),
                    user.getUserFullName(),
                    user.getUserEmail()
        )).toList();
        return users;
    }

    public UserResponse createAccount(AccountValidation request) {

        if(repository.alreadyCreated(request.email())){
            throw new RuntimeException("User Allready exists");
        }
         
        User user = new User();
        user.setUserEmail(request.email());
        user.setUserFullName(request.fullname());
        user.setUserPosition(request.position());
        user.setUserPassword(passwordEncoder.encode(request.password()));

        User createdUser = repository.save(user);

        return new UserResponse(
            createdUser.getUserId(),
            createdUser.getUserFullName(),
            createdUser.getUserEmail()
        );
    }


    public UserResponse login(AccountValidation request) {

        User user = repository.getOneUserWithEmail(request.email()).orElseThrow(
            () -> new RuntimeException("User account does not exist")
        );

        if(!passwordEncoder.matches(request.password(), user.getUserPassword())){
            throw new RuntimeException("Wrong password");
        }

        return new UserResponse(
            user.getUserId(),
            user.getUserFullName(),
            user.getUserEmail()
        );
    }


    public UserResponse addOneUser(UserRequestValidation request) {
        
        System.out.println(request);

        User user = new User();
        user.setUserFullName(request.fullname());
        user.setUserPassword(request.password());
        user.setUserPosition(request.position());
        user.setUserEmail(request.email());

        User createdUser = repository.save(user);

        return new UserResponse(
            createdUser.getUserId(),
            createdUser.getUserFullName(),
            createdUser.getUserEmail()
        );
    }

    public UserResponse updateOneUser(Long id, UserRequestValidation request) {
        User user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        user.setUserFullName(request.fullname());
        user.setUserPassword(request.password());
        user.setUserPosition(request.position());
        user.setUserEmail(request.email());

        User updatedUser = repository.save(user);

        return new UserResponse(
            updatedUser.getUserId(),
            updatedUser.getUserFullName(),
            updatedUser.getUserEmail()
        );
    }

    public UserResponse deleteOneUser(Long id) {
        User user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        repository.delete(user);

        return new UserResponse(
            user.getUserId(),
            user.getUserFullName(),
            user.getUserEmail()
        );
    }

    public void deleteOneUserNoContent(Long id) {
        User user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );
        repository.delete(user);
    }

}