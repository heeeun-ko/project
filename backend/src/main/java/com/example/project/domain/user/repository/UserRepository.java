package com.example.project.domain.user.repository;

import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndAuthProvider(String email, AuthProvider authProvider);

}