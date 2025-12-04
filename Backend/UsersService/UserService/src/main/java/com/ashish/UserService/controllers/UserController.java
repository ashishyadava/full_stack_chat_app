package com.ashish.UserService.controllers;

import java.util.List;

import com.tanuj.UserService.model.Users;
import com.tanuj.UserService.model.DTO.UserInfoDTO;
import com.tanuj.UserService.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/chat/user")
public class UserController {

    @Autowired
    UsersRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserInfoDTO userToUserInfoDTO(Users userDetails){

        try{
            return UserInfoDTO.builder()
            .id(userDetails.getId())
            .username(userDetails.getUsername())
            .email(userDetails.getEmail())
            .build();
        }
        catch(Exception e){
            LOGGER.error("Error in function userToUserInfoDTO is: "+e);
            return null;
        }
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInfoDTO>> findAll(@PathVariable String userId) {
        
        try{
            Long userIdLong =  Long.parseLong(userId);
            
            List<Users> users = userRepository.findAll();
            
            List<UserInfoDTO> usersDto = users
                    .stream()
                    .filter(user -> !user.getId().equals(userIdLong))
                    .map(user -> userToUserInfoDTO(user))
                    .toList();

            return ResponseEntity
                    .ok(usersDto);
        }
        catch(Exception e){
            LOGGER.error("Error in controller findAll users except userId is: "+e);
            return null;
        }
    }

}