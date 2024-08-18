package jie.handle;

import com.alibaba.fastjson.JSONArray;
import jie.utils.mail.MailUtil;
import jie.utils.mail.RabEmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午4:28
 * @注释 监听者
 */
@Component
@Slf4j
public class Listener {

    @Autowired
    private MailUtil mailUtil;

    @RabbitListener(queues = "will_fire.email")//监听的消息队列
    public void sendEmail(String message) {
        log.info(message);
        RabEmailMessage rabEmailMessage = JSONArray.parseObject(message, RabEmailMessage.class);
        mailUtil.sendMailMessage(rabEmailMessage.getTo(),rabEmailMessage.getSubject(),rabEmailMessage.getText());
    }

//    @RabbitListener(queues = "will_fire.email")
//    public void test(String message) {
//        log.info(message);
//    }
}
