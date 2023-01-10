package com.paycq.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paycq.stocks.entity.Stock;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Repository for performing data access layer operations.
 */

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{

}
