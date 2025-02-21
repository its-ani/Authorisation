package com.example.userservicefeb25.controllers;

import com.example.userservicefeb25.dtos.*;
import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;
import com.example.userservicefeb25.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // localhost:8080/users/
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = new LoginResponseDto();
        try {
            Token token = userService.login(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setToken(token);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return responseDto;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        UserDto userDto = new UserDto();

        try {
            User user = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
            userDto.setRoles(user.getRoles());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userDto;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogOutRequestDto requestDto) {
        userService.logout(requestDto.getTokenValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //localhost:8080/users/validate/token
    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") String tokenValue) {
        UserDto userDto = new UserDto();

        try{
            User user = userService.validateToken(tokenValue);
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
            userDto.setRoles(user.getRoles());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userDto;
    }
}
