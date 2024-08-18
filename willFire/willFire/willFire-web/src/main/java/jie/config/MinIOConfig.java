package jie.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午11:00
 * @注释
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOConfig {

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }
}
