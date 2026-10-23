package com.myspring.demo.user;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myspring.demo.user.dto.UserResponse;
import com.myspring.demo.user.dto.UserValidation;


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
            user.getUserFirstName(),
            user.getUserLastName(),
            user.getUserEmail()
        );
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = repository.findAll()
        .stream()
        .map((user) -> new UserResponse( 
                    user.getUserId(),
                    user.getUserFirstName(),
                    user.getUserLastName(),
                    user.getUserEmail()
        )).toList();
        return users;
    }

    public UserResponse createAccount(UserValidation request) {

        if(repository.alreadyCreated(request.email())){
            throw new RuntimeException("User Allready exists");
        }
         
        User user = new User();
        user.setUserEmail(request.email());
        user.setUserFirstName(request.firstname());
        user.setUserLastName(request.lastname());
        user.setUserPassword(passwordEncoder.encode(request.password()));

        User createdUser = repository.save(user);

        return new UserResponse(
            createdUser.getUserId(),
            createdUser.getUserFirstName(),
            createdUser.getUserLastName(),
            createdUser.getUserEmail()
        );
    }


    public UserResponse login(UserValidation request) {

        User user = repository.getOneUserWithEmail(request.email()).orElseThrow(
            () -> new RuntimeException("User account does not exist")
        );

        if(!passwordEncoder.matches(request.password(), user.getUserPassword())){
            throw new RuntimeException("Wrong password");
        }

        return new UserResponse(
            user.getUserId(),
            user.getUserFirstName(),
            user.getUserLastName(),
            user.getUserEmail()
        );
    }


    public UserResponse addOneUser(UserValidation request) {
        
        System.out.println(request);

        User user = new User();
        user.setUserFirstName(request.firstname());
        user.setUserPassword(request.password());
        user.setUserLastName(request.lastname());
        user.setUserEmail(request.email());

        User createdUser = repository.save(user);

        return new UserResponse(
            createdUser.getUserId(),
            createdUser.getUserFirstName(),
            createdUser.getUserLastName(),
            createdUser.getUserEmail()
        );
    }

    public UserResponse updateOneUser(Long id, UserValidation request) {
        User user = repository.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        user.setUserFirstName(request.firstname());
        user.setUserPassword(request.password());
        user.setUserLastName(request.lastname());
        user.setUserEmail(request.email());

        User updatedUser = repository.save(user);

        return new UserResponse(
            updatedUser.getUserId(),
            updatedUser.getUserFirstName(),
            updatedUser.getUserLastName(),
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
            user.getUserFirstName(),
            user.getUserLastName(),
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