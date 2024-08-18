package jie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jie.entity.Comment;
import jie.vo.BaseRequestVO;
import jie.vo.CommentVO;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午3:38
 * @注释
 */
public interface CommentService extends IService<Comment> {
    Page listAdminComment(BaseRequestVO baseRequestVO, boolean isBoss);

    BaseRequestVO listComment(BaseRequestVO baseRequestVO);

    void saveComment(CommentVO commentVO);
}
