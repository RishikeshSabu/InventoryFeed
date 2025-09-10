package com.litmus7.inventoryfeedv3.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedv3.constants.Constants;
import com.litmus7.inventoryfeedv3.dto.Response;
import com.litmus7.inventoryfeedv3.exceptions.InventoryFeedServiceException;
import com.litmus7.inventoryfeedv3.service.Service;
import com.litmus7.inventoryfeedv3.util.ApplicationProperties;
import com.litmus7.inventoryfeedv3.util.GetAllCSVFiles;

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
			List<String> messages=service.loadAndSaveFromInput(INPUT_DIR);
			long endTime=System.currentTimeMillis();
			long timeTaken=endTime-startTime;
			System.out.println(timeTaken);
			return new Response<>(200,messages);
		}catch(Exception e){
			return new Response<>(500,e.getMessage());
		}
	}
}
