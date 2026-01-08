package com.cafe.erp.order;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailDTO {
	private Integer hqOrderDetailId;
	private String hqOrderItemCode;
	private String hqOrderItemName;
	private Integer hqOrderQty;
	private Integer hqOrderPrice;
	private Integer hqOrderAmount;
	private Date hqOrderCreatedAt;
	private String hqOrderId;
	private Integer itemId;
	private Integer vendorCode;
	
}
