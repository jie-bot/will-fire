package jie.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ArticleMapper;
import jie.dao.LabelMapper;
import jie.dao.SortMapper;
import jie.enums.CodeMsg;
import jie.enums.CommentTypeEnum;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.ArticleService;
import jie.service.UserService;
import jie.utils.CommonQuery;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.utils.mail.MailUtil;
import jie.utils.mail.RabEmailMessage;
import jie.vo.ArticleVO;
import jie.vo.BaseRequestVO;
import jie.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:48
 * @注释
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SortMapper sortMapper;

    @Autowired
    private CommonQuery commonQuery;

    // 订阅格式
    @Value("${user.subscribe.format}")
    private String subscribeFormat;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 查询分类文章List
     * @return
     */
    @Override
    public Map<Integer, List<ArticleVO>> listSortArticle() {
        Map<Integer, List<ArticleVO>> result = (Map<Integer, List<ArticleVO>>) WillFireCache.get(CommonConst.SORT_ARTICLE_LIST);
        if (result != null) {
            return result;
        }

        synchronized (CommonConst.SORT_ARTICLE_LIST.intern()) {
            result = (Map<Integer, List<ArticleVO>>) WillFireCache.get(CommonConst.SORT_ARTICLE_LIST);
            if (result == null) {
                Map<Integer, List<ArticleVO>> map = new HashMap<>();

                List<Sort> sorts = new LambdaQueryChainWrapper<>(sortMapper).select(Sort::getId).list();
                for (Sort sort : sorts) {
                    LambdaQueryChainWrapper<Article> lambdaQuery = lambdaQuery()
                            .eq(Article::getSortId, sort.getId())
                            .orderByDesc(Article::getCreateTime)
                            .last("limit 6");
                    List<Article> articleList = lambdaQuery.list();
                    if (CollectionUtils.isEmpty(articleList)) {
                        continue;
                    }

                    List<ArticleVO> articleVOList = articleList.stream().map(article -> {
                        if (article.getArticleContent().length() > CommonConst.SUMMARY) {
                            article.setArticleContent(article.getArticleContent().substring(0, CommonConst.SUMMARY).replace("`", "").replace("#", "").replace(">", ""));
                        }

                        ArticleVO vo = buildArticleVO(article, false);
                        vo.setHasVideo(StringUtils.hasText(article.getVideoUrl()));
                        vo.setPassword(null);
                        vo.setVideoUrl(null);
                        return vo;
                    }).collect(Collectors.toList());
                    map.put(sort.getId(), articleVOList);
                }

                WillFireCache.put(CommonConst.SORT_ARTICLE_LIST, map, CommonConst.TOKEN_INTERVAL);
                return map;
            } else {
                return result;
            }
        }
    }

    /**
     * 分页查询文章
     * @param baseRequestVO
     * @return
     */
    @Override
    public Page listArticle(BaseRequestVO baseRequestVO) {
        List<Integer> ids = null;
        List<List<Integer>> idList = null;
        // 是否搜索
        if (StringUtils.hasText(baseRequestVO.getArticleSearch())) {
            // 按照搜索字段进行匹配查询
            idList = commonQuery.getArticleIds(baseRequestVO.getArticleSearch());
            ids = idList.stream().flatMap(Collection::stream).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(ids)) {
                // 空的，没有查找到
                baseRequestVO.setRecords(new ArrayList());
                return baseRequestVO;
            }
        }

        // 查询ids里面的所有文章，匹配查询参数
        LambdaQueryChainWrapper<Article> lambdaQuery = lambdaQuery();
        lambdaQuery.in(!CollectionUtils.isEmpty(ids), Article::getId, ids);
        lambdaQuery.like(StringUtils.hasText(baseRequestVO.getSearchKey()), Article::getArticleTitle, baseRequestVO.getSearchKey());
        lambdaQuery.eq(baseRequestVO.getRecommendStatus() != null && baseRequestVO.getRecommendStatus(), Article::getRecommendStatus, WillFireEnum.STATUS_ENABLE.getCode());
        if (baseRequestVO.getLabelId() != null) {
            lambdaQuery.eq(Article::getLabelId, baseRequestVO.getLabelId());
        } else if (baseRequestVO.getSortId() != null) {
            lambdaQuery.eq(Article::getSortId, baseRequestVO.getSortId());
        }

        // 根据创建时间排序
        lambdaQuery.orderByDesc(Article::getCreateTime);

        // 分页查询
        lambdaQuery.page(baseRequestVO);

        List<Article> records = baseRequestVO.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<ArticleVO> articles = new ArrayList<>();
            List<ArticleVO> titles = new ArrayList<>();
            List<ArticleVO> contents = new ArrayList<>();

            for (Article article : records) {
                if (article.getArticleContent().length() > CommonConst.SUMMARY) {
                    article.setArticleContent(article.getArticleContent().substring(0, CommonConst.SUMMARY).replace("`", "").replace("#", "").replace(">", ""));
                }
                ArticleVO vo = buildArticleVO(article, false);
                vo.setHasVideo(StringUtils.hasText(article.getVideoUrl()));
                vo.setPassword(null);
                vo.setVideoUrl(null);
                if (CollectionUtils.isEmpty(ids)) {
                    articles.add(vo);
                } else if (idList.get(0).contains(vo.getId())) {
                    titles.add(vo);
                } else if (idList.get(1).contains(vo.getId())) {
                    contents.add(vo);
                }
            }
            List<ArticleVO> collect = new ArrayList<>();
            collect.addAll(articles);
            collect.addAll(titles);
            collect.addAll(contents);
            baseRequestVO.setRecords(collect);
        }
        return baseRequestVO;
    }

    /**
     * 查询文章
     * @param baseRequestVO
     * @param isBoss
     * @return
     */
    @Override
    public WillFireResult<Page> listAdminArticle(BaseRequestVO baseRequestVO, boolean isBoss) {
        LambdaQueryChainWrapper<Article> lambdaQuery = lambdaQuery();
        // 不获取文章内容字段
        lambdaQuery.select(Article.class, a -> !a.getColumn().equals("article_content"));
        if (!isBoss) {
            lambdaQuery.eq(Article::getUserId, WillFireUtil.getUserId());
        } else {
            if (baseRequestVO.getUserId() != null) {
                lambdaQuery.eq(Article::getUserId, baseRequestVO.getUserId());
            }
        }

        // 匹配搜索条件
        if (StringUtils.hasText(baseRequestVO.getSearchKey())) {
            lambdaQuery.like(Article::getArticleTitle, baseRequestVO.getSearchKey());
        }

        // 是否推荐
        if (baseRequestVO.getRecommendStatus() != null && !baseRequestVO.getRecommendStatus()) {
            lambdaQuery.eq(Article::getRecommendStatus, WillFireEnum.STATUS_DISABLE.getCode());
        }

        // 根据标签查询
        if (baseRequestVO.getLabelId() != null) {
            lambdaQuery.eq(Article::getLabelId, baseRequestVO.getLabelId());
        }

        // 根据分类查询
        if (baseRequestVO.getSortId() != null) {
            lambdaQuery.eq(Article::getSortId, baseRequestVO.getSortId());
        }

        // 创建时间排序
        lambdaQuery.orderByDesc(Article::getCreateTime).page(baseRequestVO);

        // 返回结果
        List<Article> records = baseRequestVO.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<ArticleVO> collect = records.stream().map(article -> {
                article.setPassword(null);
                ArticleVO articleVO = buildArticleVO(article, true);
                return articleVO;
            }).collect(Collectors.toList());
            baseRequestVO.setRecords(collect);
        }
        return WillFireResult.success(baseRequestVO);
    }

    /**
     * 保存文章
     * @param articleVO
     */
    @Override
    public void saveArticle(ArticleVO articleVO) {
        // 私密文档要设置密码
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && !StringUtils.hasText(articleVO.getPassword())) {
            throw new WillFireRuntimeException(CodeMsg.SET_ARTICLE_PASSWORD);
        }

        // 设置数据库交互对象
        Article article = new Article();
        if (StringUtils.hasText(articleVO.getArticleCover())) {
            article.setArticleCover(articleVO.getArticleCover());
        }
        if (StringUtils.hasText(articleVO.getVideoUrl())) {
            article.setVideoUrl(articleVO.getVideoUrl());
        }
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && StringUtils.hasText(articleVO.getPassword())) {
            article.setPassword(articleVO.getPassword());
            article.setTips(articleVO.getTips());
        }
        article.setViewStatus(articleVO.getViewStatus());
        article.setCommentStatus(articleVO.getCommentStatus());
        article.setRecommendStatus(articleVO.getRecommendStatus());
        article.setArticleTitle(articleVO.getArticleTitle());
        article.setArticleContent(articleVO.getArticleContent());
        article.setSortId(articleVO.getSortId());
        article.setLabelId(articleVO.getLabelId());
        article.setUserId(WillFireUtil.getUserId());
        save(article);

        // 刷新缓存信息
        WillFireCache.remove(CommonConst.SORT_INFO);

        // 测试rabbitmq
