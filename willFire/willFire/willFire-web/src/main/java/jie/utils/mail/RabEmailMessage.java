package jie.utils.mail;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午4:07
 * @注释
 */
@Data
public class RabEmailMessage implements Serializable {
    private List<String> to;
    private String subject;
    private String text;
}
