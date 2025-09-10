package com.litmus7.inventoryfeedv1.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedv1.constants.Constants;
import com.litmus7.inventoryfeedv1.dto.Response;
import com.litmus7.inventoryfeedv1.exceptions.InventoryFeedServiceException;
import com.litmus7.inventoryfeedv1.service.Service;
import com.litmus7.inventoryfeedv1.util.ApplicationProperties;
import com.litmus7.inventoryfeedv1.util.GetAllCSVFiles;



public class InventoryFeedContoller {
	private static final Logger logger = LogManager.getLogger(InventoryFeedContoller.class);
	public Service service=new Service();
	private static final String INPUT_DIR =ApplicationProperties.getInputFolder();
	
	List<String> messages=new ArrayList<>();
	public Response<List<String>> loadAndSaveProductsFromInputFolder(){
		long startTime=System.currentTimeMillis();
		try {
			List<String>messages=service.loadAndSaveProductsFromInput(INPUT_DIR);
			for(String message:messages) {
				System.out.println(message);
			}
			long endTime=System.currentTimeMillis();
			long timeTaken=endTime-startTime;
			System.out.println(timeTaken);
			return new Response<>(200,messages);
		}catch(InventoryFeedServiceException e) {
			return new Response<>(500,e.getMessage());
		}catch(Exception e){
			return new Response<>(500,e.getMessage());
		}
	}
}
