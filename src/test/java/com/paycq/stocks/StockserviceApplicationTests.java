package com.paycq.stocks;

import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paycq.stocks.dto.StockDto;
import com.paycq.stocks.exception.ErrorResponse;


@SpringBootTest
class StockserviceApplicationTests {
	
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	ObjectMapper mapper;
	
	private static String API_PATH = "http://localhost:9091/api/v1/stock";
	

	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}
	
	/**
	 * Given a stock
	 * When calling the POST operation
	 * Then create a new Stock 
	 */
	@Test
	@WithMockUser("asas")
	void addStock() {
		
		setup();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		String stock1 = """
						{
							"name": "Stock-%s",
							"currentPrice": 33.22
						}
						""".formatted(Math.random());
		String stock2 = """
						{
							"name": "Stock-%s",
							"currentPrice": 44.44
						}
						""".formatted(Math.random());
		
		
		try {
			MvcResult res = mvc.perform(MockMvcRequestBuilders.post(API_PATH).content(stock1).contentType(MediaType.APPLICATION_JSON)).andReturn();
			StockDto response  = mapper.readValue(res.getResponse().getContentAsString(), StockDto.class);
			org.junit.jupiter.api.Assertions.assertEquals(res.getResponse().getStatus(), HttpStatus.CREATED.value());
			org.junit.jupiter.api.Assertions.assertEquals(33.22, response.getCurrentPrice());
			
			MvcResult res2 = mvc.perform(MockMvcRequestBuilders.post(API_PATH).content(stock2).contentType(MediaType.APPLICATION_JSON)).andReturn();
			StockDto response2  = mapper.readValue(res2.getResponse().getContentAsString(), StockDto.class);
			org.junit.jupiter.api.Assertions.assertEquals(res2.getResponse().getStatus(), HttpStatus.CREATED.value());
			org.junit.jupiter.api.Assertions.assertEquals(44.44, response2.getCurrentPrice());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a stock id
	 * When calling GET operation
	 * Then fetch the stock with it's Id
	 */
	@Test
	@WithMockUser("asas")
	void findStockId2() {
		setup();
		try {
			MvcResult res =  mvc.perform(MockMvcRequestBuilders.get(API_PATH+"/2").contentType(MediaType.APPLICATION_JSON)).andReturn();
			StockDto response  = mapper.readValue(res.getResponse().getContentAsString(), StockDto.class);
			org.junit.jupiter.api.Assertions.assertEquals(res.getResponse().getStatus(), HttpStatus.OK.value());
			org.junit.jupiter.api.Assertions.assertEquals(2, response.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a Stock service
	 * When calling Get operations on stocks
	 * Then fetch all Stocks
	 */
	@Test
	@WithMockUser("asas")
	void findAllRecipes() {
		setup();
		try {
			MvcResult res =  mvc.perform(MockMvcRequestBuilders.get(API_PATH).contentType(MediaType.APPLICATION_JSON)).andReturn();
			List<StockDto> response  = mapper.readValue(res.getResponse().getContentAsString(), List.class);
			org.junit.jupiter.api.Assertions.assertEquals(res.getResponse().getStatus(), HttpStatus.OK.value());
			org.junit.jupiter.api.Assertions.assertTrue(response.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Given a Stock service
	 * When calling Get operations on stocks using page no
	 * Then fetch all Stocks for that page number
	 */
	@Test
	@WithMockUser("asas")
	void findAllRecipesForPage() {
		setup();
		try {
			MvcResult res =  mvc.perform(MockMvcRequestBuilders.get(API_PATH+"?page=2").contentType(MediaType.APPLICATION_JSON)).andReturn();
			List<StockDto> response  = mapper.readValue(res.getResponse().getContentAsString(), List.class);
			org.junit.jupiter.api.Assertions.assertEquals(res.getResponse().getStatus(), HttpStatus.OK.value());
			org.junit.jupiter.api.Assertions.assertTrue(response.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Given a stock id
	 * When calling Delete operations on stock
	 * Then delete the stock
	 * 
	 * NOTE: This method will fail subsequent time else we need to restart server.
	 */
	@Test
	@WithMockUser("asas")
	void testDeleteRecipeById() {
		setup();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		String stock1 = """
						{
							"name": "Stock-%s",
							"currentPrice": 33.22
						}
						""".formatted(Math.random());

		//Add a stock to be deleted.
		MvcResult res;
		try {
			res = mvc.perform(MockMvcRequestBuilders.post(API_PATH).content(stock1).contentType(MediaType.APPLICATION_JSON)).andReturn();
			StockDto response  = mapper.readValue(res.getResponse().getContentAsString(), StockDto.class);
			org.junit.jupiter.api.Assertions.assertEquals(res.getResponse().getStatus(), HttpStatus.CREATED.value());
			
			mvc.perform(MockMvcRequestBuilders.delete(API_PATH + response.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * Given a non existing stock id
	 * When calling GET operation
	 * Then application returns NOT_FOUND HTTP_STATUS
	 */
	@Test
	@WithMockUser("asas")
	void findNonExistingStock() {
		
		setup();
		try {
			MvcResult res =  mvc.perform(MockMvcRequestBuilders.get(API_PATH+"/202").contentType(MediaType.APPLICATION_JSON)).andReturn();
			StockDto response  = mapper.readValue(res.getResponse().getContentAsString(), StockDto.class);
			org.junit.jupiter.api.Assertions.assertEquals(res.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
