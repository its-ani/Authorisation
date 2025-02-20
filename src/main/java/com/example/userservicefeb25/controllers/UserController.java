package com.example.userservicefeb25.controllers;

import com.example.userservicefeb25.dtos.*;
import com.example.userservicefeb25.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // localhost:8080/users/
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        return null;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogOutRequestDto requestDto) {
        return null;
    }

    //localhost:8080/users/validate/token
    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") String tokenValue) {
        return null;
    }
}
