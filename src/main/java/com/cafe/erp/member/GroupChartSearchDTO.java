package com.cafe.erp.member;

import com.cafe.erp.util.Pager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GroupChartSearchDTO extends Pager{

	private Integer deptCode;          
    private Boolean includeRetired;    
    private String keyword;            

    private Long totalCount;
}
