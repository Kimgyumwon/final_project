package com.cafe.erp.company;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.erp.security.UserDTO;
import com.cafe.erp.util.ExcelUtil;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
public class CompanyHolidayController {
	
		@Autowired
		private CompanyHolidayService holidayService;
		
		@GetMapping("admin_holiday_list")
		  public String holidayList(Model model, CompanyHolidaySearchDTO searchDTO) throws Exception {

		    if (searchDTO.getPage() == null || searchDTO.getPage() < 1) searchDTO.setPage(1L);
		    if (searchDTO.getPerPage() == null || searchDTO.getPerPage() < 1) searchDTO.setPerPage(10L);

		    List<CompanyHolidayDTO> list = holidayService.getHolidayList(searchDTO);

		    model.addAttribute("list", list);
		    model.addAttribute("pager", searchDTO);
		    model.addAttribute("totalCount", searchDTO.getTotalCount());

		    return "member/admin_holiday_list";
		  }
	
		  @PostMapping("admin_holiday_add")
		  @ResponseBody
		  public String addCompanyHoliday(CompanyHolidayDTO dto, @AuthenticationPrincipal UserDTO userDTO) throws Exception {
			  
			  try {
				  dto.setMemberId(userDTO.getMember().getMemberId());
				  dto.setComHolidayType("회사휴일");
				  holidayService.addHoliday(dto);
				  return "success";
				
			} catch (Exception e) {
				return e.getMessage();
			}
		    
		  }
		  
		  @PostMapping("admin_holiday_update")
		  @ResponseBody
		  public String updateHoliday(CompanyHolidayDTO dto) throws Exception {
		    return holidayService.updateHoliday(dto) ? "success" : "fail";
		  }
		  
		  @PostMapping("admin_holiday_toggle")
		  @ResponseBody
		  public String toggleHoliday(@RequestParam int companyHolidayId, @RequestParam boolean active) throws Exception {
		    return holidayService.toggleHolidayActive(companyHolidayId, active) ? "success" : "fail";
		  }
		  
		  
		  @GetMapping("admin_holiday_excel")
		  public void holidayExcel(CompanyHolidaySearchDTO dto, HttpServletResponse response) throws Exception {

		      if (dto.getPage() == null || dto.getPage() < 1) dto.setPage(1L);
		      if (dto.getPerPage() == null || dto.getPerPage() < 1) dto.setPerPage(10L);

		      Long total = holidayService.totalCount(dto);
		      dto.setTotalCount(total);
		      dto.pageing(total);


		      List<CompanyHolidayDTO> list = holidayService.getHolidayList(dto); 

		      String[] headers = {"날짜", "유형", "휴무명", "등록자"};
		      ExcelUtil.download(list, headers, "회사휴무", response, (row, h) -> {
		          row.createCell(0).setCellValue(h.getComHolidayDate() == null ? "" : h.getComHolidayDate().toString());
		          row.createCell(1).setCellValue(h.getComHolidayType() == null ? "" : h.getComHolidayType());
		          row.createCell(2).setCellValue(h.getComHolidayName() == null ? "" : h.getComHolidayName());
		          row.createCell(3).setCellValue(String.valueOf(h.getMemberId()));
		      });
		  }

		}




