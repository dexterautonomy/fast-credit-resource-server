package com.hingebridge.devops.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hingebridge.devops.config.dtos.LoginDTO;
import com.hingebridge.devops.config.dtos.OauthDTO;
import com.hingebridge.devops.config.dtos.ResponseDTO;
import com.hingebridge.devops.services.GeneralService;
import com.hingebridge.devops.utilities.UtilitiesAndTweaks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("fast-credit/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
	@Autowired
	private GeneralService generalService;
	@Autowired
	private UtilitiesAndTweaks utilities;

	@PostMapping("login")
	public ResponseDTO<OauthDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest req) {
		log.info("---->>> Payload validation passed");
		log.info("---->>> Initializing the login process: {}", loginDTO);

		utilities.channelCodeHandler(req);
		return generalService.login(loginDTO);
	}

	@GetMapping("role")
	public ResponseDTO<String> login(HttpServletRequest req) {

		utilities.channelCodeHandler(req);
		return generalService.getRole(req);
	}
}