package com.cafe.erp.notification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/notification/*")
@Controller
public class NotificationController {

	
	
	@GetMapping("index")
	public void index() {
		
	}
	
	
	
}
