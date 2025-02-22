package com.example.userservicefeb25.services;

import com.example.userservicefeb25.exceptions.ExistingUserException;
import com.example.userservicefeb25.exceptions.UserNotRegisteredException;
import com.example.userservicefeb25.models.Role;
import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;
import com.example.userservicefeb25.repository.RoleRepository;
import com.example.userservicefeb25.repository.TokenRepository;
import com.example.userservicefeb25.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;  // Injected automatically


    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validatePassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);
    }

    @Override
    public Token login(String email, String password) throws UserNotRegisteredException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UserNotRegisteredException("User with this email is not registered");
        }

        boolean isMatchingPassword = passwordEncoder.matches(password, user.get().getPassword());
        if(!isMatchingPassword) {
            throw new UserNotRegisteredException("Wrong password");
        }

//        if(!user.get().getPassword().equals(password)) {
//            throw new UserNotRegisteredException("Wrong password");
//        }

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
        Optional<Role> existingRole = roleRepository.findByValue(role);

//        if(existingRole.isEmpty()) {
//            Role newRole = new Role();
//            newRole.setValue(role);
//            Role createdRole = roleRepository.save(newRole);
//            ArrayList<Role> roles = new ArrayList<>();
//            roles.add(createdRole);
//
//            return roles;
//        }

        return existingRole.map(r -> new ArrayList<>(List.of(r)))
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setValue(role);
                    return new ArrayList<>(List.of(roleRepository.save(newRole)));
                });
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
        User newUser = new User();

        if(user.isPresent()) {
            throw new ExistingUserException("User with this email id exists. Please login from /login");
        }
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setEmail(email);
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);

        User savedUser = userRepository.save(newUser);

        savedUser.setRoles(creatingRoles("ADMIN"));


        User dbUser = userRepository.save(savedUser);
        createToken(dbUser);

        return dbUser;
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
