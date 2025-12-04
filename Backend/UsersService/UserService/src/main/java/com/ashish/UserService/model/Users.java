package com.ashish.UserService.model;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username",  nullable=false, length = 50, unique = true)
    private String username;

    @Column(name="email",  nullable=false, length = 60, unique = true)
    private String email;

    @Column(name="password",  nullable=false, length = 64)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
              joinColumns = @JoinColumn(name = "users_id"),
              inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Roles> roles = new HashSet<>();

    public Users() {
    }

    public Users(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
    }
}