package com.cafe.erp.member;

import java.time.YearMonth;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.erp.company.CompanyHolidayDTO;
import com.cafe.erp.company.CompanyHolidayService;
import com.cafe.erp.member.attendance.MemberAttendanceDAO;
import com.cafe.erp.member.attendance.MemberAttendanceDTO;
import com.cafe.erp.member.attendance.MemberAttendanceSearchDTO;
import com.cafe.erp.member.attendance.MemberLeaveStatsDTO;
import com.cafe.erp.member.commute.MemberCommuteDTO;
import com.cafe.erp.member.commute.MemberCommuteSearchDTO;
import com.cafe.erp.member.commute.MemberCommuteService;
import com.cafe.erp.member.history.MemberHistorySearchDTO;
import com.cafe.erp.member.history.MemberHistoryService;
import com.cafe.erp.member.history.MemberLoginHistoryDTO;
import com.cafe.erp.security.UserDTO;
import com.cafe.erp.util.ExcelUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberCommuteService commuteService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CompanyHolidayService companyHolidayService;
	
	@Autowired
	private MemberAttendanceDAO memberAttendanceDAO;
	
	@Autowired
	private MemberHistoryService memberHistoryService;
	
	

	MemberController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	
	

	@GetMapping("login")
	public void login() throws Exception {

	}
	
	@GetMapping("member_mypage")
	public void mypage() throws Exception{
		
	}
	
	
	
	@GetMapping("calendar")
	@ResponseBody
	public List<Map<String, Object>> holidayView(@AuthenticationPrincipal UserDTO userDTO) throws Exception{
		List<CompanyHolidayDTO> list = companyHolidayService.selectHolidayAllActive();
		int memberId = userDTO.getMember().getMemberId();
		
		
		List<Map<String, Object>> view = new ArrayList<>();
		for(CompanyHolidayDTO dto : list) {
			Map<String, Object> calendar = new HashMap<>();
			calendar.put("title", dto.getComHolidayName()); // 휴일 이름
			calendar.put("start", dto.getComHolidayDate().toString()); // 휴일 
			
			calendar.put("className", "holiday-event");
			calendar.put("allDay", true);
			view.add(calendar);
		}
		
		List<MemberAttendanceDTO> attList = memberAttendanceDAO.selectApprovedAttendance(memberId);
	    for(MemberAttendanceDTO dto : attList) {
	        Map<String, Object> attMap = new HashMap<>();
	        attMap.put("title", dto.getMemAttendanceType()); // "연차", "오후반차" 등
	        
	        String startDate = dto.getMemAttendanceStartDate().toString().split(" ")[0];
	        attMap.put("start", startDate); 
	        
	        attMap.put("className", "attendance-approved-event"); // CSS 구분용
	        attMap.put("allDay", true);
	        view.add(attMap);
	    }
		
		
		MemberCommuteSearchDTO commuteSearchDTO = new MemberCommuteSearchDTO();
		commuteSearchDTO.setMemberId(memberId);
		
		commuteSearchDTO.setPage(1L);
		commuteSearchDTO.setPerPage(1000L); 
		commuteSearchDTO.setStartNum(0L);
		
		
		List<MemberCommuteDTO> commuteList = commuteService.attendanceList(commuteSearchDTO);
		
		for(MemberCommuteDTO dto : commuteList) {
			String state = dto.getMemCommuteState();
			if(dto.getMemCommuteWorkDate() != null) {
				Map<String, Object> checkIn = new HashMap<>();
				String checkInDate = "";
				if (dto.getMemCommuteInTime() != null) {
					checkInDate = dto.getMemCommuteInTime().toString();
				}
				
				String checkInTime = ""; 

				if (dto.getMemCommuteInTime() != null) {
				    String fullTime = dto.getMemCommuteInTime().toString();
				    if (fullTime.length() >= 16) {
				    	checkInTime = fullTime.substring(11, 16); 
				    } else {
				    	checkInTime = fullTime;
				    }
				}
				
				if ("지각".equals(state) || checkInTime.compareTo("09:00") > 0) {
		            checkIn.put("title", "지각 (" + checkInTime + ")");    
		            checkIn.put("className", "commute-late-event"); 
		        } else {
					checkIn.put("title", "출근 (" + checkInTime + ")") ;
					checkIn.put("className", "commute-go-event");
				}
				checkIn.put("start",checkInDate );
				view.add(checkIn);
			}
			
			if (dto.getMemCommuteOutTime() != null) {
                Map<String, Object> checkout = new HashMap<>();
                String checkOutDate = "";
				if (dto.getMemCommuteOutTime() != null) {
					checkOutDate = dto.getMemCommuteOutTime().toString();
				}
				
				String checkOutTime = ""; 

				if (dto.getMemCommuteOutTime() != null) {
				    String fullTime = dto.getMemCommuteOutTime().toString();
				    if (fullTime.length() >= 16) {
				    	checkOutTime = fullTime.substring(11, 16); 
				    } else {
				    	checkOutTime = fullTime;
				    }
				}
                
                
                if("조퇴".equals(state)) {
                	checkout.put("title", "조퇴 (" + checkOutTime + ")");
                	checkout.put("className", "commute-late-event");
					
				} else {
					checkout.put("title", "퇴근 (" + checkOutTime + ")");
					checkout.put("className", "commute-leave-event");
					
					
				}
                checkout.put("start",checkOutDate );
				view.add(checkout);
			}
		}
		return view;
	}
	
	

	@GetMapping("AM_group_chart")
	public String chatList(Model model, @AuthenticationPrincipal UserDTO userDTO)throws Exception {
		// 로그인 한 유저 확인
		int memberId = userDTO.getMember().getMemberId();

		
		// 초기 조직도 목록
		Map<String, Object> startChart = new HashMap<>();
	    startChart.put("deptCode", 0);
	    startChart.put("includeRetired", false);
	    startChart.put("keyword", "");
	    List<Map<String, Object>> deptCount = memberService.deptMemberCount(startChart);

	    // 전체 활성 사원 수(상단 표시용)
	    int totalCount = memberService.countActiveMember(new MemberSearchDTO());

	    GroupChartSearchDTO dto = new GroupChartSearchDTO();
	    dto.setDeptCode(0);
	    dto.setIncludeRetired(false);
	    dto.setKeyword("");
	    dto.setPage(1L);          // 첫 페이지

	    Map<String, Object> paged = memberService.chatList(dto);

	    model.addAttribute("deptCount", deptCount);
	    model.addAttribute("totalCount", totalCount);

	    model.addAttribute("startViewChart", paged.get("memberList"));
	    model.addAttribute("pager", paged.get("pager"));

	    return "member/AM_group_chart";
	}

	
	
	
	

	@GetMapping("checkCount")
	@ResponseBody
	public Map<String, Object> checkMemberCount( @RequestParam(name = "deptCode", defaultValue = "0") int deptCode,
	        @RequestParam(name = "includeRetired", defaultValue = "false") boolean includeRetired,
	        @RequestParam(defaultValue = "") String keyword,
	        @RequestParam(name = "page", defaultValue = "1") Long page
	) throws Exception {

	    GroupChartSearchDTO dto = new GroupChartSearchDTO();
	    dto.setDeptCode(deptCode);
	    dto.setIncludeRetired(includeRetired);
	    dto.setKeyword(keyword);
	    dto.setPage(page);


	    Map<String, Object> result = memberService.chatList(dto);

	    Map<String, Object> checkMem = new HashMap<>();
	    checkMem.put("deptCode", deptCode);
	    checkMem.put("includeRetired", includeRetired);
	    checkMem.put("keyword", keyword);

	    List<Map<String, Object>> deptCounts = memberService.deptMemberCount(checkMem);
	    result.put("deptCounts", deptCounts);

	    return result;
	}
	
	
	@GetMapping("admin_member_list")
	public String list( Model model, MemberSearchDTO memberSearchDTO, @AuthenticationPrincipal UserDTO user) throws Exception {
		/* List<MemberDTO> list = memberService.list(memberDTO); */
		
		boolean isMaster = user.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_MASTER")
			               || a.getAuthority().equals("ROLE_DEPT_HR"));

	    if (!isMaster) return "error/403"; 
	    
		List<MemberDTO> list = memberService.getMemberList(memberSearchDTO); 
		
		model.addAttribute("list", list);
		model.addAttribute("pager", memberSearchDTO);
		
		List<MemberDTO> deptList = memberService.deptList();
	    model.addAttribute("deptList", deptList);
	    
	    List<MemberDTO> positionList = memberService.positionList();
	    model.addAttribute("positionList", positionList);

		int totalCount = memberService.countAllMember(memberSearchDTO); // 전체(퇴사 포함)
		int activeCount = memberService.countActiveMember(memberSearchDTO); // 재직자만

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("activeCount", activeCount);

		return "member/admin_member_list";
	}
	
	

	@PostMapping("admin_member_add")
	public String add(MemberDTO memberDTO, Model model) throws Exception {
		String pw = "1234";
		memberDTO.setMemPassword(pw);

		int result = memberService.add(memberDTO);

		String msg = "등록 성공";

		int memid = memberDTO.getMemberId();

		if (result == 0) {
			msg = "등록 실패";
			model.addAttribute("msg", msg);
			model.addAttribute("url", "./admin_member_list");
			return "redirect:./admin_member_list";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", "./AM_user_detail");
		emailService.sendPasswordEmail(memberDTO.getMemEmail(), pw, memberDTO.getMemName());
		return "redirect:./AM_member_detail?memberId=" + memid;
	}
	
	

	@GetMapping("AM_member_detail")
	public String detail(MemberDTO memberDTO, Model model, 
	                     @AuthenticationPrincipal UserDTO userDTO, 
	                     MemberCommuteSearchDTO memberCommuteSearchDTO,
	                     @ModelAttribute("vacationSearch") MemberAttendanceSearchDTO memberAttendanceSearchDTO,
	                     @RequestParam(value = "aPage", defaultValue = "1") Long aPage,
	                     @RequestParam(value = "vPage", defaultValue = "1") Long vPage, 
	                     @RequestParam(value = "tab", required = false) String tab  ) throws Exception {
	    
	    int targetMemberId = memberDTO.getMemberId();
	    if (targetMemberId == 0) {
	        targetMemberId = userDTO.getMember().getMemberId();
	        memberDTO.setMemberId(targetMemberId); 
	    }

	    MemberDTO member = memberService.detail(memberDTO);
	    model.addAttribute("dto", member);
	    model.addAttribute("deptList", memberService.deptList());
	    model.addAttribute("positionList", memberService.positionList());

	    if (member != null) {
	        memberCommuteSearchDTO.setMemberId(targetMemberId);
	        
	        
	        memberCommuteSearchDTO.setPage(aPage == null || aPage < 1 ? 1L : aPage);
	        
	        Long perPage = memberCommuteSearchDTO.getPerPage();
	        if (perPage == null || perPage < 1) perPage = 10L;
	        if (perPage > 1000L) perPage = 1000L;        
	        memberCommuteSearchDTO.setPerPage(perPage);

	        String dateType = memberCommuteSearchDTO.getDateType();
	        
	        if (dateType == null || "month".equals(dateType) || dateType.isEmpty()) {
	            String mDate = memberCommuteSearchDTO.getMonthDate();

	            if (mDate == null || mDate.isEmpty()) {
	                mDate = YearMonth.now().toString(); 
	                memberCommuteSearchDTO.setMonthDate(mDate);
	            }

	            YearMonth ym = YearMonth.parse(mDate); 
	            memberCommuteSearchDTO.setStartDate(ym.atDay(1).toString());
	            memberCommuteSearchDTO.setEndDate(ym.atEndOfMonth().toString());
	            memberCommuteSearchDTO.setDateType("month");
	        }
	        else if ("year".equals(dateType)) {
	            String yDate = memberCommuteSearchDTO.getYearDate();
	            if (yDate == null || yDate.isEmpty()) {
	                yDate = String.valueOf(java.time.LocalDate.now().getYear());
	                memberCommuteSearchDTO.setYearDate(yDate);
	            }
	            memberCommuteSearchDTO.setStartDate(yDate + "-01-01");
	            memberCommuteSearchDTO.setEndDate(yDate + "-12-31");
	        } 
	        else if ("all".equals(dateType)) {
	            memberCommuteSearchDTO.setStartDate(null);
	            memberCommuteSearchDTO.setEndDate(null);
	        } 
	        
	        
	        Long totalAttendanceCount = commuteService.countAttendance(memberCommuteSearchDTO);
	        memberCommuteSearchDTO.pageing(totalAttendanceCount);
	        memberCommuteSearchDTO.setStartNum((memberCommuteSearchDTO.getPage() - 1) * memberCommuteSearchDTO.getPerPage());


	        List<MemberCommuteDTO> attendanceList = commuteService.attendanceList(memberCommuteSearchDTO);
	        
	        model.addAttribute("attendanceList", attendanceList);
	        
	        model.addAttribute("pager", memberCommuteSearchDTO); 
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        memberAttendanceSearchDTO.setMemberId(targetMemberId);
	        memberAttendanceSearchDTO.setPage(vPage == null || vPage < 1 ? 1L : vPage);

	        Long vPerPage = memberAttendanceSearchDTO.getPerPage();
	        if (vPerPage == null || vPerPage < 1) vPerPage = 10L;
	        if (vPerPage > 500L) vPerPage = 500L;

	        String vYear = memberAttendanceSearchDTO.getYearDate();
	        if (vYear == null || vYear.isEmpty()) {
	            vYear = String.valueOf(java.time.Year.now().getValue());
	            memberAttendanceSearchDTO.setYearDate(vYear);
	        }
	        
	        memberAttendanceSearchDTO.setStartDate(vYear + "-01-01");
	        memberAttendanceSearchDTO.setEndDate(vYear + "-12-31");

	        Long totalVacationCount = memberAttendanceDAO.countAttendanceList(memberAttendanceSearchDTO);
	        memberAttendanceSearchDTO.pageing(totalVacationCount);

	        List<MemberAttendanceDTO> vacationList = memberAttendanceDAO.attendanceList(memberAttendanceSearchDTO);
	        
	        model.addAttribute("vacationList", vacationList);
	        
	        model.addAttribute("stats", memberAttendanceDAO.selectLeaveStats(targetMemberId));
	    
	    }

	    return "member/AM_member_detail";
	}
	
	
	
	
	@GetMapping("admin_login_history")
    public String loginHistory(Model model, MemberHistorySearchDTO memberHistorySearchDTO, @AuthenticationPrincipal UserDTO user) throws Exception{
		
		boolean isMaster = user.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_MASTER"));

	    if (!isMaster) return "error/403"; 
		
		/* 최근 3개월 이력 출력 */
		if (memberHistorySearchDTO.getStartDate() == null || memberHistorySearchDTO.getStartDate().equals("")) {
	        LocalDate today = LocalDate.now();
	        LocalDate threeMonthsAgo = today.minusMonths(3);
	        
	        memberHistorySearchDTO.setStartDate(threeMonthsAgo.toString()); 
	        memberHistorySearchDTO.setEndDate(today.toString());          
	    }
		
		if (memberHistorySearchDTO.getPage() == null || memberHistorySearchDTO.getPage() < 1) memberHistorySearchDTO.setPage(1L);
	    if (memberHistorySearchDTO.getPerPage() == null || memberHistorySearchDTO.getPerPage() < 1) memberHistorySearchDTO.setPerPage(10L);
	    
	    Long totalCount = memberHistoryService.totalCount(memberHistorySearchDTO);
	    memberHistorySearchDTO.setTotalCount(totalCount);

	    memberHistorySearchDTO.setStartNum((memberHistorySearchDTO.getPage() - 1) * memberHistorySearchDTO.getPerPage());



	    
        List<MemberLoginHistoryDTO> list = memberHistoryService.selectLoginHistoryList(memberHistorySearchDTO);
        
        model.addAttribute("historyList", list);
        model.addAttribute("pager", memberHistorySearchDTO); 
        model.addAttribute("totalCount", memberHistorySearchDTO.getTotalCount());
        
        return "member/admin_login_history";
    }
	
	
	
	
	
	
	@GetMapping("attendance_excel")
	public void attendanceExcel(MemberCommuteSearchDTO dto, HttpServletResponse response) throws Exception {

		
		if (dto.getPage() == null || dto.getPage() < 1) dto.setPage(1L);
	    if (dto.getPerPage() == null || dto.getPerPage() < 1) dto.setPerPage(10L);
	    if (dto.getPerPage() > 200L) dto.setPerPage(200L);

	    dto.setStartNum((dto.getPage() - 1) * dto.getPerPage());

	    List<MemberCommuteDTO> list = commuteService.attendanceList(dto);

	    String[] headers = {"날짜", "출근", "퇴근", "상태", "비고"};
	    ExcelUtil.download(list, headers, "근태내역", response, (row, a) -> {
	        row.createCell(0).setCellValue(a.getMemCommuteWorkDate() == null ? "" : a.getMemCommuteWorkDate().toString());
	        row.createCell(1).setCellValue(a.getFormattedInTime());
	        row.createCell(2).setCellValue(a.getFormattedOutTime());
	        row.createCell(3).setCellValue(a.getMemCommuteState() == null ? "" : a.getMemCommuteState());
	        row.createCell(4).setCellValue(a.getMemCommuteNote() == null ? "" : a.getMemCommuteNote());
	    });
	}
	
	@GetMapping("vacation_excel")
	public void vacationExcel(
	        MemberAttendanceSearchDTO dto,
	        HttpServletResponse response
	) throws Exception {

	    // 기본값
	    if (dto.getPage() == null || dto.getPage() < 1) dto.setPage(1L);
	    if (dto.getPerPage() == null || dto.getPerPage() < 1) dto.setPerPage(10L);
	    if (dto.getPerPage() > 500L) dto.setPerPage(500L);

	    dto.setStartNum((dto.getPage() - 1) * dto.getPerPage());

	    List<MemberAttendanceDTO> list = memberAttendanceDAO.attendanceList(dto);

	    String[] headers = {"기간", "종류", "사용", "사유", "상태"};

	    ExcelUtil.download(list, headers, "휴가내역", response, (row, v) -> {
	        String start = (v.getMemAttendanceStartDate() == null) ? "" : v.getMemAttendanceStartDate().toString();
	        String end   = (v.getMemAttendanceEndDate() == null) ? "" : v.getMemAttendanceEndDate().toString();
	        row.createCell(0).setCellValue(start + " ~ " + end);

	        row.createCell(1).setCellValue(v.getMemAttendanceType() == null ? "" : v.getMemAttendanceType());
	        row.createCell(2).setCellValue(String.valueOf(v.getMemAttendanceUsedDays())); // usedDays 타입이 숫자면 OK
	        row.createCell(3).setCellValue(v.getMemAttendanceReason() == null ? "" : v.getMemAttendanceReason());
	        row.createCell(4).setCellValue(v.getAppStatus() == null ? "" : v.getAppStatus());
	    });
	}


	
	
	@GetMapping("admin_member_excel")
	public void adminMemberExcel(MemberSearchDTO memberSearchDTO, HttpServletResponse response) throws Exception {


	    Long totalCount = memberService.memberCount(memberSearchDTO); // 아래 서비스 추가 (또는 DAO 직접 호출)
	    memberSearchDTO.pageing(totalCount);

	    List<MemberDTO> list = memberService.getMemberList(memberSearchDTO);

	    String[] headers = {"사번", "이름", "부서", "직급", "입사일", "퇴사일", "상태"};

	    ExcelUtil.download(
	        list,
	        headers,
	        "사원목록",
	        response,
	        (row, dto) -> {
	            row.createCell(0).setCellValue(dto.getMemberId());
	            row.createCell(1).setCellValue(dto.getMemName());
	            row.createCell(2).setCellValue(dto.getMemDeptName());
	            row.createCell(3).setCellValue(dto.getMemPositionName());
	            row.createCell(4).setCellValue(dto.getMemHireDate() == null ? "" : dto.getMemHireDate().toString());
	            row.createCell(5).setCellValue(dto.getMemLeftDate() == null ? "" : dto.getMemLeftDate().toString());
	            row.createCell(6).setCellValue(dto.getMemIsActive() ? "재직" : "퇴사");
	        }
	    );
	}

	
	
	
	@GetMapping("admin_login_history_excel")
	public void adminLoginHistoryExcel(MemberHistorySearchDTO dto, HttpServletResponse response) throws Exception {

	    if (dto.getStartDate() == null || dto.getStartDate().equals("")) {
	        LocalDate today = LocalDate.now();
	        LocalDate threeMonthsAgo = today.minusMonths(3);
	        dto.setStartDate(threeMonthsAgo.toString());
	        dto.setEndDate(today.toString());
	    }

	    if (dto.getPage() == null || dto.getPage() < 1) dto.setPage(1L);
	    if (dto.getPerPage() == null || dto.getPerPage() < 1) dto.setPerPage(10L);

	    dto.setStartNum((dto.getPage() - 1) * dto.getPerPage());

	    List<MemberLoginHistoryDTO> list = memberHistoryService.selectLoginHistoryList(dto);

	    String[] headers = {"일시", "접속자(사번)", "로그인 ID(입력값)", "IP 주소", "유형", "결과"};

	    ExcelUtil.download(
	        list,
	        headers,
	        "로그인이력",
	        response,
	        (row, log) -> {
	            row.createCell(0).setCellValue(log.getMemLogHisCreatedTime() == null ? "" : log.getMemLogHisCreatedTime().toString());
	            String name = (log.getMemName() == null ? "-" : log.getMemName());
	            
	            // null일때 다운 안되는 거 방지
	            Integer mid = log.getMemberId();
	            String memberId = (mid == null || mid == 0) ? "-" : String.valueOf(mid);
	            
	            row.createCell(1).setCellValue(name + " (" + memberId + ")");
	            row.createCell(2).setCellValue(log.getMemLogHisLoginId() == null ? "" : log.getMemLogHisLoginId());
	            row.createCell(3).setCellValue(log.getMemLogHisClientIp() == null ? "" : log.getMemLogHisClientIp());
	            row.createCell(4).setCellValue(log.getMemLogHisActionType() == null ? "" : log.getMemLogHisActionType());
	            row.createCell(5).setCellValue(log.isMemLogHisIsSuccess() ? "성공" : "실패");
	        }
	    );
	}

	
	
	

	@PostMapping("member_info_update")
	@ResponseBody
	public String update(MemberDTO memberDTO,
			@RequestParam(value = "profileImage", required = false) MultipartFile file) throws Exception {
		String newFileName = memberService.update(memberDTO, file);
		if (newFileName != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDTO userDTO = (UserDTO) authentication.getPrincipal();
			userDTO.getMember().setMemProfileSavedName(newFileName);
		}

		return "success";
	}

	
	@PostMapping("/updateCommute")
	@ResponseBody
	public Map<String, Object> updateCommute(@RequestBody @Valid MemberCommuteDTO commuteDTO) {
	    
	    Map<String, Object> response = new HashMap<>();
	    try {
	        int result = commuteService.updateCommute(commuteDTO);
	        if (result > 0) {
	            response.put("success", true);
	        } else {
	            response.put("success", false);
	            response.put("message", "변경사항이 없습니다.");
	        }
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", e.getMessage());
	    }
	    return response;
	}
	
	@PreAuthorize("hasAnyRole('MASTER','DEPT_HR')")
	@PostMapping("reset_password")
	@ResponseBody
	public String resetPassword(@RequestParam("memberId") int memberId) throws Exception {

		MemberDTO searchDTO = new MemberDTO();
		searchDTO.setMemberId(memberId);
		MemberDTO targetMember = memberService.detail(searchDTO);

		if (targetMember == null) {
			return "fail";
		}

		String pw = "1234";

		MemberDTO updateDTO = new MemberDTO();
		updateDTO.setMemberId(memberId);
		updateDTO.setMemPassword("1234");

		memberService.resetPw(updateDTO);

		emailService.resetPasswordEmail(targetMember.getMemEmail(), pw, targetMember.getMemName());

		return "success";
	}

	
	
	@PostMapping("changePassword")
	@ResponseBody
	public String changePassword(@Valid MemberChangePasswordDTO changePasswordDTO, BindingResult bindingResult,
			@AuthenticationPrincipal UserDTO userDTO) throws Exception {

		if (bindingResult.hasErrors()) {
			return bindingResult.getFieldError().getDefaultMessage();
		}

		changePasswordDTO.setMemberId(userDTO.getMember().getMemberId());

		int result = memberService.changePassword(userDTO.getMember().getMemberId(), changePasswordDTO);

		if (result == -1) {
			return "현재 비밀번호가 일치하지 않습니다.";
		} else if (result == 2) {
			return "현재 비밀번호와 일치합니다.";
		}
		return result > 0 ? "success" : "fail";
	}
	
	
	@PreAuthorize("hasAnyRole('MASTER','DEPT_HR')")
	@PostMapping("deptNameChange")
	@ResponseBody
	public String updateDeptName(@RequestParam int deptCode,
	                             @RequestParam String deptName) throws Exception {

	    if (deptCode == 0) return "부서를 선택하세요.";
	    if (deptName == null || deptName.trim().isEmpty()) return "부서명을 입력하세요.";

	    int result = memberService.updateDeptName(deptCode, deptName.trim());
	    return result > 0 ? "success" : "fail";
	}


	
	
	
	
	
	
	
	@PostMapping("InActive")
	@ResponseBody
	public String InActive(@RequestParam("memberId") int memberId) throws Exception {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(memberId);
		memberDTO.setMemIsActive(false);
		memberService.InActive(memberDTO);

		return "success";
	}

	
	@GetMapping("sessionCheck")
	@ResponseBody
	public String sessionCheck() {
		return "active";
	}

	
	@GetMapping("/search/owner")
	@ResponseBody
	public List<MemberDTO> searchOwner(@RequestParam String keyword) throws Exception {
		return memberService.searchOwner(keyword);
	}

	
	@GetMapping("/search/manager")
	@ResponseBody
	public List<MemberDTO> searchManager(@RequestParam String keyword) throws Exception {
		return memberService.searchManager(keyword);
	}

}
