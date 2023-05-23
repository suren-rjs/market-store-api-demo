package com.market.store.model.dto.notification;

import com.market.store.model.enums.NotificationPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AndroidNotification {
    private String body;
    private String title;
    private String image;
    private String sound;
    private String clickAction;
    private NotificationPriority notificationPriority;
}