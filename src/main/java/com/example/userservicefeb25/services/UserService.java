package com.example.userservicefeb25.services;

import com.example.userservicefeb25.exceptions.ExistingUserException;
import com.example.userservicefeb25.exceptions.UserNotRegisteredException;
import com.example.userservicefeb25.models.Role;
import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    Token login(String email, String password)  throws UserNotRegisteredException;

    User signUp(String name, String email, String password) throws ExistingUserException;

    User validateToken(String tokenValue) throws ExistingUserException;

    void logout(String tokenValue);

    User createRoles(String name, String email, List<Role> role) throws ExistingUserException, UserNotRegisteredException;
}
