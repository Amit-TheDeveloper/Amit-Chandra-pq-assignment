package com.paycq.stocks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paycq.stocks.dto.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class StockUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String userName;
	@Column
	@JsonIgnore
	private String password;
	@Column
	private Role role;
}
