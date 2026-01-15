package com.cafe.erp.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cafe.erp.order.OrderDetailDTO;

@Controller
public class StockController {
	
	@Autowired
	private StockService stockService;
	
	public void insertStockHistory(StockDTO stockDTO, OrderDetailDTO orderDetailDTO) {
		stockService.insertStockHistory(stockDTO, orderDetailDTO);
	}
	
	public int existsStock(StockDTO stockDTO){
	 	return stockService.existsStock(stockDTO);
	}
	
	public void updateStockQuantity(StockDTO stockDTO) {
		stockService.updateStockQuantity(stockDTO);
	}

	public void insertStock(StockDTO stockDTO) {
		stockService.insertStock(stockDTO);
	}
}
