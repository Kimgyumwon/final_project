package com.cafe.erp.order;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDAO {
	
	public String selectMaxOrderId(String prefix, String orderDate);
	
	public void insertOrder(OrderDTO orderDTO);
	
	public void insertOrderDetail(OrderDetailDTO orderDetailDTO);
	
}
