package com.hingebridge.devops.services;

import javax.servlet.http.HttpServletRequest;

import com.hingebridge.devops.config.dtos.LoginDTO;
import com.hingebridge.devops.config.dtos.OauthDTO;
import com.hingebridge.devops.config.dtos.ResponseDTO;

public interface GeneralService {
	ResponseDTO<OauthDTO> login(LoginDTO loginDTO);
	ResponseDTO<String> getRole(HttpServletRequest req);
}