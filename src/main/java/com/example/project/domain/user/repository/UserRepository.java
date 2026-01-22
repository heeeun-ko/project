package com.example.project.domain.user.repository;

import com.example.project.domain.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
