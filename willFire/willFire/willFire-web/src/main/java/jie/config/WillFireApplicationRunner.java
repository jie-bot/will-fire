package jie.config;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.constants.CommonConst;
import jie.dao.HistoryInfoMapper;
import jie.dao.WebInfoMapper;
import jie.entity.Family;
import jie.entity.HistoryInfo;
import jie.entity.User;
import jie.entity.WebInfo;
import jie.enums.WillFireEnum;
//import com.im.websocket.TioUtil;
//import com.im.websocket.TioWebsocketStarter;
import jie.service.FamilyService;
import jie.service.UserService;
import jie.utils.cache.WillFireCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;


/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午10:19
 * @注释
 */

@Component
public class WillFireApplicationRunner implements ApplicationRunner {

//    @Value("${store.type}")
//    private String defaultType;

    @Autowired
    private WebInfoMapper webInfoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private HistoryInfoMapper historyInfoMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LambdaQueryChainWrapper<WebInfo> wrapper = new LambdaQueryChainWrapper<>(webInfoMapper);
        List<WebInfo> list = wrapper.list();
        if (!CollectionUtils.isEmpty(list)) {
            list.get(0).setDefaultStoreType(CommonConst.defaultStoreType);
            WillFireCache.put(CommonConst.WEB_INFO, list.get(0));
        }

        User admin = userService.lambdaQuery().eq(User::getUserType, WillFireEnum.USER_TYPE_ADMIN.getCode()).one();
        WillFireCache.put(CommonConst.ADMIN, admin);

        Family family = familyService.lambdaQuery().eq(Family::getUserId, admin.getId()).one();
        WillFireCache.put(CommonConst.ADMIN_FAMILY, family);

        List<HistoryInfo> infoList = new LambdaQueryChainWrapper<>(historyInfoMapper)
                .select(HistoryInfo::getIp, HistoryInfo::getUserId)
                .ge(HistoryInfo::getCreateTime, LocalDateTime.now().with(LocalTime.MIN))
                .list();

        WillFireCache.put(CommonConst.IP_HISTORY, new CopyOnWriteArraySet<>(infoList.stream().map(info -> info.getIp() + (info.getUserId() != null ? "_" + info.getUserId().toString() : "")).collect(Collectors.toList())));

        Map<String, Object> history = new HashMap<>();
        history.put(CommonConst.IP_HISTORY_PROVINCE, historyInfoMapper.getHistoryByProvince());
        history.put(CommonConst.IP_HISTORY_IP, historyInfoMapper.getHistoryByIp());
        history.put(CommonConst.IP_HISTORY_HOUR, historyInfoMapper.getHistoryBy24Hour());
        history.put(CommonConst.IP_HISTORY_COUNT, historyInfoMapper.getHistoryCount());
        WillFireCache.put(CommonConst.IP_HISTORY_STATISTICS, history);


//
//        TioUtil.buildTio();
//        TioWebsocketStarter websocketStarter = TioUtil.getTio();
//        if (websocketStarter != null) {
//            websocketStarter.start();
//        }
    }
}
