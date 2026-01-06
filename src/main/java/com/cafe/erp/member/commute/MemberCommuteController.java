package com.cafe.erp.member.commute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.erp.member.MemberDTO;
import com.cafe.erp.security.UserDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/commute/*")
public class MemberCommuteController {
	
	@Autowired
	private MemberCommuteService commuteService;
	
	@PostMapping("checkIn")
	@ResponseBody
	public String checkIn(@AuthenticationPrincipal UserDTO userDTO) throws Exception{
		
		if(userDTO == null) {
			return "fail";
		}
		
		int memberId = userDTO.getMember().getMemberId();
		
		int result = commuteService.checkIn(memberId);
		
		if(result > 0) {
			return "success";
		}
		
		return "already";
	}
	
	@PostMapping("checkOut")
	@ResponseBody
	public String checkOut(@AuthenticationPrincipal UserDTO userDTO) throws Exception{
		
		if(userDTO == null) {
			return "fail";
		}
		
		int memberId = userDTO.getMember().getMemberId();
		int result = commuteService.checkOut(memberId);
		
		if(result > 0) {
			return "success";
		}
		
		return "already";
	}

}
