package com.market.store.service;

import com.market.store.helper.GlobalCommonService;
import com.market.store.model.document.FcmToken;
import com.market.store.model.document.User;
import com.market.store.model.dto.notification.*;
import com.market.store.model.enums.NotificationPriority;
import com.market.store.repository.crud.FcmTokenRepository;
import com.market.store.repository.crud.UserRepository;
import com.market.store.repository.data.FcmTokenDtoRepository;
import com.market.store.repository.data.PushNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("All")
@Service
@Transactional
public class NotificationService implements PushNotificationRepository, FcmTokenDtoRepository {

    @Value("${server-key}")
    private String CLOUD_SERVER_KEY;

    @Value("${collapse-key}")
    private String COLLAPSE_KEY;


    @Value("${notification-end-point}")
    private String NOTIFICATION_SERVICE_END_POINT_URI;

    @Value("${android-package-name}")
    private String APP_PACKAGE_NAME;

    @Autowired
    private FcmTokenRepository fcmTokenRepository;

    @Autowired
    private GlobalCommonService globalCommonService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private RestTemplate template;

    @Override
    public PushNotificationResponse sendPushNotification(Notification notification, String delayTime) {
        String fcmToken = notification.getFcmToken();
        //  Push notification body
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(fcmToken, notification);
        pushNotificationRequest.setCollapseKey(COLLAPSE_KEY);

        Android android = new Android();
        AndroidNotification androidNotification = new AndroidNotification();

        android.setNotification(androidNotification);
        pushNotificationRequest.setAndroid(android);

        //  Custom android specifications
        android.setTtl(delayTime);
        android.setPriority(NotificationPriority.HIGH);
        android.setRestrictedPackageName(APP_PACKAGE_NAME);

        //  Push notification body
        androidNotification.setNotificationPriority(NotificationPriority.PRIORITY_HIGH);
        androidNotification.setTitle(notification.getTitle());
        androidNotification.setBody(notification.getBody());
        androidNotification.setImage(notification.getImage());
        androidNotification.setClickAction(notification.getClickAction().toString());
        androidNotification.setSound("default");


        //  Push notification header
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + CLOUD_SERVER_KEY);
        HttpEntity<PushNotificationRequest> pushNotificationRequestHttpEntity = new HttpEntity<>(pushNotificationRequest, headers);

        //  Get Response
        return template.postForObject(NOTIFICATION_SERVICE_END_POINT_URI, pushNotificationRequestHttpEntity, PushNotificationResponse.class);
    }

    @Override
    public void save(FcmTokenDto fcmTokenDto) {
        User user = globalCommonService.getCurrentUser();
        FcmToken fcmToken = new FcmToken(fcmTokenDto.getValue(), user.getId());
        try {
            fcmTokenRepository.save(fcmToken);
        } catch (Exception e) {
            this.update(fcmTokenDto);
        }
    }

    @Override
    public void update(FcmTokenDto fcmTokenDto) {
        User user = globalCommonService.getCurrentUser();
        FcmToken existingToken = this.findByUserId(user.getId());
        existingToken.setValue(fcmTokenDto.getValue());
        fcmTokenRepository.save(existingToken);
    }

    @Override
    public FcmToken findByUserId(String id) {
        return fcmTokenRepository.findByUserId(id);
    }
}
