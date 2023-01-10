package com.paycq.stocks.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paycq.stocks.dto.StockDto;
import com.paycq.stocks.exception.StockException;
import com.paycq.stocks.service.StockService;
import com.paycq.stocks.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Rest Controller for a Stock service.
 */
@RestController
@RequestMapping("api/v1/stock")
@AllArgsConstructor
@SecurityRequirement(name="bearer")
public class StockController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	StockService stockService;

	@Operation(description = Constants.API_DESCRIPTION_GET_BY_ID, tags = {"STOCKS"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = Constants.HTTP_CODE_200, description = Constants.MESSAGE_SUCCESSFUL, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_404, description = Constants.MESSAGE_STOCK_NOT_FOUND, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_401, description = Constants.API_DESCRIPTION_SECURITY_FORBIDDEN, content = {@Content(mediaType = "application/json")})
	})
	@GetMapping("{id}")
	public ResponseEntity<StockDto> getById(@PathVariable Long id) {
		LOGGER.debug("Fetching record for id: {%s}".formatted(id));
		return ResponseEntity.ok(stockService.findById(id));
	}

	@Operation(description = Constants.API_DESCRIPTION_CREATE_STOCKS, tags = {"STOCKS"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = Constants.HTTP_CODE_200, description = Constants.MESSAGE_SUCCESSFUL, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_401, description = Constants.API_DESCRIPTION_SECURITY_FORBIDDEN, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_400, description = Constants.MESSAGE_BAD_REQUEST, content = {@Content(mediaType = "application/json")})
	})
	@PostMapping
	public ResponseEntity<StockDto> create(@Valid @RequestBody StockDto stockDto) throws StockException {
		try {
			LOGGER.debug("Creating a new stock record for: {%s}".formatted(stockDto.getName()));
			return ResponseEntity.status(HttpStatus.CREATED).body(stockService.create(stockDto));
		}catch (Exception e) {
			throw new StockException(Constants.MESSAGE_STOCK_CREATE_ERROR, HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(description = Constants.API_DESCRIPTION_UPDATE_AMOUNT_BY_ID, tags = {"STOCKS"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = Constants.HTTP_CODE_200, description = Constants.MESSAGE_SUCCESSFUL, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_404, description = Constants.MESSAGE_STOCK_NOT_FOUND, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_401, description = Constants.API_DESCRIPTION_SECURITY_FORBIDDEN, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_400, description = Constants.MESSAGE_BAD_REQUEST, content = {@Content(mediaType = "application/json")})
	})
	@PatchMapping("{id}")
	public ResponseEntity<StockDto> update(@RequestParam(Constants.REQUEST_PARAM_AMOUNT) Double amount, @PathVariable Long id) {
		LOGGER.debug("Updating stock {%s}:".formatted(id));
		return ResponseEntity.ok(stockService.update(amount, id));
	}

	@Operation(description = Constants.API_DESCRIPTION_DELETE_BY_ID, tags = {"STOCKS"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = Constants.HTTP_CODE_200, description = Constants.MESSAGE_SUCCESSFUL, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_404, description = Constants.MESSAGE_STOCK_NOT_FOUND, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_401, description = Constants.API_DESCRIPTION_SECURITY_FORBIDDEN, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_400, description = Constants.MESSAGE_BAD_REQUEST, content = {@Content(mediaType = "application/json")})
	})
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		LOGGER.debug("Deleting stock Id {%s}:".formatted(id));
		return ResponseEntity.ok(stockService.deleteById(id));
	}

	@Operation(description = Constants.API_DESCRIPTION_GET_ALL_STOCKS, tags = {"STOCKS"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = Constants.HTTP_CODE_200, description = Constants.MESSAGE_SUCCESSFUL, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_404, description = Constants.MESSAGE_STOCK_NOT_FOUND, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_401, description = Constants.API_DESCRIPTION_SECURITY_FORBIDDEN, content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = Constants.HTTP_CODE_400, description = Constants.MESSAGE_BAD_REQUEST, content = {@Content(mediaType = "application/json")})
	})
	@GetMapping
	public ResponseEntity<List<StockDto>> finadAll(@RequestParam(Constants.REQUEST_PARAM_PAGE) Optional<Integer> page,
												   @RequestParam Optional<String> sortBy) {
		
		LOGGER.debug("Finding all stock records:");
		return ResponseEntity.ok(stockService.findAll(page, sortBy));
	}
}
