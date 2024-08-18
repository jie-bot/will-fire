package jie.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jie.constants.CommonConst;
import jie.dao.ArticleMapper;
import jie.dao.CommentMapper;
import jie.entity.Article;
import jie.entity.Comment;
import jie.entity.User;
import jie.enums.CodeMsg;
import jie.enums.CommentTypeEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.CommentService;
import jie.utils.CommonQuery;
import jie.utils.WillFireUtil;
import jie.utils.mail.MailSendUtil;
import jie.vo.BaseRequestVO;
import jie.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午3:38
 * @注释
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private MailSendUtil mailSendUtil;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommonQuery commonQuery;

    /**
     * boss查询评论信息
     * @param baseRequestVO
     * @param isBoss
     * @return
     */
    @Override
    public Page listAdminComment(BaseRequestVO baseRequestVO, boolean isBoss) {
        LambdaQueryChainWrapper<Comment> wrapper = lambdaQuery();
        if (isBoss) {
            // 评论来源
            if (baseRequestVO.getSource() != null) {
                wrapper.eq(Comment::getSource, baseRequestVO.getSource());
            }
            // 评论类型
            if (StringUtils.hasText(baseRequestVO.getCommentType())) {
                wrapper.eq(Comment::getType, baseRequestVO.getCommentType());
            }
            // 时间排序
            wrapper.orderByDesc(Comment::getCreateTime).page(baseRequestVO);
        } else {
            // 获取用户评论
            List<Integer> userArticleIds = commonQuery.getUserArticleIds(WillFireUtil.getUserId());
            if (CollectionUtils.isEmpty(userArticleIds)) {
                baseRequestVO.setTotal(0);
                baseRequestVO.setRecords(new ArrayList());
            } else {
                if (baseRequestVO.getSource() != null) {
                    wrapper.eq(Comment::getSource, baseRequestVO.getSource()).eq(Comment::getType, CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode());
                } else {
                    wrapper.eq(Comment::getType, CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode()).in(Comment::getSource, userArticleIds);
                }
                wrapper.orderByDesc(Comment::getCreateTime).page(baseRequestVO);
            }
        }
        return baseRequestVO;
    }

    /**
     * 查询评论列表
     * @param baseRequestVO
     * @return
     */
    @Override
    public BaseRequestVO listComment(BaseRequestVO baseRequestVO) {
        // 参数错误，没有评论来源和评论类型
        if (baseRequestVO.getSource() == null || !StringUtils.hasText(baseRequestVO.getCommentType())) {
            throw new WillFireRuntimeException(CodeMsg.PARAMETER_ERROR);
        }

        // 获取文章评论
        if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(baseRequestVO.getCommentType())) {
            LambdaQueryChainWrapper<Article> articleWrapper = new LambdaQueryChainWrapper<>(articleMapper);
            Article one = articleWrapper.eq(Article::getId, baseRequestVO.getSource()).select(Article::getCommentStatus).one();
            // 该文章是否关闭评论区
            if (one != null && !one.getCommentStatus()) {
                throw new WillFireRuntimeException(CodeMsg.COMMENT_CLOSE);
            }
        }

        // 是不是楼主
        if (baseRequestVO.getFloorCommentId() == null) {
            lambdaQuery()
                    .eq(Comment::getSource, baseRequestVO.getSource())
                    .eq(Comment::getType, baseRequestVO.getCommentType())
                    .eq(Comment::getParentCommentId, CommonConst.FIRST_COMMENT)
                    .orderByDesc(Comment::getCreateTime)
                    .page(baseRequestVO);

            List<Comment> comments = baseRequestVO.getRecords();
            if (CollectionUtils.isEmpty(comments)) {
                return baseRequestVO;
            }
            List<CommentVO> commentVOs = comments.stream().map(c -> {
                CommentVO commentVO = buildCommentVO(c);
                Page page = new Page(1, 5);
                lambdaQuery()
                        .eq(Comment::getSource, baseRequestVO.getSource())
                        .eq(Comment::getType, baseRequestVO.getCommentType())
                        .eq(Comment::getFloorCommentId, c.getId())
                        .orderByAsc(Comment::getCreateTime)
                        .page(page);
                List<Comment> childComments = page.getRecords();
                if (childComments != null) {
                    List<CommentVO> ccVO = childComments.stream().map(cc -> buildCommentVO(cc)).collect(Collectors.toList());
                    page.setRecords(ccVO);
                }
                commentVO.setChildComments(page);
                return commentVO;
            }).collect(Collectors.toList());
            baseRequestVO.setRecords(commentVOs);
        } else {
            lambdaQuery().eq(Comment::getSource, baseRequestVO.getSource()).eq(Comment::getType, baseRequestVO.getCommentType()).eq(Comment::getFloorCommentId, baseRequestVO.getFloorCommentId()).orderByAsc(Comment::getCreateTime).page(baseRequestVO);
            List<Comment> childComments = baseRequestVO.getRecords();
            if (CollectionUtils.isEmpty(childComments)) {
                return baseRequestVO;
            }
            List<CommentVO> ccVO = childComments.stream().map(cc -> buildCommentVO(cc)).collect(Collectors.toList());
            baseRequestVO.setRecords(ccVO);
        }
        return baseRequestVO;
    }

    /**
     * 保存评论
     * @param commentVO
     */
    @Override
    public void saveComment(CommentVO commentVO) {
        if (CommentTypeEnum.getEnumByCode(commentVO.getType()) == null) {
            throw new WillFireRuntimeException(CodeMsg.PARAMETER_ERROR);
        }
        Article one = null;
        if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {
            LambdaQueryChainWrapper<Article> articleWrapper = new LambdaQueryChainWrapper<>(articleMapper);
            one = articleWrapper.eq(Article::getId, commentVO.getSource()).select(Article::getUserId, Article::getArticleTitle, Article::getCommentStatus).one();

            if (one == null) {
                throw new WillFireRuntimeException(CodeMsg.ARTICLE_NOT_EXIST);
            } else {
                if (!one.getCommentStatus()) {
                    // 评论区已经关闭了
                    throw new WillFireRuntimeException(CodeMsg.COMMENT_CLOSE);
                }
            }
        }

        Comment comment = new Comment();
        comment.setSource(commentVO.getSource());
        comment.setType(commentVO.getType());
        comment.setCommentContent(commentVO.getCommentContent());
        comment.setParentCommentId(commentVO.getParentCommentId());
        comment.setFloorCommentId(commentVO.getFloorCommentId());
        comment.setParentUserId(commentVO.getParentUserId());
        comment.setUserId(WillFireUtil.getUserId());
        if (StringUtils.hasText(commentVO.getCommentInfo())) {
            comment.setCommentInfo(commentVO.getCommentInfo());
        }
        save(comment);

        try {
//            RabEmailMessage rabEmailMessage = new RabEmailMessage();
//            rabEmailMessage.setTo(emails);
//            rabEmailMessage.setSubject("您有一封来自" + (webInfo == null ? "火の意志" : webInfo.getWebName()) + "的回执！");
//            rabEmailMessage.setText(text);
//            String queueName = "will_fire.email"; //指定接收消息的队列名称
//            String message = JSONArray.toJSONString(rabEmailMessage);//设置发送的消息信息
//            rabbitTemplate.convertAndSend(queueName,message);
            mailSendUtil.sendCommentMail(commentVO, one, this);
        } catch (Exception e) {
            log.error("发送评论邮件失败：", e);
        }

//        return PoetryResult.success();
    }

    /**
     * 创建评论vo
     * @param c
     * @return
     */
    private CommentVO buildCommentVO(Comment c) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(c, commentVO);

        User user = commonQuery.getUser(commentVO.getUserId());
        if (user != null) {
            commentVO.setAvatar(user.getAvatar());
            commentVO.setUsername(user.getUsername());
        }

        if (!StringUtils.hasText(commentVO.getUsername())) {
            commentVO.setUsername(WillFireUtil.getRandomName(commentVO.getUserId().toString()));
        }

        if (commentVO.getParentUserId() != null) {
            User u = commonQuery.getUser(commentVO.getParentUserId());
            if (u != null) {
                commentVO.setParentUsername(u.getUsername());
            }
            if (!StringUtils.hasText(commentVO.getParentUsername())) {
                commentVO.setParentUsername(WillFireUtil.getRandomName(commentVO.getParentUserId().toString()));
            }
        }
        return commentVO;
    }
}
