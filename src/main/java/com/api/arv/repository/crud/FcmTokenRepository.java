package com.api.arv.repository.crud;


import com.api.arv.model.document.FcmToken;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface FcmTokenRepository extends MongoRepository<FcmToken, String> {
    FcmToken findByUserId(String id);
}
