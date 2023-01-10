package com.paycq.stocks.service;

import java.util.List;
import java.util.Optional;

import com.paycq.stocks.dto.StockDto;
import com.paycq.stocks.exception.StockException;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Service interface for handling Stock operations.
 */

public interface StockService {

	/**
	 * Find a stock by id.
	 *
	 * @param id Id of an existing Stock.
	 * @return Stock fetched.
	 * @throws StockException
	 */
	public StockDto findById(Long id) throws StockException;

	/**
	 * Creates a new stock.
	 *
	 * @param stockDto Input stock information.
	 * @return Stock created.
	 * @throws StockException
	 */
	public StockDto create(StockDto stockDto) throws StockException;

	/**
	 * Updates a stock amount by it's Id.
	 *
	 * @param price Price of a Stock
	 * @param id Id of an existing Stock
	 * @return Updated Stock.
	 * @throws StockException
	 */
	public StockDto update(Double price, Long id) throws StockException;

	/**
	 * Deletes a stock by it's Id.
	 *
	 * @param id Id of an existing Stock
	 * @return Status for delete operation
	 * @throws StockException
	 */
	public String deleteById(Long id) throws StockException;

	/**
	 * Find all stocks.
	 *
	 * @param page to fetch results for a page index.
	 * @return Available stocks for the page.
	 * @throws StockException
	 */
	public List<StockDto> findAll(Optional<Integer> page, Optional<String> sortBy) throws StockException;

}
