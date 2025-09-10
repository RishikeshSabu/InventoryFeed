package com.litmus7.inventoryfeedv3.service;

import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedv3.constants.Constants;
import com.litmus7.inventoryfeedv3.dao.InventoryFeedDao;
import com.litmus7.inventoryfeedv3.dto.ProductDTO;
import com.litmus7.inventoryfeedv3.dto.Response;
import com.litmus7.inventoryfeedv3.exceptions.CSVFileAccessException;
import com.litmus7.inventoryfeedv3.exceptions.InventoryFeedDaoException;
import com.litmus7.inventoryfeedv3.exceptions.InventoryFeedServiceException;
import com.litmus7.inventoryfeedv3.exceptions.ValidationFailedException;
import com.litmus7.inventoryfeedv3.util.ApplicationProperties;
import com.litmus7.inventoryfeedv3.util.GetAllCSVFiles;
import com.litmus7.inventoryfeedv3.util.MoveFile;
import com.litmus7.inventoryfeedv3.util.ReadCSV;
import com.litmus7.inventoryfeedv3.util.Validation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;

public class Service {
	private static final Logger logger = LogManager.getLogger(Service.class);
	public InventoryFeedDao dao=new InventoryFeedDao();
	public int[] loadAndSaveProducts(File file) throws InventoryFeedServiceException{
		List<String[]> records=new ArrayList<>();
		List<ProductDTO> products=new ArrayList<>();
		try {
			records=ReadCSV.readCSV(file);
			for(String[] record : records) {
				int id=Integer.parseInt(record[0]);
				int quantity=Integer.parseInt(record[2]);
				double price=Double.parseDouble(record[3]);
				ProductDTO product=new ProductDTO(id,record[1],quantity,price);
				boolean result=Validation.productValidation(product);
				if(result==false) {
					throw new ValidationFailedException("Validation Failed");
				}
				products.add(product);
				//for tomorrow
				//till now csv has been read. Now save to db. do that outside of for loop
			}
			int[] results;
			results=dao.addProductsInBatch(products);
			MoveFile.moveFile(file,ApplicationProperties.getProcessedFolder());
			return results;
			
		}catch(CSVFileAccessException e) {
			logger.error("Failed to read CSV");
			throw new InventoryFeedServiceException("Failed to read CSV",e);
		}catch(ValidationFailedException e) {
			throw new InventoryFeedServiceException(e.getMessage(),e);
		}catch(InventoryFeedDaoException e) {
			MoveFile.moveFile(file,ApplicationProperties.getErrorFolder());
			throw new InventoryFeedServiceException(e.getMessage()+" for "+file.getName(),e);
		}
	}
	public List<String> loadAndSaveFromInput(String inputDir) throws InventoryFeedServiceException{
		File[] files=GetAllCSVFiles.getCSVFiles(inputDir);
		List<String> messages=new ArrayList<>();
		if(files==null||files.length==0) {
			throw new InventoryFeedServiceException("Files cannot be empty");
		}
		ExecutorService executor=Executors.newFixedThreadPool(3);
		for(File file:files) {
			executor.submit(()->{
				try {
					logger.info("Thread started for {}",file.getName());
					loadAndSaveProducts(file);
					logger.info("Thread completed for file: {}",file.getName());
				}catch(InventoryFeedServiceException e) {
					messages.add(e.getMessage());
				}
			},"Thread - "+file.getName());
			messages.add("Started processing "+file.getName());
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1,TimeUnit.HOURS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		messages.add("Program Ended");
		return messages;
	}
}
