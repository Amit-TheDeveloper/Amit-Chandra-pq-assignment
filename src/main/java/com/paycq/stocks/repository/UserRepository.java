package com.paycq.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paycq.stocks.entity.StockUser;

@Repository
public interface UserRepository extends JpaRepository<StockUser, Long> {
	
	public StockUser findByUserName(String userName);
	boolean existsByUserName(String userName);

}
