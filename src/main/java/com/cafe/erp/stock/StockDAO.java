package com.cafe.erp.stock;

import org.apache.ibatis.annotations.Mapper;

import com.cafe.erp.order.OrderDetailDTO;

@Mapper
public interface StockDAO {
	
	public void inStock();
	
	public void insertStockHistory(StockDTO stockDTO);
	
	public int existsStock(StockDTO stockDTO);
	
	public void updateStockQuantity(StockDTO stockDTO);
	
	public void insertStock(StockDTO stockDTO);
}
