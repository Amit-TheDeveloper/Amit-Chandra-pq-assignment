package com.paycq.stocks.util;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Utility for storing message and variable constants.
 */
public interface Constants {
	
	public static final String MESSAGE_STOCK_DELETED = "Stock Deleted";
	public static final String MESSAGE_STOCK_NOT_FOUND = "Stock not found";
	public static final String MESSAGE_NO_STOCKS_AVAILABLE = "No Stocks Available";
	public static final String MESSAGE_STOCK_CREATE_ERROR =  "Error while adding stock";
	public static final String MESSAGE_MISSING_STOCK_NAME =  "Stock name cannot be blank";
	public static final String MESSAGE_MISSING_STOCK_PRICE =  "Stock price cannot be blank";
	public static final String MESSAGE_SUCCESSFUL =  "Operation successful.";
	public static final String MESSAGE_BAD_REQUEST =  "Bad Request. Operation Failed.";
	public static final String MESSAGE_SECURITY_TOKEN_CEREATION_ERROR =  "Error while creating token";
	
	public static final String MESSAGE_MISSING_USER_NAME =  "User name cannot be blank";
	public static final String MESSAGE_MISSING_PASSWORD = "Password Cannot be blank";
	
	public static final String API_DESCRIPTION_GET_BY_ID = "Find a stock by Id.";
	public static final String API_DESCRIPTION_DELETE_BY_ID = "Delete a stock by Id.";
	public static final String API_DESCRIPTION_UPDATE_AMOUNT_BY_ID = "Update price of a stock by Id.";
	public static final String API_DESCRIPTION_GET_ALL_STOCKS = "Fetch all stocks.";
	public static final String API_DESCRIPTION_CREATE_STOCKS = "Create a new Stock.";
	public static final String API_DESCRIPTION_SECURITY_FORBIDDEN = "Forbidden resource. Use a valid token";
	
	public static final String API_DESCRIPTION_SECURITY_TOKEN_SIGNUP = "Sign up for a token";
	
	
	public static final String REQUEST_PARAM_AMOUNT = "amount";
	public static final String REQUEST_PARAM_PAGE = "page";
	
	public static final String HTTP_CODE_200 = "200";
	public static final String HTTP_CODE_400 = "400";
	public static final String HTTP_CODE_404 = "404";
	public static final String HTTP_CODE_401 = "401";

}
