package com.cafe.erp.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.erp.item.ItemDTO;
import com.cafe.erp.item.ItemService;
import com.cafe.erp.vendor.VendorService;

@Controller
@RequestMapping("/order/*")
public class OrderController {
	
	private final ItemService itemService;
	private final VendorService vendorService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
    public OrderController(ItemService itemService, VendorService vendorService) {
        this.itemService = itemService;
        this.vendorService = vendorService;
    }
	
	// 발주 등록 페이지 요청
	@GetMapping("request")
	public String request(Model model) {
		model.addAttribute("showVendorSelect", true);
		model.addAttribute("vendorList", vendorService.findAll());
		return "order/hqOrder";
	}
	
	// 발주 등록 상품 검색 목록 요청
	@GetMapping("/order/itemSearch")
	@ResponseBody
	public List<ItemDTO> searchForOrder(
	        @RequestParam(required = false) Long vendorCode,
	        @RequestParam(required = false) String keyword) {
		
	    return itemService.searchForOrder(vendorCode, keyword);
	}
	
	// 발주 목록 요청
	@PostMapping("request")
	public String request(OrderDTO orderDTO) {
		System.out.println(orderDTO.getItems().iterator().next().getItemCode());
		System.out.println(orderDTO.getItems().iterator().next().getItemId());
		System.out.println(orderDTO.getItems().iterator().next().getItemSupplyPrice());
		System.out.println(orderDTO.getItems().iterator().next().getItemQuantity());
		System.out.println(orderDTO.getItems().iterator().next().getVendorCode());
		orderService.requestOrder(orderDTO);
		
		return "redirect:./request";
		
	}

}