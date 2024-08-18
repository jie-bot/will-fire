package jie.utils.mail;

import com.alibaba.fastjson.JSONArray;
import jie.constants.CommonConst;
import jie.entity.Article;
import jie.entity.Comment;
import jie.entity.User;
import jie.entity.WebInfo;
import jie.enums.CommentTypeEnum;
import jie.service.CommentService;
import jie.utils.CommonQuery;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.vo.CommentVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/15 下午3:36
 * @注释 发送邮件
 */

@Component
public class MailSendUtil {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CommonQuery commonQuery;

    @Autowired
    private MailUtil mailUtil;

    public void sendCommentMail(CommentVO commentVO, Article one, CommentService commentService) {
        List<String> mail = new ArrayList<>();
        String toName = "";
        if (commentVO.getParentUserId() != null) {
            User user = commonQuery.getUser(commentVO.getParentUserId());
            if (user != null && !user.getId().equals(WillFireUtil.getUserId()) && StringUtils.hasText(user.getEmail())) {
                toName = user.getUsername();
                mail.add(user.getEmail());
            }
        } else {
            if (CommentTypeEnum.COMMENT_TYPE_MESSAGE.getCode().equals(commentVO.getType()) ||
                    CommentTypeEnum.COMMENT_TYPE_LOVE.getCode().equals(commentVO.getType())) {
                User adminUser = WillFireUtil.getAdminUser();
                if (StringUtils.hasText(adminUser.getEmail()) && !Objects.equals(WillFireUtil.getUserId(), adminUser.getId())) {
                    mail.add(adminUser.getEmail());
                }
            } else if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {
                User user = commonQuery.getUser(one.getUserId());
                if (user != null && StringUtils.hasText(user.getEmail()) && !user.getId().equals(WillFireUtil.getUserId())) {
                    mail.add(user.getEmail());
                }
            }
        }

        if (!CollectionUtils.isEmpty(mail)) {
            String sourceName = "";
            if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {
                sourceName = one.getArticleTitle();
            }
            String commentMail = getCommentMail(commentVO.getType(), sourceName,
                    WillFireUtil.getUsername(),
                    commentVO.getCommentContent(),
                    toName,
                    commentVO.getParentCommentId(), commentService);

            AtomicInteger count = (AtomicInteger) WillFireCache.get(CommonConst.COMMENT_IM_MAIL + mail.get(0));
            if (count == null || count.get() < CommonConst.COMMENT_IM_MAIL_COUNT) {
                WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
                RabEmailMessage rabEmailMessage = new RabEmailMessage();
                rabEmailMessage.setTo(mail);
                rabEmailMessage.setSubject("您有一封来自" + (webInfo == null ? "火の意志" : webInfo.getWebName()) + "的回执！");
                rabEmailMessage.setText(commentMail);
                String queueName = "will_fire.email"; //指定接收消息的队列名称
                String message = JSONArray.toJSONString(rabEmailMessage);//设置发送的消息信息
                rabbitTemplate.convertAndSend(queueName,message);
                if (count == null) {
                    WillFireCache.put(CommonConst.COMMENT_IM_MAIL + mail.get(0), new AtomicInteger(1), CommonConst.CODE_EXPIRE);
                } else {
                    count.incrementAndGet();
                }
            }
        }
    }

    /**
     * source：0留言 其他是文章标题
     * fromName：评论人
     * toName：被评论人
     */
    private String getCommentMail(String commentType, String source, String fromName,
                                  String fromContent, String toName, Integer toCommentId,
                                  CommentService commentService) {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        String webName = (webInfo == null ? "火の意志" : webInfo.getWebName());

        String mailType = "";
        String toMail = "";
        if (StringUtils.hasText(toName)) {
            mailType = String.format(MailUtil.replyMail, fromName);
            Comment toComment = commentService.lambdaQuery().select(Comment::getCommentContent).eq(Comment::getId, toCommentId).one();
            if (toComment != null) {
                toMail = String.format(MailUtil.originalText, toName, toComment.getCommentContent());
            }
        } else {
            if (CommentTypeEnum.COMMENT_TYPE_MESSAGE.getCode().equals(commentType)) {
                mailType = String.format(MailUtil.messageMail, fromName);
            } else if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentType)) {
                mailType = String.format(MailUtil.commentMail, source, fromName);
            } else if (CommentTypeEnum.COMMENT_TYPE_LOVE.getCode().equals(commentType)) {
                mailType = String.format(MailUtil.loveMail, fromName);
            }
        }

        return String.format(mailUtil.getMailText(),
                webName,
                mailType,
                fromName,
                fromContent,
                toMail,
                webName);
    }

//    public void sendImMail(ImChatUserMessage message) {
//        if (!message.getMessageStatus()) {
//            List<String> mail = new ArrayList<>();
//            String username = "";
//            User toUser = commonQuery.getUser(message.getToId());
//            if (toUser != null && StringUtils.hasText(toUser.getEmail())) {
//                mail.add(toUser.getEmail());
//            }
//            User fromUser = commonQuery.getUser(message.getFromId());
//            if (fromUser != null) {
//                username = fromUser.getUsername();
//            }
//
//            if (!CollectionUtils.isEmpty(mail)) {
//                String commentMail = getImMail(username, message.getContent());
//
//                AtomicInteger count = (AtomicInteger) WillFireCache.get(CommonConst.COMMENT_IM_MAIL + mail.get(0));
//                if (count == null || count.get() < CommonConst.COMMENT_IM_MAIL_COUNT) {
//                    WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
//                    mailUtil.sendMailMessage(mail, "您有一封来自" + (webInfo == null ? "POETIZE" : webInfo.getWebName()) + "的回执！", commentMail);
//                    if (count == null) {
//                        WillFireCache.put(CommonConst.COMMENT_IM_MAIL + mail.get(0), new AtomicInteger(1), CommonConst.CODE_EXPIRE);
//                    } else {
//                        count.incrementAndGet();
//                    }
//                }
//            }
//        }
//    }

//    private String getImMail(String fromName, String fromContent) {
//        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
//        String webName = (webInfo == null ? "POETIZE" : webInfo.getWebName());
//
//        return String.format(mailUtil.getMailText(),
//                webName,
//                String.format(MailUtil.imMail, fromName),
//                fromName,
//                fromContent,
//                "",
//                webName);
//    }
}
