package com.cafe.erp.store.qsc.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class QscDTO {

    private Integer qscId;
    private Integer storeId;
    private Integer memberId;
    private String qscTitle;
    private LocalDateTime qscDate;
    private Integer qscQuestionTotalScore;
    private Integer qscTotalScore;
    private String qscGrade;
    private String qscOpinion;
    private QscDetailDTO[] qscDetailDTOS;

    // store table column
    private String storeName;

    // member table column
    private String memName;

}
