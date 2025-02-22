package com.example.userservicefeb25.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date lastModifiedAt;

    @JsonProperty(defaultValue = "false")
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @PrePersist
    public void prePersist() {
        if (deleted == null) {
            deleted = false;
        }
    }
}
