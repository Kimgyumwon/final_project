package com.cafe.erp.member.commute;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.erp.company.CompanyHolidayService;
import com.cafe.erp.member.MemberDTO;
import com.cafe.erp.security.UserDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/commute/*")
public class MemberCommuteController {
	
	@Autowired
	private MemberCommuteService commuteService;
	
	@Autowired
	private CompanyHolidayService holidayService;
	
	@Autowired
	private MemberCommuteDAO commuteDAO;
	
	@PostMapping("checkIn")
	@ResponseBody
	public String checkIn(@AuthenticationPrincipal UserDTO userDTO) throws Exception{
		
		if(userDTO == null) {
			return "fail";
		}
		
		int memberId = userDTO.getMember().getMemberId();
		
		// 공휴일/연차면 출근 금지
	    java.time.LocalDate today = java.time.LocalDate.now();
	    boolean isHoliday = holidayService.isHoliday(today);
	    boolean isLeave = commuteDAO.existsApprovedFullDayLeave(memberId, java.sql.Date.valueOf(today)) > 0;
	    if (isHoliday || isLeave) {
	        return "blocked";
	    }
		
		MemberCommuteDTO commuteDTO = new MemberCommuteDTO();
		commuteDTO.setMemberId(memberId);
		
		// 현재 시간
		LocalDateTime now = LocalDateTime.now();
		commuteDTO.setMemCommuteInTime(now);
		
		LocalDateTime in = now.withHour(9).withMinute(0).withSecond(0);
		
		if(now.isAfter(in)) {
			commuteDTO.setMemCommuteState("지각");
		} else {
			commuteDTO.setMemCommuteState("출근");
			
		}
		
		int result = commuteService.checkIn(commuteDTO);
		
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
		
	    java.time.LocalDate today = java.time.LocalDate.now();
	    boolean isHoliday = holidayService.isHoliday(today);
	    boolean isLeave = commuteDAO.existsApprovedFullDayLeave(memberId, java.sql.Date.valueOf(today)) > 0;
	    if (isHoliday || isLeave) {
	        return "blocked";
	    }
		
		MemberCommuteDTO commuteDTO = new MemberCommuteDTO();
		
		commuteDTO.setMemberId(memberId);
		
		LocalDateTime now = LocalDateTime.now();
		commuteDTO.setMemCommuteOutTime(now);
		
		LocalDateTime hour1730 = now.withHour(17).withMinute(30).withSecond(0);
		
		if(now.isBefore(hour1730)) {
			commuteDTO.setMemCommuteState("조퇴");
		} else if(now.isAfter(hour1730)) {
			commuteDTO.setMemCommuteState("퇴근");
			
		}
		
		
		int result = commuteService.checkOut(commuteDTO);
		
		if(result > 0) {
			return "success";
		}
		
		return "already";
	}
	
	@GetMapping("availability")
	@ResponseBody
	public Map<String, Object> availability(@AuthenticationPrincipal UserDTO userDTO) throws Exception {

	    int memberId = userDTO.getMember().getMemberId();
	    java.time.LocalDate today = java.time.LocalDate.now();

	    boolean isHoliday = holidayService.isHoliday(today);
	    boolean isLeave = commuteDAO.existsApprovedFullDayLeave(memberId, java.sql.Date.valueOf(today)) > 0;

	    boolean canCommute = !(isHoliday || isLeave);

	    Map<String, Object> res = new java.util.HashMap<>();
	    res.put("date", today.toString());
	    res.put("isHoliday", isHoliday);
	    res.put("isLeave", isLeave);
	    res.put("canCommute", canCommute);
	    res.put("reason", isHoliday ? "HOLIDAY" : (isLeave ? "LEAVE" : "OK"));
	    return res;
	}
	
	
	@GetMapping("list")
	@ResponseBody
	public Map<String, Object> list(@AuthenticationPrincipal UserDTO userDTO, MemberCommuteSearchDTO searchDTO) throws Exception {

	    int memberId = userDTO.getMember().getMemberId();

	    searchDTO.setMemberId(memberId);

	    if (searchDTO.getPage() == null) searchDTO.setPage(1L);
	    if (searchDTO.getPerPage() == null) searchDTO.setPerPage(35L);

	    Long totalCount = commuteDAO.countCommuteList(searchDTO);
	    searchDTO.pageing(totalCount);

	    Map<String, Object> res = new java.util.HashMap<>();
	    res.put("list", commuteDAO.attendanceList(searchDTO));
	    res.put("pager", searchDTO);
	    res.put("totalCount", totalCount);
	    return res;
	}



}
