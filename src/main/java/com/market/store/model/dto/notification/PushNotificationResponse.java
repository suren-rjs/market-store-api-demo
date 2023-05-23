package com.market.store.model.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PushNotificationResponse {
    private Double multicast_id;
    private Long success;
    private Long failure;
    private Long canonical_ids;
    private List<PushNotificationResult> results;
}
