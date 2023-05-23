package com.market.store.repository.data;

import com.market.store.model.document.FcmToken;
import com.market.store.model.dto.notification.FcmTokenDto;

@SuppressWarnings("unused")
public interface FcmTokenDtoRepository {
    void save(FcmTokenDto fcmTokenDto);

    void update(FcmTokenDto fcmTokenDto);

    FcmToken findByUserId(String userId);
}
