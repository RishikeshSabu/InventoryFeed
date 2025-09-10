package com.litmus7.inventoryfeedv2.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedv2.constants.Constants;
import com.litmus7.inventoryfeedv2.dto.Response;
import com.litmus7.inventoryfeedv2.exceptions.InventoryFeedServiceException;
import com.litmus7.inventoryfeedv2.service.Service;
import com.litmus7.inventoryfeedv2.util.ApplicationProperties;
import com.litmus7.inventoryfeedv2.util.GetAllCSVFiles;

public class InventoryFeedController {
	private static final Logger logger = LogManager.getLogger(InventoryFeedController.class);
	public Service service=new Service();
	private static final String INPUT_DIR =ApplicationProperties.getInputFolder();
	private int length=0;
	List<String> messages=new ArrayList<>();
	List<Thread> threads = new ArrayList<>();
	public Response<List<String>> loadAndSaveProductsFromInputFolder(){
		long startTime=System.currentTimeMillis();
		try {
			List<String>messages=service.loadAndSaveFrominput(INPUT_DIR);
			long endTime=System.currentTimeMillis();
			long timeTaken=endTime-startTime;
			System.out.println(timeTaken);
			return new Response<>(200,messages);
		}catch(InventoryFeedServiceException e){
			return new Response<>(500,e.getMessage());
		}
	}
}
