package com.hingebridge.devops.config.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("deprecation")
public class NewUserDTO {
	@Email
	@NotBlank(message = "email {app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String email;
	@NotBlank(message = "role {app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String role;
	@NotBlank(message = "firstname {app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String firstname;
	@NotBlank(message = "lastname {app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String lastname;
	@NotBlank(message = "{app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
//	@Size(min = 10, max = 200, message = "password {app.password.length}")
	private String password;
	@NotBlank(message = "{app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String phoneNumber;
	@NotBlank(message = "{app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String dob;
	@NotBlank(message = "{app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String nationality;
	@NotBlank(message = "{app.field.blank}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String gender;
}