package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.handle.WillFireRuntimeException;
import jie.service.CommentService;
import jie.utils.CommonQuery;
import jie.utils.StringUtil;
import jie.utils.cache.WillFireCache;
import jie.vo.BaseRequestVO;
import jie.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午11:35
 * @注释
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommonQuery commonQuery;

    /**
     * 查询评论
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listComment")
    public WillFireResult<BaseRequestVO> listComment(@RequestBody BaseRequestVO baseRequestVO) {
        BaseRequestVO vo = commentService.listComment(baseRequestVO);
        return WillFireResult.success(vo);
    }

    /**
     * 保存评论
     * @param commentVO
     * @return
     */

    @PostMapping("/saveComment")
    @LoginCheck
    @SaveCheck
    public WillFireResult saveComment(@Validated @RequestBody CommentVO commentVO) {
        String content = StringUtil.removeHtml(commentVO.getCommentContent());
        if (!StringUtils.hasText(content)) {
            throw new WillFireRuntimeException("评论内容为空~~", 500);
        }
        commentVO.setCommentContent(content);

        // 刷新文章的评论数量
        WillFireCache.remove(CommonConst.COMMENT_COUNT_CACHE + commentVO.getSource().toString() + "_" + commentVO.getType());
        commentService.saveComment(commentVO);
        return WillFireResult.success();
    }

    /**
     * 获取评论数量
     * @param source
     * @param type
     * @return
     */
    @GetMapping("/getCommentCount")
    public WillFireResult<Integer> getCommentCount(@RequestParam("source") Integer source, @RequestParam("type") String type) {
        return WillFireResult.success(commonQuery.getCommentCount(source, type));
    }
}
