package jie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jie.entity.TreeHole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午10:56
 * @注释
 */
public interface TreeHoleMapper extends BaseMapper<TreeHole> {
    List<TreeHole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
}
