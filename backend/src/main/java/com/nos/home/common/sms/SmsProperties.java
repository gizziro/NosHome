package com.nos.home.common.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    private String apiKey;
    private String apiSecret;
    private String baseUrl;
    private String sender;
}
