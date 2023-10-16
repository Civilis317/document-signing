package net.playground.documentsigningservice.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties("signature")
public class SignatureConfig {

    private String creator;
    private String reason;
    private String location;

}
