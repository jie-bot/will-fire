package jie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jie.entity.HistoryInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午10:46
 * @注释 历史信息 Mapper 接口
 */
public interface HistoryInfoMapper extends BaseMapper<HistoryInfo> {

    /**
     * 访问次数最多的10个IP
     */
    @Select("select ip, count(*) as num from history_info group by ip order by num desc limit 10")
    List<Map<String, Object>> getHistoryByIp();

    /**
     * 访问IP最多的10个省
     * @return
     */
    @Select("select nation, province, count(distinct ip) as num " +
            "from history_info " +
            "where nation is not null and province is not null " +
            "group by nation, province, province " +
            "order by num desc " +
            "limit 10")
    List<Map<String, Object>> getHistoryByProvince();

    /**
     * 访问24小时内的数据
     * @return
     */
    @Select("select ip, user_id, nation, province " +
            "from history_info " +
            "where create_time >= (now() - interval 24 hour )")
     List<Map<String, Object>> getHistoryBy24Hour();

    /**
     * 总访问量
     */
    @Select("select count(*) from history_info")
    Long getHistoryCount();
}
