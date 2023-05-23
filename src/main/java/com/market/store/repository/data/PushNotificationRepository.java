package com.market.store.repository.data;

import com.market.store.model.dto.notification.Notification;
import com.market.store.model.dto.notification.PushNotificationResponse;

@SuppressWarnings({"unused"})
public interface PushNotificationRepository {
    PushNotificationResponse sendPushNotification(Notification notification, String delayTime);
}
