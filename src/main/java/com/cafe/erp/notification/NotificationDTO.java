package com.cafe.erp.notification;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class NotificationDTO {
    private Long notificationId;
    private String notificationType;
    private String notificationTitle;
    private String notificationContent;
    private String notificationLink;
    private String notificationReadYn;
    private LocalDateTime notificationCreatedAt;
    private Integer senderMemberId;
    private Integer receiverMemberId;
}
