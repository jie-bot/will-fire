package jie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jie.entity.User;
import jie.entity.WebInfo;

import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午6:53
 * @注释 获取网站信息
 */
public interface WebInfoService extends IService<WebInfo> {
    WebInfo getWebInfo();

    String getWaifuJson();

    List<User> getAdmire();
}
