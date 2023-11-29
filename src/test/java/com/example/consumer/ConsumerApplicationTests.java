package com.example.consumer;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.ResponseModel;
import com.example.consumer.repository.LoggingSellerNativeRepository;
import com.example.consumer.repository.LoggingSellerNativeRepositoryTest;
import com.example.consumer.service.LoggingSellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
class ConsumerApplicationTests {

	@Test
	public void test_insertLoggingSeller_expect_201(){
		LoggingSellerNativeRepository loggingSellerNativeRepository  = new LoggingSellerNativeRepositoryTest();
		LoggingSellerService loggingSellerService = new LoggingSellerService(loggingSellerNativeRepository);

		LoggingSellerModel loggingSellerModel = new LoggingSellerModel();
		//loggingSellerModel.setAdventureSellerId(1);
		//loggingSellerModel.setItemId(1);
		ResponseModel<Integer> result =loggingSellerService.insertLoggingSeller(loggingSellerModel);
		log.info("=======>result:{}",result);
		Assertions.assertTrue(result.getStatus() == 201);

	}



}
