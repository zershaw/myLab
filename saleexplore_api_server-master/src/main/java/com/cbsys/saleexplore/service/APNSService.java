package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IAPNSService;
import com.cbsys.saleexplore.config.AppProperties;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import com.turo.pushy.apns.util.concurrent.PushNotificationResponseListener;
import io.netty.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class APNSService implements IAPNSService {

    @Autowired
    private AppProperties appProperties;

    private ApnsClient apnsClient;


    public APNSService(AppProperties appProperties){
        /**
         * initialize the apns clients
         * https://github.com/relayrides/pushy
         * http://relayrides.github.io/pushy/
         * https://blog.csdn.net/qq_28483283/article/details/80514161
         */
        try{
            String rName = appProperties.getIos().getPushCertificatePath();

            if(appProperties.isDebug()){
                apnsClient = new ApnsClientBuilder()
                        .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                        .setClientCredentials(new ClassPathResource(rName).getInputStream(),
                                appProperties.getIos().getPushCertificatePd()).build();
            }else{
                apnsClient = new ApnsClientBuilder()
                        .setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
                        .setClientCredentials(new ClassPathResource(rName).getInputStream(),
                                appProperties.getIos().getPushCertificatePd()).build();
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void pushNotification(String msg, String token) {

        // TODO according to

        ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
        payloadBuilder.setAlertBody("Example!");

        String payload = payloadBuilder.buildWithDefaultMaximumLength();

        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, "Test notification", payload);

        PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
                sendNotificationFuture = apnsClient.sendNotification(pushNotification);

        sendNotificationFuture.addListener(new PushNotificationResponseListener<SimpleApnsPushNotification>() {

            @Override
            public void operationComplete(final PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> future) throws Exception {
                // When using a listener, callers should check for a failure to send a
                // notification by checking whether the future itself was successful
                // since an exception will not be thrown.
                if (future.isSuccess()) {
                    final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                            sendNotificationFuture.getNow();

                    // Handle the push notification response as before from here.
                } else {
                    // Something went wrong when trying to send the notification to the
                    // APNs gateway. We can find the exception that caused the failure
                    // by getting future.cause().
                    future.cause().printStackTrace();
                }
            }
        });

        Future<Void> closeFuture = apnsClient.close();
        try {
            closeFuture.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
