package com.example.userservicefeb25.dtos;

import com.example.userservicefeb25.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private List<Role> roles;
}
