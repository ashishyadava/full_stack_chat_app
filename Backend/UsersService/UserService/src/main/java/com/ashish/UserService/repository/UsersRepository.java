package com.ashish.UserService.repository;

import java.util.Optional;

import com.tanuj.UserService.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    
    Boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);

    Boolean existsByEmail(String email);
}