package com.example.userservicefeb25.controllers;

import com.example.userservicefeb25.dtos.*;
import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;
import com.example.userservicefeb25.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // localhost:8080/users/
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {

        Token token = userService.login(requestDto.getEmail(), requestDto.getPassword());
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token);

        return responseDto;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {

        User user = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());

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

        User user = userService.validateToken(tokenValue);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
