package com.nos.home.common.sms;


import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;

public class SmsService {

    @Value("${com.nos.home.sms.url}")
    private String smsUrl;

    @Value("${com.nos.home.sms.api-key}")
    private String apiKey;

    @Value("${com.nos.home.sms.api-secret}")
    private String apiSecret;

    private String senderPhoneNumber = "01032002213";

    final DefaultMessageService messageService;

    public SmsService() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, smsUrl);
    }

    public SingleMessageSentResponse sendOne(String targetPhoneNumber, String messageText) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(senderPhoneNumber);
        message.setTo(targetPhoneNumber);
        message.setText(messageText);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}
