package com.example.userservicefeb25.repository;

import com.example.userservicefeb25.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    User save(User user);
}
