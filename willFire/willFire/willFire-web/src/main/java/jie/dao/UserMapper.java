package jie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jie.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午10:29
 * @注释
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where (username = #{account} or email = #{account} or phone_number = #{account}) and password = #{password}")
    User select(String account, String password);
}
