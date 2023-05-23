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
public class Android {
    private String ttl;
    private NotificationPriority priority;
    private String restrictedPackageName;
    private AndroidNotification notification;
}
