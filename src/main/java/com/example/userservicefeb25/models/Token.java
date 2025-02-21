package com.example.userservicefeb25.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token extends BaseModel {
    private String value;

    @ManyToOne
    private User user;

    private Date expiryAt;
}

/*

Token ------ User => M:1

 */
