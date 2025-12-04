package com.ashish.UserService.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.tanuj.UserService.model.Users;
import com.tanuj.UserService.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  
    @Autowired
    UsersRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> user = userRepository.findByUsername(username);

        if(!user.isPresent())
            throw new UsernameNotFoundException("User Not Found with username: " + username);

        UserDetails userDetails = UserDetailsImpl.build(user.get());

        return userDetails;
    }

}