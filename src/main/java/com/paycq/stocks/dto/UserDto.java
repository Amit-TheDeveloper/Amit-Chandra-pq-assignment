package com.paycq.stocks.dto;

import com.paycq.stocks.util.Constants;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	@NotBlank(message = Constants.MESSAGE_MISSING_USER_NAME)
	private String userName;
	@NotBlank(message = Constants.MESSAGE_MISSING_PASSWORD)
	private String password;
	private Role role;
}
