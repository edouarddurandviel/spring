package com.myspring.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.demo.user.dto.UserValidation;
import com.myspring.demo.user.dto.UserResponse;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired 
    private UserService userService;


    @GetMapping("/{id}")
    public UserResponse getOneUser(@PathVariable Long id) {
        UserResponse user = userService.getOneUser(id);
        return user;
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return users;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addOneUser(@Valid @RequestBody UserValidation request) {
        UserResponse user = userService.addOneUser(request);
        return user;
    }

    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createAccount(@Valid @RequestBody UserValidation request) {
        UserResponse user = userService.createAccount(request);
        return user;
    }

    @PatchMapping("/update/{id}")
    public UserResponse addOneUser(@PathVariable Long id, @Valid @RequestBody UserValidation request) {
        UserResponse user = userService.updateOneUser(id, request);
        return user;
    }

    @DeleteMapping("/delete/{id}")
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