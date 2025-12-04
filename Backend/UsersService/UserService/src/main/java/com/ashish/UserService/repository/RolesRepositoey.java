package com.ashish.UserService.repository;

import java.util.Optional;

import com.tanuj.UserService.model.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepositoey extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}