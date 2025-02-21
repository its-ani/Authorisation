package com.example.userservicefeb25.repository;

import com.example.userservicefeb25.models.Token;
import com.example.userservicefeb25.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);
    Optional<Token> findByUser(User user);
    Token save(Token token);
}

