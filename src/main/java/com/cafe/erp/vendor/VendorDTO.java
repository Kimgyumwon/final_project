package com.cafe.erp.vendor;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VendorDTO {
	@NotNull(message = "vendorId는 필수입니다")
	private int vendorId;
	
	private Integer vendorCode;
	private String vendorName;
	private String vendorBusinessNumber;
	private String vendorAddress;
	private String vendorCeoName;
	private String vendorBusinessType;
	private String vendorManagerTel;
	private String vendorManagerName;
	private String vendorManagerEmail;
	private Date vendorCreatedAt;
	private int memberId;
	
}
