package com.hingebridge.devops.web;

import static com.hingebridge.devops.constants.ResponseConstants.SUCCESSFUL;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hingebridge.devops.config.dtos.ResponseDTO;
import com.hingebridge.devops.config.dtos.UserDTO;
import com.hingebridge.devops.config.dtos.NewUserDTO;
import com.hingebridge.devops.config.dtos.PagingDTO;
import com.hingebridge.devops.services.AdminService;
import com.hingebridge.devops.utilities.UtilitiesAndTweaks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("fast-credit/admin/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private UtilitiesAndTweaks utilities;
	
	@PostMapping("save-new")
	public ResponseDTO<String> saveNewUser(@RequestBody @Valid NewUserDTO signupDTO, HttpServletRequest req) {
		log.info("---->>> Payload validation passed");
		log.info("---->>> Initializing saving new user: {}", signupDTO);
		
		utilities.channelCodeHandler(req);
		return adminService.addUser(signupDTO);
	}
	
	@PostMapping("users")
	public ResponseDTO<List<UserDTO>> fetchAllUsers(@RequestBody @Valid PagingDTO pagingDTO, HttpServletRequest req) {
		log.info("---->>> Payload validation passed");
		log.info("---->>> Initializing fetching all user: {}", pagingDTO);
		
		utilities.channelCodeHandler(req);
		return adminService.fetchAll(pagingDTO);
	}
	
	@PostMapping("user/{userId}")
	public ResponseDTO<UserDTO> fetchOneUser(@PathVariable Long userId, HttpServletRequest req) {
		log.info("---->>> Payload validation passed");
		log.info("---->>> Initializing fetching single user");
		
		utilities.channelCodeHandler(req);
		return adminService.fetchOne(userId);
	}
	
	@GetMapping("user/{name}")
	public ResponseDTO<UserDTO> fetchOneUserByName(@PathVariable String name, HttpServletRequest req) {
		log.info("---->>> Payload validation passed");
		log.info("---->>> Initializing fetching single user");
		
		utilities.channelCodeHandler(req);
		return adminService.fetchOneByName(name);
	}
	
	@GetMapping("token")
	public ResponseDTO<String> testToken(HttpServletRequest req) {
		log.info("---->>> Initializing token verification");
		
		utilities.channelCodeHandler(req);
		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), SUCCESSFUL.getMessage());
	}
	
	@GetMapping("del/{username}")
	public ResponseDTO<String> deleteUser(@PathVariable String username, HttpServletRequest req) {
		log.info("---->>> Payload validation passed");
		log.info("---->>> Initializing delete single user");
		
		utilities.channelCodeHandler(req);
		return adminService.deleteOneByName(username);
	}
}
