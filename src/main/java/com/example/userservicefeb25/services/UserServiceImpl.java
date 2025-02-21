package com.example.userservicefeb25.services;

import com.example.userservicefeb25.exceptions.ExistingUserException;
import com.example.userservicefeb25.exceptions.UserNotRegisteredException;
import com.example.userservicefeb25.models.Role;
import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;
import com.example.userservicefeb25.repository.TokenRepository;
import com.example.userservicefeb25.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }
    @Override
    public Token login(String email, String password) throws UserNotRegisteredException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UserNotRegisteredException("User with this email is not registered");
        }

        if(!user.get().getPassword().equals(password)) {
            throw new UserNotRegisteredException("Wrong password");
        }

        Optional<Token> optionalToken = tokenRepository.findByUser(user.get());
        Token token = null;
        if(optionalToken.isEmpty()) {
            createToken(user.get());
            Optional<Token> optionalTokens = tokenRepository.findByUser(user.get());
            token = optionalTokens.get();
        }
        else{
            token = optionalToken.get();
        }

        return token;
    }

    public ArrayList<Role> creatingRoles(String role) {
        Role roless = new Role();
        roless.setValue(role);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roless);

        return roles;
    }

    public String generateUUIDToken() {
        return UUID.randomUUID().toString();
    }

    public void createToken(User newUser) {
        Token token = new Token();
        token.setUser(newUser);
        token.setValue(generateUUIDToken());
        LocalDateTime expiryDateTime = LocalDateTime.now().plusDays(30);
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryDate);
        tokenRepository.save(token);
    }

    @Override
    public User signUp(String name, String email, String password) throws ExistingUserException{
        //Implement BCryptPassword
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new ExistingUserException("User with this email id exists. Please login from /login");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setRoles(creatingRoles("ADMIN"));
        createToken(newUser);

        return userRepository.save(newUser);
    }

    @Override
    public User validateToken(String tokenValue) throws ExistingUserException{
        Optional<Token> optionalToken = tokenRepository.findByValue(tokenValue);
        if (optionalToken.isEmpty()) {
            throw new ExistingUserException("Token not found sign up again.");
        }
        return optionalToken.get().getUser();
    }

    @Override
    public void logout(String tokenValue) {

    }
}
