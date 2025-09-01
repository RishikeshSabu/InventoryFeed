package com.litmus7.inventoryfeedv3.util;

import com.litmus7.inventoryfeedv3.dto.ProductDTO;

public class Validation {
	public static boolean productValidation(ProductDTO product) {
		if(product.getProductId()<=0) return false;
		if(product.getProductName()==null || product.getProductName().trim().isEmpty()) return false;
		if(product.getQuantity()<0) return false;
		if(product.getPrice()<0) return false;
		return true;
	}
}
