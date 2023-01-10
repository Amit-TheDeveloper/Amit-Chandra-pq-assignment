package com.paycq.stocks.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paycq.stocks.dto.JwtTokenDto;
import com.paycq.stocks.dto.UserDto;
import com.paycq.stocks.exception.StockException;
import com.paycq.stocks.repository.UserRepository;
import com.paycq.stocks.service.TokenService;
import com.paycq.stocks.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private TokenService tokenService;
	private PasswordEncoder passwordEncoder;
	
	@Override
    public JwtTokenDto signUp(UserDto singUpDTO) {
        if (!userRepository.existsByUserName(singUpDTO.getUserName())) {
            com.paycq.stocks.entity.StockUser user = new com.paycq.stocks.entity.StockUser();
            user.setUserName(singUpDTO.getUserName());
            user.setRole(singUpDTO.getRole());
            user.setPassword(passwordEncoder.encode(singUpDTO.getPassword()));
            userRepository.save(user);
            
            String token = tokenService.generateToken(new UsernamePasswordAuthenticationToken(singUpDTO.getUserName(), singUpDTO.getPassword()));
            return new JwtTokenDto(token);
        } else {
            throw new StockException("username already exist", HttpStatus.FORBIDDEN);
        }
    }

	 	
}
