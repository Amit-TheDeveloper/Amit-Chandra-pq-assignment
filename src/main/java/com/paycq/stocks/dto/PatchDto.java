package com.paycq.stocks.dto;

import com.paycq.stocks.util.Constants;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchDto {
	
	@NotNull(message = "Stock Id is missing")
	private Long id;
	@NotNull(message = Constants.MESSAGE_MISSING_STOCK_PRICE)
	private Double amount;

}
