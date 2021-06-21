package com.hingebridge.devops.config.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String email;
	private String role;
	private String firstname;
	private String lastname;
	private String password;
	private String phonenumber;
	private String dateOfBirth;
	private String nationality;
	private String gender;
    private String createdOn;
}