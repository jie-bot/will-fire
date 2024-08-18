package jie.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jie.constants.CommonConst;
import jie.dao.WebInfoMapper;
import jie.entity.User;
import jie.entity.WebInfo;
import jie.enums.CodeMsg;
import jie.handle.WillFireRuntimeException;
import jie.service.WebInfoService;
import jie.utils.CommonQuery;
import jie.utils.cache.WillFireCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午6:53
 * @注释
 */
@Service
public class WebInfoServiceImpl extends ServiceImpl<WebInfoMapper, WebInfo> implements WebInfoService {

    @Autowired
    private WebInfoMapper webInfoMapper;
    @Autowired
    private CommonQuery commonQuery;

    /**
     * 获取网站信息
     * @return
     */
    @Override
    public WebInfo getWebInfo() {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo == null) {
            // 从数据库里面获取
            LambdaQueryChainWrapper<WebInfo> wrapper = new LambdaQueryChainWrapper<>(webInfoMapper);
            List<WebInfo> list = wrapper.list();
            if (!CollectionUtils.isEmpty(list)) {
                list.get(0).setDefaultStoreType(CommonConst.defaultStoreType);
                webInfo = list.get(0);
            }
        }

        if (webInfo == null) {
            throw new WillFireRuntimeException(CodeMsg.GET_WEB_INFO_ERROR);
        }

        webInfo.setRandomAvatar(null);
        webInfo.setRandomName(null);
        webInfo.setWaifuJson(null);
        webInfo.setHistoryAllCount(((Long) ((Map<String, Object>) WillFireCache
                .get(CommonConst.IP_HISTORY_STATISTICS))
                .get(CommonConst.IP_HISTORY_COUNT))
                .toString());
        webInfo.setHistoryDayCount(Integer.toString(((List<Map<String, Object>>) ((Map<String, Object>)
                WillFireCache.get(CommonConst.IP_HISTORY_STATISTICS))
                .get(CommonConst.IP_HISTORY_HOUR)).size()));
        return webInfo;
    }

    /**
     * 获取看板娘的消息
     * @return
     */
    @Override
    public String getWaifuJson() {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo == null) {
            // 从数据库里面获取
            LambdaQueryChainWrapper<WebInfo> wrapper = new LambdaQueryChainWrapper<>(webInfoMapper);
            List<WebInfo> list = wrapper.list();
            if (!CollectionUtils.isEmpty(list)) {
                webInfo = list.get(0);
            }
        }

        if (webInfo == null) {
            throw new WillFireRuntimeException(CodeMsg.GET_WEB_INFO_ERROR);
        }
        if (!StringUtils.hasText(webInfo.getWaifuJson())) {
            return "{}";
        }
        return webInfo.getWaifuJson();
    }

    @Override
    public List<User> getAdmire() {
        List<User> list = commonQuery.getAdmire();
        return list;
    }

}
