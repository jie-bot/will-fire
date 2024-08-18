package jie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jie.dao.SysConfigMapper;
import jie.entity.SysConfig;
import jie.enums.WillFireEnum;
import jie.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:08
 * @注释
 */

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public Map<String, String> listSysConfig() {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<SysConfig>();
        queryWrapper.eq(SysConfig::getConfigType, Integer.toString(WillFireEnum.SYS_CONFIG_PUBLIC.getCode()));
        List<SysConfig> list = sysConfigMapper.selectList(queryWrapper);
        Map<String, String> map = new HashMap<>();
        for (SysConfig sysConfig : list) {
            map.put(sysConfig.getConfigKey(), sysConfig.getConfigValue());
        }
        return map;
    }
}
