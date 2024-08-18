package jie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jie.config.WillFireResult;
import jie.entity.User;
import jie.vo.BaseRequestVO;
import jie.vo.UserVO;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午10:26
 * @注释 用户信息相关
 */
public interface UserService extends IService<User> {

    UserVO login(String account, String password, Boolean isAdmin);

    void exit();

    Page listUser(BaseRequestVO baseRequestVO);

    UserVO subscribe(Integer labelId, Boolean flag);

    void getCodeForForgetPassword(String place, Integer flag);

    UserVO regist(UserVO user);

    UserVO token(String userToken);

    WillFireResult getCodeForBind(String place, Integer flag);

    WillFireResult<UserVO> updateUserInfo(UserVO user);

    WillFireResult getCode(Integer flag);

    WillFireResult<UserVO> updateSecretInfo(String place, Integer flag, String code, String password);
}
