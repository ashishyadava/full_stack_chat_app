package com.ashish.UserService.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "roles")
@Entity
@Getter
@Setter
public class Roles {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 20)
  private String name;

}
