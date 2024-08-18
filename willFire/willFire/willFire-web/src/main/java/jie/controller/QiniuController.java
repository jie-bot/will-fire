package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.utils.storage.QiniuFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/14 下午11:07
 * @注释
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuController {

//    @Resource(name = CommonConst.FILE_OSS)

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取覆盖凭证，用于七牛云
     */
    @GetMapping("/getUpToken")
    @LoginCheck
    @SaveCheck
    public WillFireResult<String> getUpToken(@RequestParam(value = "key") String key) {
        QiniuFileStorageService qiniuUtil = applicationContext.getBean(CommonConst.FILE_OSS, QiniuFileStorageService.class);
        return WillFireResult.success(qiniuUtil.getToken(key));
    }

}
