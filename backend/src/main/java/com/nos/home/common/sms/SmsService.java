package com.nos.home.common.sms;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {
    private final SmsProperties smsProperties;

    private DefaultMessageService messageService;

    @PostConstruct
    public void initialize() {
        this.messageService = NurigoApp.INSTANCE.initialize(smsProperties.getApiKey(), smsProperties.getApiSecret(), smsProperties.getBaseUrl());
        log.info("Initializing SMS Client with:");
        log.info("API Key: {}", smsProperties.getApiKey());
        log.info("API Secret: {}", smsProperties.getApiSecret());
        log.info("Base URL: {}", smsProperties.getBaseUrl());
        // NurigoApp 초기화
//        sendOne("01032002213", "SMS 서비스가 정상 작동 중입니다.");
    }

    public SingleMessageSentResponse sendOne(String targetPhoneNumber, String messageText) {

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(smsProperties.getSender());
        message.setTo(targetPhoneNumber);
        message.setText(messageText);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}
