package com.market.store.model.dto.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.market.store.model.enums.ClickAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @JsonIgnore
    ClickAction clickAction = ClickAction.OPEN_ACTIVITY_1;
    @NonNull
    private String body;
    @NonNull
    private String title;
    private String image;
    private String fcmToken;
    private Android android;
}
