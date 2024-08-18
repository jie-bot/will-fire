package jie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jie.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午12:14
 * @注释
 */

public interface ArticleMapper extends BaseMapper<Article> {
    @Update("update article set view_count=view_count+1 where id=#{id}")
    int updateViewCount(@Param("id") Integer id);
}
