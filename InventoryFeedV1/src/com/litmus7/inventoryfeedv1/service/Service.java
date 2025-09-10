package com.litmus7.inventoryfeedv1.service;

import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedv1.dao.InventoryFeedDao;
import com.litmus7.inventoryfeedv1.dto.ProductDTO;
import com.litmus7.inventoryfeedv1.exceptions.CSVFileAccessException;
import com.litmus7.inventoryfeedv1.exceptions.InventoryFeedDaoException;
import com.litmus7.inventoryfeedv1.exceptions.InventoryFeedServiceException;
import com.litmus7.inventoryfeedv1.exceptions.ValidationFailedException;
import com.litmus7.inventoryfeedv1.util.ApplicationProperties;
import com.litmus7.inventoryfeedv1.util.GetAllCSVFiles;
import com.litmus7.inventoryfeedv1.util.MoveFile;
import com.litmus7.inventoryfeedv1.util.ReadCSV;
import com.litmus7.inventoryfeedv1.util.Validation;
import com.litmus7.inventoryfeedv1.constants.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public List<String> loadAndSaveProductsFromInput(String inputDir) throws InventoryFeedServiceException{
		List<String> messages=new ArrayList<>();
		File[] files=GetAllCSVFiles.getCSVFiles(inputDir);
		if(files==null||files.length==0) {
			throw new InventoryFeedServiceException("Files connot be empty");
		}
		for(File file:files) {
			try {
				loadAndSaveProducts(file);
			}catch(InventoryFeedServiceException e) {
				messages.add(e.getMessage());
			}
		}
		return messages;
	}
}
