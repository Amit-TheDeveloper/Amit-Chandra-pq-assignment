package com.paycq.stocks.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.paycq.stocks.dto.StockDto;
import com.paycq.stocks.entity.Stock;
import com.paycq.stocks.exception.StockException;
import com.paycq.stocks.repository.StockRepository;
import com.paycq.stocks.service.StockService;
import com.paycq.stocks.util.Constants;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Service implementation for handling Stock operations.
 */
@Service
public class StockServiceImpl implements StockService {
	
	@Value("${page-size}")
	private Integer pageSize;

	@Autowired
	StockRepository repository;
	@Autowired
	ModelMapper mapper;

	@Override
	public StockDto findById(Long id) throws StockException {
		Optional<Stock> stockDao = repository.findById(id);
		if (stockDao.isEmpty()) {
			throw new StockException(Constants.MESSAGE_STOCK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

		return mapper.map(stockDao.get(), StockDto.class);
	}

	@Override
	public StockDto create(StockDto stockDto) throws StockException {
		Stock stockDao = mapper.map(stockDto, Stock.class);
		stockDao.setLastUpdated(LocalDateTime.now());
		Stock stockDaoCreated = repository.save(stockDao);
		return mapper.map(stockDaoCreated, StockDto.class);
	}

	@Override
	public StockDto update(Double price, Long id) throws StockException {
		if (!repository.existsById(id)) {
			throw new StockException(Constants.MESSAGE_STOCK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Optional<Stock> stockDao = repository.findById(id);
		stockDao.get().setCurrentPrice(price);
		stockDao.get().setLastUpdated(LocalDateTime.now());
		Stock updatedDao = repository.save(stockDao.get());
		return mapper.map(updatedDao, StockDto.class);
	}

	@Override
	public String deleteById(Long id) throws StockException {
		if (!repository.existsById(id)) {
			throw new StockException(Constants.MESSAGE_STOCK_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return Constants.MESSAGE_STOCK_DELETED;
	}
	
	@Override
	public List<StockDto> findAll(Optional<Integer> page, Optional<String> sortBy) {
		List<StockDto> result = new ArrayList<>();
		Page<Stock> stocks = repository.findAll(PageRequest.of(page.orElse(0), this.pageSize, Sort.Direction.ASC, sortBy.orElse("id")));
		stocks.get().forEach(r -> {
								result.add(mapper.map(r, StockDto.class));
							});
		
		if (result.size() == 0) {
			throw new StockException(Constants.MESSAGE_NO_STOCKS_AVAILABLE, HttpStatus.NOT_FOUND);
		}
		return result;
	}

}