//        for (int i = 0; i < 10; i++) {
//            String queueName = "will_fire.email"; //指定接收消息的队列名称
//            String message = JSONArray.toJSONString("hello world" + i);//设置发送的消息信息
//            rabbitTemplate.convertAndSend(queueName,message);
//        }

        // 处理订阅邮件发送
        try {
            if (articleVO.getViewStatus()) {
                List<User> users = userService.lambdaQuery().select(User::getEmail, User::getSubscribe).eq(User::getUserStatus, WillFireEnum.STATUS_ENABLE.getCode()).list();
                List<String> emails = users.stream().filter(u -> {
                    List<Integer> sub = JSON.parseArray(u.getSubscribe(), Integer.class);
                    return !CollectionUtils.isEmpty(sub) && sub.contains(articleVO.getLabelId());
                }).map(User::getEmail).collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(emails)) {
                    LambdaQueryChainWrapper<Label> wrapper = new LambdaQueryChainWrapper<>(labelMapper);
                    Label label = wrapper.select(Label::getLabelName).eq(Label::getId, articleVO.getLabelId()).one();
                    String text = getSubscribeMail(label.getLabelName(), articleVO.getArticleTitle());
                    WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
//                    mailUtil.sendMailMessage(emails, "您有一封来自" + (webInfo == null ? "火の意志" : webInfo.getWebName()) + "的回执！", text);
                    // 添加任务到队列尾部
                    RabEmailMessage rabEmailMessage = new RabEmailMessage();
                    rabEmailMessage.setTo(emails);
                    rabEmailMessage.setSubject("您有一封来自" + (webInfo == null ? "火の意志" : webInfo.getWebName()) + "的回执！");
                    rabEmailMessage.setText(text);
                    String queueName = "will_fire.email"; //指定接收消息的队列名称
                    String message = JSONArray.toJSONString(rabEmailMessage);//设置发送的消息信息
                    rabbitTemplate.convertAndSend(queueName,message);
                }
            }
        } catch (Exception e) {
            log.error("订阅邮件发送失败：", e);
        }
    }

    /**
     * 删除文章
     * @param id
     */
    @Override
    public void deleteArticle(Integer id) {
        log.info("删除文章");
        Integer userId = WillFireUtil.getUserId();
        lambdaUpdate().eq(Article::getId, id)
                .eq(Article::getUserId, userId)
                .remove();
        WillFireCache.remove(CommonConst.SORT_INFO);
    }

    /**
     * 获取文章的具体信息
     * @param id
     * @return
     */
    @Override
    public ArticleVO getArticleByIdForUser(Integer id) {
        LambdaQueryChainWrapper<Article> lambdaQuery = lambdaQuery();
        lambdaQuery.eq(Article::getId, id).eq(Article::getUserId, WillFireUtil.getUserId());
        Article article = lambdaQuery.one();
        if (article == null) {
            throw new WillFireRuntimeException(CodeMsg.ARTICLE_NOT_EXIST);
        }

        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        return articleVO;
    }

    /**
     * 更新文章信息
     * @param articleVO
     */
    @Override
    public void updateArticle(ArticleVO articleVO) {
        // 私密文章要设置访问密码
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && !StringUtils.hasText(articleVO.getPassword())) {
            throw new WillFireRuntimeException(CodeMsg.SET_ARTICLE_PASSWORD);
        }

        Integer userId = WillFireUtil.getUserId();
        // 设置更新字段
        LambdaUpdateChainWrapper<Article> updateChainWrapper = lambdaUpdate()
                .eq(Article::getId, articleVO.getId())
                .eq(Article::getUserId, userId)
                .set(Article::getLabelId, articleVO.getLabelId())
                .set(Article::getSortId, articleVO.getSortId())
                .set(Article::getArticleTitle, articleVO.getArticleTitle())
                .set(Article::getUpdateBy, WillFireUtil.getUsername())
                .set(Article::getUpdateTime, LocalDateTime.now())
                .set(Article::getVideoUrl, StringUtils.hasText(articleVO.getVideoUrl()) ? articleVO.getVideoUrl() : null)
                .set(Article::getArticleContent, articleVO.getArticleContent());

        if (StringUtils.hasText(articleVO.getArticleCover())) {
            updateChainWrapper.set(Article::getArticleCover, articleVO.getArticleCover());
        }
        if (articleVO.getCommentStatus() != null) {
            updateChainWrapper.set(Article::getCommentStatus, articleVO.getCommentStatus());
        }
        if (articleVO.getRecommendStatus() != null) {
            updateChainWrapper.set(Article::getRecommendStatus, articleVO.getRecommendStatus());
        }
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && StringUtils.hasText(articleVO.getPassword())) {
            updateChainWrapper.set(Article::getPassword, articleVO.getPassword());
            updateChainWrapper.set(StringUtils.hasText(articleVO.getTips()), Article::getTips, articleVO.getTips());
        }
        if (articleVO.getViewStatus() != null) {
            updateChainWrapper.set(Article::getViewStatus, articleVO.getViewStatus());
        }
        updateChainWrapper.update();
        // 刷新缓存
        WillFireCache.remove(CommonConst.SORT_INFO);
    }

    /**
     * id 查询文章
     * @param id
     * @param password
     * @return
     */
    @Override
    public ArticleVO getArticleById(Integer id, String password) {
        // 加密文章要判断密码
        LambdaQueryChainWrapper<Article> lambdaQuery = lambdaQuery();
        lambdaQuery.eq(Article::getId, id);

        Article article = lambdaQuery.one();
        if (article == null) {
            throw new WillFireRuntimeException(CodeMsg.ARTICLE_NOT_EXIST);
        }

        if (!article.getViewStatus() && (!StringUtils.hasText(password) || !password.equals(article.getPassword()))) {
            String msg = "密码错误" + (StringUtils.hasText(article.getTips()) ? article.getTips() : "请联系作者获取密码");
            throw new WillFireRuntimeException(msg, 500);
        }

        // 刷新浏览量
        articleMapper.updateViewCount(id);
        // 返回给前端的时候不能把密码给带回去
        article.setPassword(null);
        if (StringUtils.hasText(article.getVideoUrl())) {
            article.setVideoUrl(SecureUtil.aes(CommonConst.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(article.getVideoUrl()));
        }

        ArticleVO articleVO = buildArticleVO(article, false);
        return articleVO;
    }

    /**
     * 获取订阅信息
     * @param labelName
     * @param articleTitle
     * @return
     */
    private String getSubscribeMail(String labelName, String articleTitle) {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        String webName = (webInfo == null ? "火の意志" : webInfo.getWebName());
        return String.format(mailUtil.getMailText(),
                webName,
                String.format(MailUtil.notificationMail, WillFireUtil.getAdminUser().getUsername()),
                WillFireUtil.getAdminUser().getUsername(),
                String.format(subscribeFormat, labelName, articleTitle),
                "",
                webName);
    }

    /**
     * 构建文章vo
     * @param article
     * @param isAdmin
     * @return
     */
    private ArticleVO buildArticleVO(Article article, Boolean isAdmin) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        if (!isAdmin) {
            if (!StringUtils.hasText(articleVO.getArticleCover())) {
                articleVO.setArticleCover(WillFireUtil.getRandomCover(articleVO.getId().toString()));
            }
        }

        // 查询当前用户
        User user = commonQuery.getUser(articleVO.getUserId());
        if (user != null && StringUtils.hasText(user.getUsername())) {
            articleVO.setUsername(user.getUsername());
        } else if (!isAdmin) {
            articleVO.setUsername(WillFireUtil.getRandomName(articleVO.getUserId().toString()));
        }
        // 是否启用评论
        if (articleVO.getCommentStatus()) {
            articleVO.setCommentCount(commonQuery.getCommentCount(articleVO.getId(), CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode()));
        } else {
            articleVO.setCommentCount(0);
        }
        List<Sort> sortInfo = commonQuery.getSortInfo();
        if (!CollectionUtils.isEmpty(sortInfo)) {
            for (Sort s : sortInfo) {
                if (s.getId().intValue() == articleVO.getSortId().intValue()) {
                    Sort sort = new Sort();
                    BeanUtils.copyProperties(s, sort);
                    sort.setLabels(null);
                    articleVO.setSort(sort);
                    if (!CollectionUtils.isEmpty(s.getLabels())) {
                        for (int j = 0; j < s.getLabels().size(); j++) {
                            Label l = s.getLabels().get(j);
                            if (l.getId().intValue() == articleVO.getLabelId().intValue()) {
                                Label label = new Label();
                                BeanUtils.copyProperties(l, label);
                                articleVO.setLabel(label);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return articleVO;
    }
}
