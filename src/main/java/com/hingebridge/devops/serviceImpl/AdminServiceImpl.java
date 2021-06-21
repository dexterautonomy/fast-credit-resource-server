package com.hingebridge.devops.serviceImpl;

import static com.hingebridge.devops.constants.ResponseConstants.SUCCESSFUL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hingebridge.devops.config.dtos.ResponseDTO;
import com.hingebridge.devops.config.dtos.UserDTO;
import com.hingebridge.devops.config.dtos.NewUserDTO;
import com.hingebridge.devops.config.dtos.PagingDTO;
import com.hingebridge.devops.exceptions.AppException;
import com.hingebridge.devops.model.User;
import com.hingebridge.devops.repositories.UserRepo;
import com.hingebridge.devops.services.AdminService;
import com.hingebridge.devops.transformers.TransformUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TransformUtil txnUtils;

	@Override
	public ResponseDTO<String> addUser(NewUserDTO newUserDTO) {
		User user = null;

		log.info("---->>> Add user process commences");

		if (userRepo.existsByUsername(newUserDTO.getEmail())) {
			log.info("---->>> Username exists in the database");
			throw new AppException("Username already exists");
		}

		log.info("---->>> Username does not exist in the database");
		user = userRepo.save(txnUtils.userDTO_User(newUserDTO));

		if (user.getId() == null) {
			log.info("---->>> Error: Could not save user");
			throw new AppException("User did not save successfully");
		}

		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), SUCCESSFUL.getMessage());
	}

	@Override
	public ResponseDTO<List<UserDTO>> fetchAll(PagingDTO pagingDTO) {
		log.info("---->>> Fetching all user process commences");

//		List<User> users = userRepo.findAll(PageRequest.of(pagingDTO.getInit(), pagingDTO.getSize())).getContent();
		List<User> users = userRepo.findAllByDelFlagFalse();

		if (users.isEmpty()) {
			throw new AppException("No record found");
		}

		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), txnUtils.users_UserDTOs(users));
	}

	@Override
	public ResponseDTO<UserDTO> fetchOne(Long userId) {
		log.info("---->>> Fetching single user process commences");

		User user = userRepo.findById(userId).orElseThrow(() -> new AppException("No such user"));

		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), txnUtils.user_UserDTO(user));
	}

	@Override
	public ResponseDTO<UserDTO> fetchOneByName(String name) {
		log.info("---->>> Fetching single user process commences");

		User user = userRepo.findByUsernameAndDelFlagFalse(name).orElseThrow(() -> new AppException("No such user"));

		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), txnUtils.user_UserDTO(user));
	}

	@Override
	public ResponseDTO<String> deleteOneByName(String username) {
		log.info("---->>> Delete single user process commences");

		User user = userRepo.findByUsernameAndDelFlagFalse(username).orElseThrow(() -> new AppException("No such user"));
		
		if(user.getRole().equalsIgnoreCase("admin")) {
			throw new AppException("You cannot delete an admin");
		}
		
		user.setDelFlag(true);
		userRepo.save(user);

		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), SUCCESSFUL.getMessage());
	}
}