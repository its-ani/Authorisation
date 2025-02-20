package com.example.userservicefeb25.services;

import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;

public interface UserService {
    Token login(String email, String password);

    User signUp(String name, String email, String password);

    User validateToken(String tokenValue);

    void logout(String tokenValue);
}
