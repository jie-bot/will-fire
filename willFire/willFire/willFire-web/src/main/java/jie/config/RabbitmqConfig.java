package jie.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午4:41
 * @注释 Rabbitmq配置
 */

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue queue() {
        return new Queue("will_fire.email");
    }
}
