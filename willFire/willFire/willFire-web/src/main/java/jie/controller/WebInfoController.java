package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.HistoryInfoMapper;
import jie.entity.HistoryInfo;
import jie.entity.User;
import jie.entity.WebInfo;
import jie.service.WebInfoService;
import jie.utils.CommonQuery;
import jie.utils.cache.WillFireCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午6:50
 * @注释 获取网站信息
 */
@RequestMapping("/webInfo")
@RestController
public class WebInfoController {

    @Autowired
    private HistoryInfoMapper historyInfoMapper;

    @Autowired
    private WebInfoService webInfoService;

    @Autowired
    private CommonQuery commonQuery;

    /**
     * 获取网站信息
     * @return
     */
    @GetMapping("/getWebInfo")
    public WillFireResult<WebInfo> getWebInfo() {
        WebInfo webInfo = webInfoService.getWebInfo();
        return WillFireResult.success(webInfo);
    }

    /**
     * 获取看板娘的消息
     * @return
     */
    @GetMapping("/getWaifuJson")
    public String getWaifuJson() {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo != null && StringUtils.hasText(webInfo.getWaifuJson())) {
            return webInfo.getWaifuJson();
        }
        return "{}";
    }

    /**
     * 获取赞赏
     * @return
     */
    @GetMapping("/getAdmire")
    public WillFireResult<List<User>> getAdmire() {
        List<User> list = webInfoService.getAdmire();
        return WillFireResult.success(list);
    }

    /**
     * 获取网站统计的历史信息
     * @return
     */
    @LoginCheck(0)
    @GetMapping("getHistoryInfo")
    public WillFireResult<Map<String, Object>> getHistoryInfo() {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> history = (Map<String, Object>) WillFireCache.get(CommonConst.IP_HISTORY_STATISTICS);
        List<HistoryInfo> infoList = new LambdaQueryChainWrapper<>(historyInfoMapper)
                .select(HistoryInfo::getIp, HistoryInfo::getUserId, HistoryInfo::getNation, HistoryInfo::getProvince, HistoryInfo::getCity)
                .ge(HistoryInfo::getCreateTime, LocalDateTime.now().with(LocalTime.MIN))
                .list();

        result.put(CommonConst.IP_HISTORY_PROVINCE, history.get(CommonConst.IP_HISTORY_PROVINCE));
        result.put(CommonConst.IP_HISTORY_IP, history.get(CommonConst.IP_HISTORY_IP));
        result.put(CommonConst.IP_HISTORY_COUNT, history.get(CommonConst.IP_HISTORY_COUNT));
        List<Map<String, Object>> ipHistoryCount = (List<Map<String, Object>>) history.get(CommonConst.IP_HISTORY_HOUR);
        result.put("ip_count_yest", ipHistoryCount.stream().map(m -> m.get("ip")).distinct().count());
        result.put("username_yest", ipHistoryCount.stream().map(m -> {
            Object userId = m.get("user_id");
            if (userId != null) {
                User user = commonQuery.getUser(Integer.valueOf(userId.toString()));
                if (user != null) {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("avatar", user.getAvatar());
                    userInfo.put("username", user.getUsername());
                    return userInfo;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()));
        result.put("ip_count_today", infoList.stream().map(HistoryInfo::getIp).distinct().count());
        result.put("username_today", infoList.stream().map(m -> {
            Integer userId = m.getUserId();
            if (userId != null) {
                User user = commonQuery.getUser(userId);
                if (user != null) {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("avatar", user.getAvatar());
                    userInfo.put("username", user.getUsername());
                    return userInfo;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()));

        List<Map<String, Object>> list = infoList.stream()
                .map(HistoryInfo::getProvince).filter(Objects::nonNull)
                .collect(Collectors.groupingBy(m -> m, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("province", entry.getKey());
                    map.put("num", entry.getValue());
                    return map;
                })
                .sorted((o1, o2) -> Long.valueOf(o2.get("num").toString()).compareTo(Long.valueOf(o1.get("num").toString())))
                .collect(Collectors.toList());

        result.put("province_today", list);

        return WillFireResult.success(result);
    }

    /**
     * 更新网站信息
     * @param webInfo
     * @return
     */
    @LoginCheck(0)
    @PostMapping("/updateWebInfo")
    public WillFireResult<WebInfo> updateWebInfo(@RequestBody WebInfo webInfo) {
        webInfoService.updateById(webInfo);

        LambdaQueryChainWrapper<WebInfo> wrapper = new LambdaQueryChainWrapper<>(webInfoService.getBaseMapper());
        List<WebInfo> list = wrapper.list();
        if (!CollectionUtils.isEmpty(list)) {
            list.get(0).setDefaultStoreType(CommonConst.defaultStoreType);
            WillFireCache.put(CommonConst.WEB_INFO, list.get(0));
        }
        return WillFireResult.success();
    }



}
