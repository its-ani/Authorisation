package com.example.userservicefeb25.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseModel {
    private String value;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
