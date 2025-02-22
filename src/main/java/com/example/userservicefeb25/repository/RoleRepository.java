package com.example.userservicefeb25.repository;

import com.example.userservicefeb25.models.Role;
import com.example.userservicefeb25.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByValue(String name);
//    List<Role> findByUser(User user);
    Role save(Role role);
}
