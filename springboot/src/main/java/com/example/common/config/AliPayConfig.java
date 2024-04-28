package com.example.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AliPayConfig {

    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;

}
