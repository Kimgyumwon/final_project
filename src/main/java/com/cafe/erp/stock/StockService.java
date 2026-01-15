package com.cafe.erp.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.erp.order.OrderDetailDTO;

@Service
public class StockService {
	
	@Autowired
	private StockDAO stockDAO;

	public StockDTO insertStockHistory(StockDTO stockDTO, OrderDetailDTO orderDetailDTO) {
		stockDTO.setStockInoutType("IN");
		stockDTO.setItemId(orderDetailDTO.getItemId());
		stockDTO.setStockQuantity(orderDetailDTO.getHqOrderQty());
		stockDAO.insertStockHistory(stockDTO);
		return stockDTO;
	}
	
	public int  existsStock(StockDTO stockDTO) {
		return stockDAO.existsStock(stockDTO);
	}
	public void updateStockQuantity(StockDTO stockDTO) {
		stockDAO.updateStockQuantity(stockDTO);
	}
	public void insertStock(StockDTO stockDTO) {
		stockDAO.insertStock(stockDTO);
	}
	
}
