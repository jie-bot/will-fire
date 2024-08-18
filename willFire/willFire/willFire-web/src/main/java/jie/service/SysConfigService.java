package jie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jie.entity.SysConfig;

import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:07
 * @注释
 */
public interface SysConfigService extends IService<SysConfig> {
    Map<String, String> listSysConfig();
}
