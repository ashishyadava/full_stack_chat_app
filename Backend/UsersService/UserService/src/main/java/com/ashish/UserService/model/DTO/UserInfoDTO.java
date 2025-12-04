package com.ashish.UserService.model.DTO;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
public class UserInfoDTO {
    
	private Long id;

    @NotBlank
	private String username;

	@NotBlank
	private String email;

	private List<String> roles;
    
}
