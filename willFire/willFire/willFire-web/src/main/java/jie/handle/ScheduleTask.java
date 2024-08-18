package jie.handle;

import jie.dao.HistoryInfoMapper;
import jie.constants.CommonConst;
import jie.utils.cache.WillFireCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:11
 * @注释 定时任务
 */

@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {

    @Autowired
    private HistoryInfoMapper historyInfoMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanIpHistory() {
        CopyOnWriteArraySet<String> ipHistory = (CopyOnWriteArraySet<String>) WillFireCache.get(CommonConst.IP_HISTORY);
        if (ipHistory == null) {
            ipHistory = new CopyOnWriteArraySet<>();
            WillFireCache.put(CommonConst.IP_HISTORY, ipHistory);
        }
        ipHistory.clear();

        WillFireCache.remove(CommonConst.IP_HISTORY_STATISTICS);
        Map<String, Object> history = new HashMap<>();
        history.put(CommonConst.IP_HISTORY_PROVINCE, historyInfoMapper.getHistoryByProvince());
        history.put(CommonConst.IP_HISTORY_IP, historyInfoMapper.getHistoryByIp());
        history.put(CommonConst.IP_HISTORY_HOUR, historyInfoMapper.getHistoryBy24Hour());
        history.put(CommonConst.IP_HISTORY_COUNT, historyInfoMapper.getHistoryCount());
        WillFireCache.put(CommonConst.IP_HISTORY_STATISTICS, history);

        log.info("清除历史IP记录");
    }

}
