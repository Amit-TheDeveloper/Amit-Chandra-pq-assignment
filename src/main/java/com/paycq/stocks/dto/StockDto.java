package com.paycq.stocks.dto;

import java.time.LocalDateTime;

import com.paycq.stocks.util.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDto {

	private Long id;
	@NotBlank(message = Constants.MESSAGE_MISSING_STOCK_NAME)
	private String name;
	@NotNull(message = Constants.MESSAGE_MISSING_STOCK_PRICE)
	private Double currentPrice;
	private LocalDateTime lastUpdated;
}