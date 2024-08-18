package jie;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午7:10
 * @注释
 */

@EnableConfigurationProperties
@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
public class WillFireApplication {

    static {
        System.setProperty("druid.mysql.usePingMethod","false");
    }

    public static void main(String[] args) {
        SecureUtil.disableBouncyCastle();
        SpringApplication.run(WillFireApplication.class, args);
        log.info("服务器启动");
    }

}
