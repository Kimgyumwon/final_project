package com.cafe.erp.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.erp.member.MemberDTO;
import com.cafe.erp.notification.service.NotificationService;
import com.cafe.erp.order.event.OrderReceivedEvent;
import com.cafe.erp.security.UserDTO;
import com.cafe.erp.stock.StockDTO;
import com.cafe.erp.stock.StockInoutDTO;
import com.cafe.erp.stock.StockService;

@Service
public class OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private StockService stockService;
	
	public void requestOrder(OrderDTO orderDTO, UserDTO userDTO) { 
		
		if (orderDTO.getHqOrderTotalAmount() == null) {
	        throw new IllegalArgumentException("발주 금액이 없습니다.");
	    }
		
		// 본사/가맹 어느쪽인지
		int orderType = userDTO.getMember().getMemberId();
		// 기본(memberId가 1로 시작): 본사(false)
		boolean isHqOrder = false;
		// 그외(memberId가 2로 시작): 가맹(ture)
		if(String.valueOf(orderType).charAt(0) == '2') {
			isHqOrder = true;
			// 스토어 정보 가져오기
			OrderDTO dto = orderDAO.selectStoreId(orderType);
			
			orderDTO.setStoreId(dto.getStoreId());
			orderDTO.setStoreName(dto.getStoreName());
		}
		// 발주번호(orderId) 생성
		String orderId = generateOrderId(isHqOrder);
				
		// 발주번호 기입
		orderDTO.setHqOrderId(orderId);
		
		// 요청자 기입
		orderDTO.setMemberId(orderType);
		
		// 상태값 기입(요청/자동승인)
		orderDTO.setHqOrderStatus(100);	// 기본: 요청	
		if (isHqOrder) {
			List<OrderItemRequestDTO> detailList = orderDTO.getItems();
			int isAutoOrder = 0;
			for (OrderItemRequestDTO orderItemRequestDTO : detailList) {
				// 0: 자동승인 1:승인요청
				if(orderItemRequestDTO.getItemAutoOrder() == true) {
					isAutoOrder++;
				}
			}
			if (isAutoOrder == 0) {
				orderDTO.setHqOrderStatus(200);	// isAutoOrder 값이 0이면 자동승인			
			}
		}
		// 발주 insert
		insertOrder(orderDTO, isHqOrder);
		// 발주 상세 insert
		insertOrderItemDetail(orderDTO, isHqOrder);

		if (isHqOrder && orderDTO.getHqOrderStatus() == 100) {
		    notificationService.sendOrderNotificationToFinanceTeam(
		        orderDTO.getHqOrderId(),
		        orderType
		  );
		}
		
	}
	
	// orderId 생성
	public String generateOrderId(boolean isHqOrder) {

	    // R / P 구분
	    String prefix = isHqOrder ? "R" : "P";

	    // 오늘 날짜 yyyyMMdd
	    String orderDate = LocalDate.now()
	        .format(DateTimeFormatter.BASIC_ISO_DATE);
	    
	    String maxOrderId = "";
	    if (prefix.equals("P")) {
	    	// 오늘 + 타입 기준 최대값 조회
	    	maxOrderId = orderDAO.selectMaxOrderHqId(prefix, orderDate);
	    } else {
	    	maxOrderId = orderDAO.selectMaxOrderStoreId(prefix, orderDate);
	    }

	    // 일련번호 계산
	    int nextSeq = 1;
	    if (maxOrderId != null) {
	      // 뒤 4자리 추출
	      String seqStr = maxOrderId.substring(maxOrderId.length() - 4);
	      nextSeq = Integer.parseInt(seqStr) + 1;
	    }

	    // 4자리 zero padding
	    String seq = String.format("%04d", nextSeq);

	    // 최종 발주번호
	    return prefix + orderDate + seq;
	  }
	
	public void insertOrder(OrderDTO orderDTO, Boolean isHqOrder) {
		
		if (!isHqOrder) {
			orderDAO.insertHqOrder(orderDTO);
		} else {
			orderDAO.insertStoreOrder(orderDTO);			
		}
	}
	public void insertOrderItemDetail(OrderDTO orderDTO, Boolean isHqOrder) {
		if (!isHqOrder) {
			for (OrderItemRequestDTO req : orderDTO.getItems()) {
				
				OrderDetailDTO detail = new OrderDetailDTO();
				detail.setHqOrderId(orderDTO.getHqOrderId()); // ⭐ FK 세팅
				detail.setHqOrderItemCode(req.getItemCode());
				detail.setHqOrderQty(req.getItemQuantity());
				detail.setHqOrderPrice(req.getItemSupplyPrice());
				detail.setHqOrderAmount(
						req.getItemQuantity() * req.getItemSupplyPrice()
						);
				detail.setItemId(req.getItemId());
				detail.setVendorCode(req.getVendorCode());
				detail.setHqOrderItemName(req.getItemName());
				
				orderDAO.insertHqOrderItemDetail(detail);
			}
		} else {
			// 가맹점 발주 상세 insert
			for (OrderItemRequestDTO req : orderDTO.getItems()) {
				
				OrderDetailDTO detail = new OrderDetailDTO();
				detail.setHqOrderId(orderDTO.getHqOrderId()); // ⭐ FK 세팅
				detail.setHqOrderItemCode(req.getItemCode());
				detail.setHqOrderQty(req.getItemQuantity());
				detail.setHqOrderPrice(req.getItemSupplyPrice());
				detail.setHqOrderAmount(
						req.getItemQuantity() * req.getItemSupplyPrice()
						);
				detail.setItemId(req.getItemId());
				detail.setVendorCode(req.getVendorCode());
				detail.setHqOrderItemName(req.getItemName());
				
				// 발주 상세 insert
				orderDAO.insertStoreOrderItemDetail(detail);
			}
		}
	}

	
	// 발주 목록 
	public List<OrderDTO> listHq(List<Integer> statuses, MemberDTO member) {
		return orderDAO.listHq(statuses, member);
	}
	public List<OrderDTO> listStore(List<Integer> statuses, MemberDTO member) {
		return orderDAO.listStore(statuses, member);
	}
	
	public List<OrderDetailDTO> getHqOrderDetail(String orderNo) {
		return orderDAO.getHqOrderDetail(orderNo);
	}
	public List<OrderDetailDTO> getStoreOrderDetail(String orderNo) {
		return orderDAO.getStoreOrderDetail(orderNo);
	}
	
	public void approveOrder(List<OrderRequestDTO> orderNos, MemberDTO member) {
		int orderApprover = member.getMemberId();
		for (OrderRequestDTO orderNo : orderNos) {
			if ("HQ".equals(orderNo.getOrderType())) {
				orderDAO.approveHqOrder(orderNo.getOrderNo(), orderApprover);							
			} else if("STORE".equals(orderNo.getOrderType())){
				orderDAO.approveStoreOrder(orderNo.getOrderNo(), orderApprover);							
			}
		}
	}
	
	public List<OrderDTO> getApprovedOrder() {
		return orderDAO.getApprovedOrder();
	}
	public List<OrderDetailDTO> getApprovedOrderDetail() {
		return orderDAO.getApprovedOrderDetail();
	}
	// 반려
	public void rejectOrder(OrderRejectDTO orderRejectDTO, UserDTO userDTO) {
		// 발주테이블 상태값을 반려로 update
		orderDAO.rejectOrder(orderRejectDTO);
		// 가맹점주 아이디 조회
		OrderRejectDTO result = orderDAO.rejectOrderNotification(orderRejectDTO);
		int senderMemberId = userDTO.getMember().getMemberId(); // 본사 직원 아이디
		int receiverMemberId = result.getStoreMemberId(); // 가맹점주 아이디
		String orderId = result.getRejectId(); // 발주 번호
		
		notificationService.sendOrderRejectNotification(
				senderMemberId,
				receiverMemberId,
				orderId
		);
	}

	@Transactional
	public void receiveOrder(List<OrderRequestDTO> orderNos, MemberDTO member) {
		System.out.println("🔥 orderNos size = " + orderNos.size());
		
		List<OrderDetailDTO> orderDetailList;
		StockInoutDTO stockInoutDTO = new StockInoutDTO();
		int inputId = 0;
		int warehouseNo = 0;
		
		for (OrderRequestDTO orderNo : orderNos) {
			// 1️ 이미 입고완료인지 체크
			if ("HQ".equals(orderNo.getOrderType())) {
				orderDAO.receiveHqOrder(orderNo.getOrderNo());							
			} else if("STORE".equals(orderNo.getOrderType())){
				orderDAO.receiveStoreOrder(orderNo.getOrderNo());							

			    OrderDTO hqOrder = orderDAO.isHqAlreadyReceived(orderNo.getOrderNo());
			    if (hqOrder != null && hqOrder.getHqOrderStatus() == 400) {
			        continue;
			    }

			} else if ("STORE".equals(orderNo.getOrderType())) {

			    OrderDTO storeOrder = orderDAO.isStoreAlreadyReceived(orderNo.getOrderNo());
			    if (storeOrder != null && storeOrder.getHqOrderStatus() == 400) {
			        continue;
			    }
			}

	        // 2️ 승인/입고완료 상태 변경
	        if ("HQ".equals(orderNo.getOrderType())) {
	            orderDAO.receiveHqOrder(orderNo.getOrderNo());
	            eventPublisher.publishEvent(
                        new OrderReceivedEvent("HQ", orderNo.getOrderNo())
                );
	            orderDetailList = orderDAO.getHqOrderDetail(orderNo.getOrderNo());
	            // 3 입출고번호 생성(입출고타입, 창고번호, 본사발주번호, 가맹발주번호)
	            warehouseNo = 11;
	            stockInoutDTO = settingStock(orderNo.getOrderType(), 11, orderNo.getOrderNo());
	        } else {
	            orderDAO.receiveStoreOrder(orderNo.getOrderNo());
	            eventPublisher.publishEvent(
	                    new OrderReceivedEvent("STORE", orderNo.getOrderNo())
	            );
	            orderDetailList = orderDAO.getStoreOrderDetail(orderNo.getOrderNo());
	            // 3 입출고번호 생성(입출고타입, 창고번호, 본사발주번호, 가맹발주번호)
	            int storeId = member.getMemberId();
	            warehouseNo = orderDAO.findByWarehouseId(storeId);	            
	            stockInoutDTO = settingStock(orderNo.getOrderType(), warehouseNo, orderNo.getOrderNo());
	        }
	        	orderDAO.insertOrderInOut(stockInoutDTO);
	        	inputId = stockInoutDTO.getInputId();

	        // 4️ 상세 목록 조회 (이게 핵심)
	        List<OrderDetailDTO> details =
	            "HQ".equals(orderNo.getOrderType())
	            ? orderDAO.getHqOrderDetail(orderNo.getOrderNo())
	            : orderDAO.getStoreOrderDetail(orderNo.getOrderNo());

	        // 5️ 상세 기준 재고 처리
	        for (OrderDetailDTO d : details) {
	    		StockDTO stockDTO = new StockDTO();
	        	stockDTO.setWarehouseId(warehouseNo);
	            stockDTO.setInputId(inputId);
	            
	            // 5-1️ 재고 이력 INSERT
	            stockDTO = stockService.insertStockHistory(stockDTO, d);

	            // 5-2️ 현재 재고 UPDATE / INSERT
	            if(stockService.existsStock(stockDTO) > 0) {
	            	stockService.updateStockQuantity(stockDTO);	            	
	            } else {
	            	stockService.insertStock(stockDTO);	            		            	
	            }
	        }
		}
	}
	public StockInoutDTO settingStock(String OrderType, int warehouseNo, String orderNo) {
		StockInoutDTO stockInoutDTO = new StockInoutDTO();
		if("HQ".equals(OrderType)) {
			stockInoutDTO.setInputType("IN");
			stockInoutDTO.setWarehouseId(11);
			stockInoutDTO.setHqOrderId(orderNo);
		} else {
			stockInoutDTO.setInputType("IN");
			stockInoutDTO.setWarehouseId(warehouseNo);
			stockInoutDTO.setHqOrderId(orderNo);
		}
		return stockInoutDTO;
	}
	public void cancelApprove(List<OrderRequestDTO> orderNos) {
		for (OrderRequestDTO orderNo : orderNos) {
			if ("HQ".equals(orderNo.getOrderType())) {
				orderDAO.cancelApproveHqOrder(orderNo.getOrderNo());							
			} else if("STORE".equals(orderNo.getOrderType())){
				orderDAO.cancelApproveStoreOrder(orderNo.getOrderNo());							
			}
		}
	}

}
