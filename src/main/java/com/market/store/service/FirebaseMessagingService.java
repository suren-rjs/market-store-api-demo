package com.market.store.service;

import com.google.firebase.messaging.*;
import com.market.store.model.dto.request.notification.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@SuppressWarnings("unused")
@Service
public class FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;


    public String sendNotification(NotificationMessage notificationMessage, String topic) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getSubject())
                .setBody(notificationMessage.getContent())
                .setImage(notificationMessage.getImage())
                .build();

        Message message = Message
                .builder()
                .setTopic(topic)
                .setNotification(notification)
                .putAllData(notificationMessage.getData())
                .build();

        return firebaseMessaging.send(message);
    }

}