package com.cafe.erp.member.approval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.erp.member.attendance.MemberAttendanceDTO;
import com.cafe.erp.security.UserDTO;

@Controller
public class MemberApprovalController {
	
	@Autowired
	MemberApprovalService memberApprovalService;
	
	@PostMapping("/member/vacation_apply")
	@ResponseBody
	public String applyVacation(MemberAttendanceDTO dto, @AuthenticationPrincipal UserDTO userDTO) {
		 if (userDTO == null) {
		        return "로그인이 필요합니다.";
		    }
		 
	    try {


	    	memberApprovalService.autoApproval(dto, userDTO);
	        return "success";
	    } catch (Exception e) {
	        return e.getMessage(); 
	    }
	}

}
