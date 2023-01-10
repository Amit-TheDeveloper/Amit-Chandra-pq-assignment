package com.paycq.stocks;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.paycq.stocks.security.RsaKeyProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Application class to trigger Stock Service.
 */
@SpringBootApplication
@OpenAPIDefinition(security = @SecurityRequirement(name = "oauth2"))
@SecurityScheme(name = "bearer", scheme="bearer",  type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@EnableConfigurationProperties(RsaKeyProperties.class)
public class StockserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockserviceApplication.class, args);
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
