package jie.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import jie.utils.WillFireUtil;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:30
 * @注释 自动填充字段
 */

@Component
public class DataAutoFill implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", String.class, !StringUtils.hasText(WillFireUtil.getUsername()) ? "Jie" : WillFireUtil.getUsername());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateBy", String.class, !StringUtils.hasText(WillFireUtil.getUsername()) ? "Jie" : WillFireUtil.getUsername());
    }
}
