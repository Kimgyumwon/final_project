package com.cafe.erp.member.approval;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.erp.member.attendance.MemberAttendanceDAO;
import com.cafe.erp.member.attendance.MemberAttendanceDTO;
import com.cafe.erp.security.UserDTO;

@Service
public class MemberApprovalService {
	
	@Autowired
	MemberApprovalDAO approvalDAO;
	
	@Autowired
	MemberAttendanceDAO memberAttendanceDAO;
	
	@Transactional
	public void autoApproval(MemberAttendanceDTO memberAttendanceDTO, UserDTO userDTO) throws Exception{
		
		String approvalId = UUID.randomUUID().toString();
		
		
		memberAttendanceDTO.setMemAttendanceUsedDays(calcUsedDays(memberAttendanceDTO));
		MemberApprovalDTO memberApprovalDTO = new MemberApprovalDTO();
		memberApprovalDTO.setApprovalId(approvalId);
		memberApprovalDTO.setMemberId(userDTO.getMember().getMemberId());
		memberApprovalDTO.setAppStatus("승인");
		memberApprovalDTO.setAppTitle("[휴가] " + memberAttendanceDTO.getMemAttendanceType()); 
		memberApprovalDTO.setAppContent(memberAttendanceDTO.getMemAttendanceReason()); 

	    approvalDAO.insertApprovalAuto(memberApprovalDTO);

	    memberAttendanceDTO.setMemberId(userDTO.getMember().getMemberId());

	    memberAttendanceDTO.setApprovalId(approvalId);                     


	    memberAttendanceDAO.insertAttendance(memberAttendanceDTO);
	}
	
	private BigDecimal calcUsedDays(MemberAttendanceDTO dto) {
	    String type = dto.getMemAttendanceType();

	    if ("오전반차".equals(type) || "오후반차".equals(type) || "반차".equals(type)) {
	        return new BigDecimal("0.5");
	    }

	    // 연차: 날짜 차이 + 1 (시작/종료 포함)
	    LocalDate s = dto.getMemAttendanceStartDate().toLocalDate();
	    LocalDate e = dto.getMemAttendanceEndDate().toLocalDate();
	    long days = ChronoUnit.DAYS.between(s, e) + 1;
	    return BigDecimal.valueOf(days);
	}

}
