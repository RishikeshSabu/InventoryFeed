package com.litmus7.inventoryfeedv1.ui;

import java.util.List;

import com.litmus7.inventoryfeedv1.controller.InventoryFeedContoller;
import com.litmus7.inventoryfeedv1.dto.Response;

public class InventoryApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InventoryFeedContoller controller=new InventoryFeedContoller();
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
