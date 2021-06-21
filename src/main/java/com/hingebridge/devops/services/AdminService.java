package com.hingebridge.devops.services;

import com.hingebridge.devops.config.dtos.ResponseDTO;
import com.hingebridge.devops.config.dtos.UserDTO;

import java.util.List;

import com.hingebridge.devops.config.dtos.NewUserDTO;
import com.hingebridge.devops.config.dtos.PagingDTO;

public interface AdminService {
	ResponseDTO<String> addUser(NewUserDTO newUserDTO);
	ResponseDTO<List<UserDTO>> fetchAll(PagingDTO pagingDTO);
	ResponseDTO<UserDTO> fetchOne(Long userId);
	ResponseDTO<UserDTO> fetchOneByName(String name);
	ResponseDTO<String> deleteOneByName(String username);
}