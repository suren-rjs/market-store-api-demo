package com.market.store.repository.crud;


import com.market.store.model.document.FcmToken;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface FcmTokenRepository extends MongoRepository<FcmToken, String> {
    FcmToken findByUserId(String id);
}
