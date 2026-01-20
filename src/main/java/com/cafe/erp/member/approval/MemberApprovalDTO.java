package com.cafe.erp.member.approval;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberApprovalDTO {
	
	private String approvalId;
	private LocalDateTime appDraftDate;
	private String appStatus;
	private String appTitle;
	private String appContent;
	private LocalDateTime appCreatedAt;
	private LocalDateTime appUpdatedAt;
	private Boolean appIsActive;
	private int memberId;

}
