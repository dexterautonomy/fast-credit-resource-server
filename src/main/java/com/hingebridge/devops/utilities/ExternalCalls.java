package com.hingebridge.devops.utilities;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hingebridge.devops.config.dtos.OauthDTO;
import com.hingebridge.devops.exceptions.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExternalCalls {

	@Autowired
	private AppProperties props;
	@Autowired
	private RestTemplate restTemplate;

	public OauthDTO generateAuthServeTokenPasswordGrantType(final String username, final String password) {
		OauthDTO oauthDTO = null;
		String basicAuth = props.getClientId() + ":" + props.getClientSecret();

		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestHeader.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuth.getBytes()));

		log.info("---->>> Initiating process to get oauth token from auth-server");

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

		requestBody.put("grant_type", Arrays.asList(props.getGrantTypePassword()));
		requestBody.put("username", Arrays.asList(username));
		requestBody.put("password", Arrays.asList(password));

		log.info("Auth-server request: {}", requestBody);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeader);
		ResponseEntity<String> response = restTemplate.postForEntity(props.getAuthServerUrl(), requestEntity,
				String.class);

		if (response != null) {
			oauthDTO = JsonBuilder.toClass(response.getBody(), OauthDTO.class);
			log.info("---->>> OauthDTO: {}", oauthDTO);
		} else {
			log.info("---->>> No Response from authorization server");
			throw new AppException("---->>> No Response from authorization server");
		}

		return oauthDTO;
	}
}