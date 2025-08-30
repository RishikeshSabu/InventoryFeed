package com.litmus7.inventoryfeedv2.dao;

import com.litmus7.inventoryfeedv2.constants.Constants;
import com.litmus7.inventoryfeedv2.dto.ProductDTO;
import com.litmus7.inventoryfeedv2.exceptions.InventoryFeedDaoException;
import com.litmus7.inventoryfeedv2.util.DatabaseConnector;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InventoryFeedDao {
    private static final Logger logger = LogManager.getLogger(InventoryFeedDao.class);

    public int[] addProductsInBatch(List<ProductDTO> products) throws InventoryFeedDaoException {
        logger.trace("Entering addProductbatch function");
        int[] results;
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(Constants.INSERT_PRODUCT)) {
                logger.debug("Starting to add product");
                for (ProductDTO product : products) {
                    statement.setInt(1, product.getProductId());
                    statement.setString(2, product.getProductName());
                    statement.setInt(3, product.getQuantity());
                    statement.setDouble(4, product.getPrice());
                    statement.addBatch();
                }
                results = statement.executeBatch();
                connection.commit();
                logger.info("Batch inserted successfully");
                logger.trace("Exiting addProductsinBatch function");
                return results;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Failed batch insertion", e);
                throw new InventoryFeedDaoException("Batch insertion failed", e);
            }
        } catch (SQLException e) {
            throw new InventoryFeedDaoException("Database connection failed", e);
        }
    }

    public ProductDTO getProductById(int productId) throws InventoryFeedDaoException {
    	logger.trace("Entering getProductById with employee id : {}", productId);
    	try(Connection connection=DatabaseConnector.getConnection();
				PreparedStatement statement=connection.prepareStatement(Constants.SELECT_PRODUCT_BY_ID)){
    		statement.setInt(1, productId);
    		try(ResultSet rs=statement.executeQuery()){
    			if(rs.next()) {
    				ProductDTO product=new ProductDTO(rs.getInt("id"),rs.getString("name"),rs.getInt("quantity"),rs.getInt("price"));
    				return product;
    			}else return null;
    		}
    	}catch(SQLException e) {
			logger.error("Error in getting the detail of empployee with employee id {}",productId,e);
			throw new InventoryFeedDaoException("Connection failed",e);
		}
    }
}
