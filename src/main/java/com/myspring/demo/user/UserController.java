package com.myspring.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.demo.user.dto.UserRequest;
import com.myspring.demo.user.dto.UserResponse;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public UserResponse addOneUser(@Valid @RequestBody UserRequest request) {
        UserResponse createdUser = userService.addOneUser(request);
        return createdUser;
    }

    @PatchMapping("/update/{id}")
    public UserResponse addOneUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse createdUser = userService.updateOneUser(id, request);
        return createdUser;
    }

    @PatchMapping("/delete/{id}")
    public UserResponse deleteOneUser(@PathVariable Long id){
        UserResponse deletedUser = userService.deleteOneUser(id);
        return deletedUser;
    }

    @PatchMapping("/delete/{id}/no-response")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneUserNoContent(@PathVariable Long id){
        userService.deleteOneUser(id);
    }
}