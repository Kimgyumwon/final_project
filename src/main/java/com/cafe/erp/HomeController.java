package com.cafe.erp;

import com.cafe.erp.security.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(@AuthenticationPrincipal UserDTO user) {
		boolean isStoreOwner = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STORE"));

		return isStoreOwner ? "view_store/dashboard" : "index";
	}
	
	
	
	
	
	
}
