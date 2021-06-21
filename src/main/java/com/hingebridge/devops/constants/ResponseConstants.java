package com.hingebridge.devops.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseConstants {
	SUCCESSFUL("00", "Operation successful"),
	FAILURE("99", "Operation failed");

	private String code;
	private String message;
}