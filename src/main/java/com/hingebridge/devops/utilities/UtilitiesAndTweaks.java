package com.hingebridge.devops.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.hingebridge.devops.exceptions.AppException;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class UtilitiesAndTweaks {
	@Autowired
	private AppProperties props;
	
	public void channelCodeHandler(HttpServletRequest req) {
		log.info("---->>> Initializing request source check");
		log.info("---->>> Authenticated user is: {}",
				req.getUserPrincipal() == null ? "No user" : req.getUserPrincipal().getName());

		final String channelCode = req.getHeader("ChannelCode");
		final String webRequest = props.getWebChannelCode();
		
		log.info("---->>> Request: " + req);
		log.info("---->>> ChannelCode: " + channelCode);

		if (channelCode != null) {
			if (channelCode.equals(webRequest)) {
				log.info("---->>> Request source: WEB BROWSER");
			} 
			else {
				log.info("---->>> Error:  Invalid access code");
				throw new AppException("Access code is invalid");
			}
		} else {
			log.info("---->>> Error:  Access code cannot be empty, blank or null");
			throw new AppException("Access code cannot be empty");
		}
	}
	
	public BCryptPasswordEncoder getPasswordEncoder() {
		log.info("---->>> Obtaining password encoder");
		return new BCryptPasswordEncoder();
	}
	
	public String formatDate(Date date) {
		return new SimpleDateFormat("dd MMMM, YYYY").format(date);
	}
}