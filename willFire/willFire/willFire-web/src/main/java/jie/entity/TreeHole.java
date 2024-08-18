package jie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tree_hole")
public class TreeHole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 留言
     */
    @TableField("message")
    private String message;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
