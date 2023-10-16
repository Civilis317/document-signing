package net.playground.documentsigningservice.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties("keystore")
public class KeyStoreConfig {

    private String keystorePath;
    private String keystorePwd;
    private String keypairAlias;
    private String keypairPwd;

    /**
     *   keystore-path: ${KEYSTORE_PATH}
     *   keystore-pwd: ${KEYSTORE_PWD}
     *   keypair-alias: ${KEYPAIR_ALIAS}
     *   keypair-pwd: ${KEYPAIR_PWD}
     */
}
