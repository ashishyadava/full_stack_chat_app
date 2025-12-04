package com.ashish.UserService.model.DTO;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class SignUpDTO {
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
 
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
        
    @NotBlank
    @Size(min = 6, max = 64)
    private String password;

    private Set<String> roles;

}
