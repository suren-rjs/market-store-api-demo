package com.market.store.model.dto.notification;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PushNotificationRequest {
    @NonNull
    private String to;
    private String collapseKey;
    @NonNull
    private Notification notification;
    private Android android;
}
