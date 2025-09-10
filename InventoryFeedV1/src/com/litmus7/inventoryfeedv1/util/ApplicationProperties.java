package com.litmus7.inventoryfeedv1.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {
	private static String input_folder;
	private static String processed_folder;
	private static String error_folder;
	
	static {
		Properties props=new Properties();
		try(FileReader fileReader=new FileReader("D:/ThreadAssignment/InventoryFeedV1/src/resources/application.properties")) {
			props.load(fileReader);
			input_folder=props.getProperty("input_folder");
			processed_folder=props.getProperty("processed_folder");
			error_folder=props.getProperty("error_folder");
		}catch(IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getInputFolder() {
		return input_folder;
	}
	
	public static String getProcessedFolder() {
		return processed_folder;
	}
	
	public static String getErrorFolder() {
		return error_folder;
	}
	
}
