package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.entity.SysConfig;
import jie.enums.CodeMsg;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:05
 * @注释
 */
@RequestMapping("/sysConfig")
@RestController
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 获取系统参数
     * @return
     */
    @GetMapping("/listSysConfig")
    public WillFireResult<Map<String, String>> listSysConfig() {
        Map<String, String> map = sysConfigService.listSysConfig();
        return WillFireResult.success(map);
    }



    /**
     * 保存参数
     * @param sysConfig
     * @return
     */
    @PostMapping("/saveOrUpdateConfig")
    @LoginCheck(0)
    public WillFireResult saveConfig(@RequestBody SysConfig sysConfig) {
        if (!StringUtils.hasText(sysConfig.getConfigName()) ||
                !StringUtils.hasText(sysConfig.getConfigKey()) ||
                !StringUtils.hasText(sysConfig.getConfigType())) {
            throw new WillFireRuntimeException(CodeMsg.SYS_CONFIG_ERROR);
        }
        String configType = sysConfig.getConfigType();
        if (!Integer.toString(WillFireEnum.SYS_CONFIG_PUBLIC.getCode()).equals(configType) &&
                !Integer.toString(WillFireEnum.SYS_CONFIG_PRIVATE.getCode()).equals(configType)) {
            throw new WillFireRuntimeException(CodeMsg.SYS_CONFIG_TYPE_ERROR);
        }
        sysConfigService.saveOrUpdate(sysConfig);
        return WillFireResult.success();
    }

    /**
     * 删除配置
     * @param id
     * @return
     */
    @GetMapping("/deleteConfig")
    @LoginCheck(0)
    public WillFireResult deleteConfig(@RequestParam("id") Integer id) {
        sysConfigService.removeById(id);
        return WillFireResult.success();
    }

    /**
     * 获取配置参数
     * @return
     */
    @GetMapping("/listConfig")
    @LoginCheck(0)
    public WillFireResult<List<SysConfig>> listConfig() {
        return WillFireResult.success(new LambdaQueryChainWrapper<>(sysConfigService.getBaseMapper()).list());
    }
}
