package com.example.userservicefeb25.dtos;

import com.example.userservicefeb25.models.Role;
import lombok.Data;

import java.util.List;

@Data
public class RolesDto {
    private String email;
    private String name;
    private List<String> roles;

}
