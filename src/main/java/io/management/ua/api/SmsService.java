package io.management.ua.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:sms.properties")
public class SmsService {
    @Value("${sms.token}")
    private String token;
    @Value("${sms.api.url}")
    private String apiUrl;

}
