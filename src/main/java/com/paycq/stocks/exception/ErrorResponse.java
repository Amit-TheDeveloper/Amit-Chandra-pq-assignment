package com.paycq.stocks.exception;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 */
@Data
@Builder
public class ErrorResponse implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private String errorMessage;
    private int errorCode;
}
