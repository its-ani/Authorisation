package com.example.userservicefeb25.repository;

import com.example.userservicefeb25.models.Role;
import com.example.userservicefeb25.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    @Query("SELECT r FROM User u JOIN u.roles r WHERE u.email = :email")
    List<Role> findRoleByEmail(@Param("email") String email);

//    List<Role> findRoleByEmail(String email);
    User save(User user);
}