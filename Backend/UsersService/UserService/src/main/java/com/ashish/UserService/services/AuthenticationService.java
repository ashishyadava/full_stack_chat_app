package com.ashish.UserService.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import com.tanuj.UserService.model.Roles;
import com.tanuj.UserService.model.Users;
import com.tanuj.UserService.model.DTO.SignUpDTO;
import com.tanuj.UserService.repository.RolesRepositoey;
import com.tanuj.UserService.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    
    @Autowired
    UsersRepository userRepository;

    @Autowired
    RolesRepositoey roleRepository;

    @Autowired
    PasswordEncoder encoder;

    private Set<Roles> getRolesToBeAdded(Set<String> strRoles){

        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Optional<Roles> userRoles = roleRepository.findByName("ROLE_USER");

            if(userRoles.isPresent()){
                roles.add(userRoles.get());
            }
            else{
                Roles userRole = new Roles();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
                roles.add(userRole);
            }
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                    Roles adminRole = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                    case "mod":
                    Roles modRole = roleRepository.findByName("ROLE_MODERATOR")
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                    default:
                    Roles userRole = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        return roles;
    }

    public Users signUpUser(SignUpDTO singUpDto){

        Users user = new Users(singUpDto.getUsername(),
                                singUpDto.getEmail(),
                                encoder.encode(singUpDto.getPassword()));

        Set<String> strRoles = singUpDto.getRoles();
        Set<Roles> roles = getRolesToBeAdded(strRoles);

        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

}
