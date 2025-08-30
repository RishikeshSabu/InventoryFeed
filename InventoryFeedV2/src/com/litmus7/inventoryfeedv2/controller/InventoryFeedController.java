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
import com.litmus7.inventoryfeedv2.util.GetAllCSVFiles;

public class InventoryFeedController {
	private static final Logger logger = LogManager.getLogger(InventoryFeedController.class);
	public Service service=new Service();
	private static final String INPUT_DIR =Constants.INPUT_FOLDER;
	private int length=0;
	List<String> messages=new ArrayList<>();
	public Response<List<String>> loadAndSaveProductsFromInputFolder(){
		try {
			File[] files=GetAllCSVFiles.getCSVFiles(INPUT_DIR);
			if(files==null||files.length==0) {
				return new Response<>(500,"Files cannot be empty");
			}
			try {
				for(File file:files) {
					int[] results=service.loadAndSaveProducts(file);
					length+=results.length;
					messages.add("Batch Insertion Success for "+file.getName());
				}
			}catch(InventoryFeedServiceException e) {
				messages.add(e.getMessage());
			}
			
			return new Response<>(200,messages);
		}catch(Exception e){
			return new Response<>(500,e.getMessage());
		}
	}
}
