package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.TreeHoleMapper;
import jie.dao.WebInfoMapper;
import jie.enums.CodeMsg;
import jie.enums.CommentTypeEnum;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
//import com.im.websocket.TioUtil;
//import com.im.websocket.TioWebsocketStarter;
import jie.service.ArticleService;
import jie.service.CommentService;
import jie.service.UserService;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.vo.ArticleVO;
import jie.vo.BaseRequestVO;
import jie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/14 上午11:18
 * @注释
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private WebInfoMapper webInfoMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TreeHoleMapper treeHoleMapper;

    /**
     * 获取管理员能看到的网站全部信息
     * @return
     */
    @GetMapping("/webInfo/getAdminWebInfo")
    @LoginCheck(0)
    public WillFireResult<WebInfo> getAdminWebInfo() {
        LambdaQueryChainWrapper wrapper = new LambdaQueryChainWrapper(webInfoMapper);
        List<WebInfo> list = wrapper.list();
        if (!CollectionUtils.isEmpty(list)) {
            return WillFireResult.success(list.get(0));
        } else {
            return WillFireResult.success();
        }
    }


    /**
     * 获取用户列表
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/user/list")
    @LoginCheck(0)
    public WillFireResult<Page> listUser(@RequestBody BaseRequestVO baseRequestVO) {
        Page page = userService.listUser(baseRequestVO);
        return WillFireResult.success(page);
    }


    /**
     * 修改用户赞赏
     */
    @GetMapping("/user/changeUserAdmire")
    @LoginCheck(0)
    public WillFireResult changeUserAdmire(@RequestParam("userId") Integer userId,
                                           @RequestParam("admire") String admire) {
        userService.lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getAdmire, admire)
                .update();
        // 清除缓存
        WillFireCache.remove(CommonConst.ADMIRE);
        return WillFireResult.success();
    }

    /**
     * 修改用户状态
     * @param userId
     * @param flag
     * @return
     */
    @GetMapping("/user/changeUserStatus")
    @LoginCheck(0)
    public WillFireResult changeUserStatus(@RequestParam("userId") Integer userId,
                                           @RequestParam("flag") Boolean flag) {
        if (userId.intValue() == WillFireUtil.getAdminUser().getId().intValue()) {
            throw new WillFireRuntimeException(CodeMsg.CANT_ROOT_STATUS);
        }

        LambdaUpdateChainWrapper<User> wrapper = userService.lambdaUpdate().eq(User::getId, userId);
        if (flag) {
            wrapper.eq(User::getUserStatus, WillFireEnum.STATUS_DISABLE.getCode()).set(User::getUserStatus, WillFireEnum.STATUS_ENABLE.getCode()).update();
        } else {
            wrapper.eq(User::getUserStatus, WillFireEnum.STATUS_ENABLE.getCode()).set(User::getUserStatus, WillFireEnum.STATUS_DISABLE.getCode()).update();
        }
        logout(userId);
        return WillFireResult.success();
    }

    @GetMapping("/user/changeUserType")
    public WillFireResult changeUserType(@RequestParam("userId") Integer userId,
                                         @RequestParam("userType") Integer userType) {
        if (userId.intValue() == WillFireUtil.getAdminUser().getId().intValue()) {
            throw new WillFireRuntimeException(CodeMsg.CANT_ROOT_TYPE);
        }


        if (userType != 0 && userType != 1 && userType != 2) {
            throw new WillFireRuntimeException(CodeMsg.PARAMETER_ERROR);
        }
        userService.lambdaUpdate().eq(User::getId, userId).set(User::getUserType, userType).update();
        logout(userId);
        return WillFireResult.success();
    }

    /**
     * boss查询文章
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/article/boss/list")
    @LoginCheck(0)
    public WillFireResult<Page> listBossArticle(@RequestBody BaseRequestVO baseRequestVO) {
        return articleService.listAdminArticle(baseRequestVO, true);
    }

    /**
     * 通过id获取文章
     * @param id
     * @return
     */
    @GetMapping("/article/getArticleById")
    @LoginCheck(1)
    public WillFireResult<ArticleVO> getArticleByIdForUser(@RequestParam("id") Integer id) {
        ArticleVO articleVO = articleService.getArticleByIdForUser(id);
        return WillFireResult.success(articleVO);
    }

    /**
     * 修改文章状态
     * @param articleId
     * @param viewStatus
     * @param commentStatus
     * @param recommendStatus
     * @return
     */
    @GetMapping("/article/changeArticleStatus")
    @LoginCheck(1)
    public WillFireResult changeArticleStatus(@RequestParam("articleId") Integer articleId,
                                            @RequestParam(value = "viewStatus", required = false) Boolean viewStatus,
                                            @RequestParam(value = "commentStatus", required = false) Boolean commentStatus,
                                            @RequestParam(value = "recommendStatus", required = false) Boolean recommendStatus) {
        LambdaUpdateChainWrapper<Article> updateChainWrapper = articleService.lambdaUpdate()
                .eq(Article::getId, articleId)
                .eq(Article::getUserId, WillFireUtil.getUserId());
        // 更新是否可见
        if (viewStatus != null) {
            updateChainWrapper.set(Article::getViewStatus, viewStatus);
        }
        // 更新是否启用评论
        if (commentStatus != null) {
            updateChainWrapper.set(Article::getCommentStatus, commentStatus);
        }
        // 更新是否推荐
        if (recommendStatus != null) {
            updateChainWrapper.set(Article::getRecommendStatus, recommendStatus);
        }
        updateChainWrapper.update();
        return WillFireResult.success();
    }

    /**
     * 作者删除评论
     * @param id
     * @return
     */
    @GetMapping("/comment/user/deleteComment")
    @LoginCheck(1)
    public WillFireResult userDeleteComment(@RequestParam("id") Integer id) {
        Comment comment = commentService.lambdaQuery().select(Comment::getSource, Comment::getType).eq(Comment::getId, id).one();
        if (comment == null) {
            return WillFireResult.success();
        }
        if (!CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(comment.getType())) {
            throw new WillFireRuntimeException(CodeMsg.PERMISSION_DENIED);
        }
        Article one = articleService.lambdaQuery().eq(Article::getId, comment.getSource()).select(Article::getUserId).one();
        if (one == null || (WillFireUtil.getUserId().intValue() != one.getUserId().intValue())) {
            throw new WillFireRuntimeException(CodeMsg.PERMISSION_DENIED);
        }
        commentService.removeById(id);
        return WillFireResult.success();
    }

    /**
     * boss删除评论
     * @param id
     * @return
     */
    @GetMapping("/comment/boss/deleteComment")
    @LoginCheck(0)
    public WillFireResult bossDeleteComment(@RequestParam("id") Integer id) {
        commentService.removeById(id);
        return WillFireResult.success();
    }

    /**
     * 用户查询评论
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/comment/user/list")
    @LoginCheck(1)
    public WillFireResult<Page> listUserComment(@RequestBody BaseRequestVO baseRequestVO) {
        Page page = commentService.listAdminComment(baseRequestVO,false);
        return WillFireResult.success(page);
    }

    /**
     * boss获取评论信息
     * @param baseRequestVO
     * @return
     */
    @PostMapping("comment/boss/list")
    @LoginCheck(0)
    public WillFireResult<Page> listBossComment(@RequestBody BaseRequestVO baseRequestVO) {
        Page page = commentService.listAdminComment(baseRequestVO, true);
        return WillFireResult.success(page);
    }

    /**
     * 用户查询文章
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/article/user/list")
    @LoginCheck(1)
    public WillFireResult<Page> listUserArticle(@RequestBody BaseRequestVO baseRequestVO) {
        return articleService.listAdminArticle(baseRequestVO, false);
    }

    /**
     * 查询树洞
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/treeHole/boss/list")
    @LoginCheck(0)
    public WillFireResult<Page> listBossTreeHole(@RequestBody BaseRequestVO baseRequestVO) {
        LambdaQueryChainWrapper<TreeHole> wrapper = new LambdaQueryChainWrapper<>(treeHoleMapper);
        wrapper.orderByDesc(TreeHole::getCreateTime).page(baseRequestVO);
        return WillFireResult.success(baseRequestVO);
    }

    private void logout(Integer userId) {
        if (WillFireCache.get(CommonConst.ADMIN_TOKEN + userId) != null) {
            String token = (String) WillFireCache.get(CommonConst.ADMIN_TOKEN + userId);
            WillFireCache.remove(CommonConst.ADMIN_TOKEN + userId);
            WillFireCache.remove(token);
        }

        if (WillFireCache.get(CommonConst.USER_TOKEN + userId) != null) {
            String token = (String) WillFireCache.get(CommonConst.USER_TOKEN + userId);
            WillFireCache.remove(CommonConst.USER_TOKEN + userId);
            WillFireCache.remove(token);
        }

    }
}
