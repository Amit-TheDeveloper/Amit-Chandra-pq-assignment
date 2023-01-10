package com.paycq.stocks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paycq.stocks.dto.JwtTokenDto;
import com.paycq.stocks.dto.UserDto;
import com.paycq.stocks.exception.StockException;
import com.paycq.stocks.service.UserService;
import com.paycq.stocks.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Rest Controller for a generating security token.
 */
@RestController
@RequestMapping("api/v1/stock")
@AllArgsConstructor
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	UserService userService;

	@Operation(description = Constants.API_DESCRIPTION_SECURITY_TOKEN_SIGNUP, tags = {"SECURITY"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = Constants.HTTP_CODE_200, description = Constants.MESSAGE_SUCCESSFUL, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_400, description = Constants.MESSAGE_BAD_REQUEST, content = {@Content(mediaType = "application/json")}),
	})
	@PostMapping("/signup")
	public ResponseEntity<JwtTokenDto> signUp(@Valid @RequestBody UserDto stockDto) throws StockException {
		try {
			LOGGER.debug("Creating a new token for: {%s}".formatted(stockDto.getUserName()));
			return ResponseEntity.ok(userService.signUp(stockDto));
		}catch (Exception e) {
			throw new StockException(Constants.MESSAGE_SECURITY_TOKEN_CEREATION_ERROR, HttpStatus.BAD_REQUEST);
		}
	}

}
