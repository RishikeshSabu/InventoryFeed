package com.litmus7.inventoryfeedv2.constants;

public class Constants {
		//SQL queries
		public static final String INSERT_PRODUCT="INSERT INTO product (id, name, quantity, price) VALUES (?, ?, ?, ?)";
		public static final String SELECT_PRODUCT_BY_ID="SELECT id, name, quantity, price FROM product WHERE id = ?";
		//errorMessage
		public static final String CSV_ERROR="Unable to read CSC file";
		
		//folderAddress
		public static final String PROCESSED_FOLDER="D:/ThreadAssignment/InventoryFeedV2/CSVFiles/processed/";
		public static final String ERROR_FOLDERS="D:/ThreadAssignment/InventoryFeedV2/CSVFiles/error/";
		public static final String INPUT_FOLDER="D:/ThreadAssignment/InventoryFeedV2/CSVFiles/input/";
}
