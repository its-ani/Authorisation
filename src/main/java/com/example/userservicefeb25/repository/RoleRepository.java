package com.example.userservicefeb25.repository;

import com.example.userservicefeb25.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByValue(String name);
    Role save(Role role);
}
