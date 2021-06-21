package com.hingebridge.devops.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hingebridge.devops.config.dtos.LoginDTO;
import com.hingebridge.devops.config.dtos.OauthDTO;
import com.hingebridge.devops.config.dtos.ResponseDTO;
import com.hingebridge.devops.exceptions.AppException;
import com.hingebridge.devops.model.User;
import com.hingebridge.devops.repositories.UserRepo;
import com.hingebridge.devops.services.GeneralService;
import com.hingebridge.devops.utilities.ExternalCalls;

import lombok.extern.slf4j.Slf4j;

import static com.hingebridge.devops.constants.ResponseConstants.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class GeneralServiceImpl implements GeneralService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ExternalCalls extCalls;
	
	@Override
	public ResponseDTO<OauthDTO> login(LoginDTO loginDTO)
	{
		log.info("---->>> Login process commences");
		User user = userRepo.findByUsernameAndDelFlagFalse(loginDTO.getUsername()).orElseThrow(() -> new AppException("Username does not exist"));
		
		if(!new BCryptPasswordEncoder().matches(loginDTO.getPassword(), user.getPassword()))
		{
			throw new AppException("Password is incorrect");
		}
		
		OauthDTO oauthDTO = extCalls.generateAuthServeTokenPasswordGrantType(loginDTO.getUsername(), loginDTO.getPassword());
		
		if(oauthDTO != null) {
			switch(user.getRole()) {
				case "admin":{
					oauthDTO.setRole("ADMIN");
				}
				break;
				
				default:{
					oauthDTO.setRole("USER");
				}
			}
		}
		
		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), oauthDTO);
	}
	
	@Override
	public ResponseDTO<String> getRole(HttpServletRequest req) {
		User user = userRepo.findByUsernameAndDelFlagFalse(req.getUserPrincipal().getName())
				.orElseThrow(() -> new AppException("Username does not exist"));
		
		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), user.getRole());
	}
}