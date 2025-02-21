package com.example.userservicefeb25.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Cascade added
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private List<Role> roles;
}

/*

  1         M
User ----- Role => M:M
  M         1


 */
