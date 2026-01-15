package com.cafe.erp.receivable.hq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HqPayableTotalSummaryDTO {

    private Long totalUnpaidAmount;

    private Integer payableCount;
}
