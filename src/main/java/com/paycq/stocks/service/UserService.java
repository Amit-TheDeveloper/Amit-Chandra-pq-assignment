package com.paycq.stocks.service;

import com.paycq.stocks.dto.JwtTokenDto;
import com.paycq.stocks.dto.UserDto;

public interface UserService {

	public JwtTokenDto signUp(UserDto user);
}
