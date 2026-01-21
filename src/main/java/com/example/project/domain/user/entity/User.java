package com.example.project.domain.user.entity;

import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String name;
}
