package com.hingebridge.devops.transformers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hingebridge.devops.config.dtos.NewUserDTO;
import com.hingebridge.devops.config.dtos.UserDTO;
import com.hingebridge.devops.model.User;
import com.hingebridge.devops.utilities.UtilitiesAndTweaks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransformUtil {
	@Autowired
	private UtilitiesAndTweaks utilities;

	public User userDTO_User(final NewUserDTO newUserDTO) {
		User user = new User();

		log.info("---->>> Initializing transformation from NewUserDTO to User object");

		user.setUsername(newUserDTO.getEmail());
		user.setPassword(utilities.getPasswordEncoder().encode(newUserDTO.getPassword()));
		user.setFirstname(newUserDTO.getFirstname());
		user.setLastname(newUserDTO.getLastname());
		user.setPhonenumber(newUserDTO.getPhoneNumber());
		user.setNationality(newUserDTO.getNationality());
		user.setRole(newUserDTO.getRole());
		user.setGender(newUserDTO.getGender());
		user.setDateOfBirth(newUserDTO.getDob());
		
		return user;
	}

	public List<UserDTO> users_UserDTOs(final List<User> users) {
		log.info("---->>> Initializing transformation from List<User> to List<UserDTO> object");
		
		return users.stream().map(this::user_UserDTO).collect(Collectors.toList());
	}
	
	public UserDTO user_UserDTO(final User user) {
		UserDTO userDTO = new UserDTO();

		log.info("---->>> Initializing transformation from User Object to UserDTO object");

		try {
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getUsername());
			userDTO.setFirstname(user.getFirstname());
			userDTO.setLastname(user.getLastname());
			userDTO.setPhonenumber(user.getPhonenumber());
			userDTO.setNationality(user.getNationality());
			userDTO.setRole(user.getRole());
			userDTO.setGender(user.getGender());
			userDTO.setCreatedOn(utilities.formatDate(user.getCreatedOn()));
			userDTO.setDateOfBirth(utilities.formatDate(new SimpleDateFormat("YYYY-MM-dd").parse(user.getDateOfBirth())));
		} catch (ParseException e) {
			log.error(e.getLocalizedMessage());;
		}
		
		return userDTO;
	}
}
