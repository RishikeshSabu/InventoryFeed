package com.litmus7.inventoryfeedv2.ui;

import java.util.List;

import com.litmus7.inventoryfeedv2.controller.InventoryFeedController;
import com.litmus7.inventoryfeedv2.dto.Response;

public class InventoryApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InventoryFeedController controller=new InventoryFeedController();
		Response<List<String>> response=controller.loadAndSaveProductsFromInputFolder();
		if(response.getStatusCode()==200) {
			for(String message:response.getData()) {
				System.out.println(message);
			}
			
		}else {
			System.out.println(response.getErrorMessage());
		}
	}

}
