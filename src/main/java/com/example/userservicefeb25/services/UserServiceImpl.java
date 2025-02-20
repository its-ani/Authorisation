package com.example.userservicefeb25.services;

import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public User signUp(String name, String email, String password) {
        //BCryptPassword

        return null;
    }

    @Override
    public User validateToken(String tokenValue) {
        return null;
    }

    @Override
    public void logout(String tokenValue) {

    }
}
