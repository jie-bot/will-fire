package jie.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.constants.CommonConst;
import jie.dao.*;
import jie.service.UserService;
import jie.utils.cache.WillFireCache;
import jie.vo.FamilyVO;
import jie.entity.*;
import org.apache.commons.io.IOUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午11:23
 * @注释
 */
@Component
public class CommonQuery {

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryInfoMapper historyInfoMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private SortMapper sortMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private CommentMapper commentMapper;

    private Searcher searcher;

    @PostConstruct
    public void init() {
        try {
            searcher = Searcher.newWithBuffer(IOUtils.toByteArray(new ClassPathResource("ip2region.xdb").getInputStream()));
        } catch (Exception e) {
        }
    }

    public void saveHistory(String ip) {
        // 获取当前用户id
        Integer userId = WillFireUtil.getUserId();
        String ipUser = ip + (userId != null ? "_" + userId.toString() : "");

        CopyOnWriteArraySet<String> ipHistory = (CopyOnWriteArraySet<String>) WillFireCache.get(CommonConst.IP_HISTORY);
        if (!ipHistory.contains(ipUser)) {
            synchronized (ipUser.intern()) {
                if (!ipHistory.contains(ipUser)) {
                    ipHistory.add(ipUser);
                    HistoryInfo historyInfo = new HistoryInfo();
                    historyInfo.setIp(ip);
                    historyInfo.setUserId(userId);
                    if (searcher != null) {
                        try {
                            String search = searcher.search(ip);
                            String[] region = search.split("\\|");
                            if (!"0".equals(region[0])) {
                                historyInfo.setNation(region[0]);
                            }
                            if (!"0".equals(region[2])) {
                                historyInfo.setProvince(region[2]);
                            }
                            if (!"0".equals(region[3])) {
                                historyInfo.setCity(region[3]);
                            }
                        } catch (Exception e) {
                        }
                    }
                    historyInfoMapper.insert(historyInfo);
                }
            }
        }
    }

    public User getUser(Integer userId) {
        User user = (User) WillFireCache.get(CommonConst.USER_CACHE + userId.toString());
        if (user != null) {
            return user;
        }
        User u = userService.getById(userId);
        if (u != null) {
            WillFireCache.put(CommonConst.USER_CACHE + userId.toString(), u, CommonConst.EXPIRE);
            return u;
        }
        return null;
    }

    /**
     * 获取赞赏的人
     * @return
     */
    public List<User> getAdmire() {
        List<User> admire = (List<User>) WillFireCache.get(CommonConst.ADMIRE);
        if (admire != null) {
            return admire;
        }

        synchronized (CommonConst.ADMIRE.intern()) {
            admire = (List<User>) WillFireCache.get(CommonConst.ADMIRE);
            if (admire != null) {
                return admire;
            } else {
                List<User> users = userService.lambdaQuery().select(User::getId, User::getAdmire, User::getAvatar).isNotNull(User::getAdmire).list();
                WillFireCache.put(CommonConst.ADMIRE, users, CommonConst.EXPIRE);
                return users;
            }
        }
    }

    public List<FamilyVO> getFamilyList() {
        List<FamilyVO> familyVOList = (List<FamilyVO>) WillFireCache.get(CommonConst.FAMILY_LIST);
        if (familyVOList != null) {
            return familyVOList;
        }

        synchronized (CommonConst.FAMILY_LIST.intern()) {
            familyVOList = (List<FamilyVO>) WillFireCache.get(CommonConst.FAMILY_LIST);
            if (familyVOList != null) {
                return familyVOList;
            } else {

                LambdaQueryWrapper<Family> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Family::getStatus, Boolean.TRUE);
                List<Family> familyList = familyMapper.selectList(queryWrapper);

//                LambdaQueryChainWrapper<Family> queryChainWrapper = new LambdaQueryChainWrapper<>(familyMapper);
//                List<Family> familyList = queryChainWrapper.eq(Family::getStatus, Boolean.TRUE).list();
                if (!CollectionUtils.isEmpty(familyList)) {
                    familyVOList = familyList.stream().map(family -> {
                        FamilyVO familyVO = new FamilyVO();
                        BeanUtils.copyProperties(family, familyVO);
                        return familyVO;
                    }).collect(Collectors.toList());
                } else {
                    familyVOList = new ArrayList<>();
                }
                WillFireCache.put(CommonConst.FAMILY_LIST, familyVOList);
                return familyVOList;
            }
        }
    }

    public List<Integer> getUserArticleIds(Integer userId) {
        List<Integer> ids = (List<Integer>) WillFireCache.get(CommonConst.USER_ARTICLE_LIST + userId.toString());
        if (ids != null) {
            return ids;
        }

        synchronized ((CommonConst.USER_ARTICLE_LIST + userId.toString()).intern()) {
            ids = (List<Integer>) WillFireCache.get(CommonConst.USER_ARTICLE_LIST + userId.toString());
            if (ids != null) {
                return ids;
            } else {
                LambdaQueryChainWrapper<Article> wrapper = new LambdaQueryChainWrapper<>(articleMapper);
                List<Article> articles = wrapper.eq(Article::getUserId, userId).select(Article::getId).list();
                List<Integer> collect = articles.stream().map(Article::getId).collect(Collectors.toList());
                WillFireCache.put(CommonConst.USER_ARTICLE_LIST + userId.toString(), collect, CommonConst.EXPIRE);
                return collect;
            }
        }
    }

    public Integer getCommentCount(Integer source, String type) {
        Integer count = (Integer) WillFireCache.get(CommonConst.COMMENT_COUNT_CACHE + source.toString() + "_" + type);
        if (count != null) {
            return count;
        }
        LambdaQueryChainWrapper<Comment> wrapper = new LambdaQueryChainWrapper<>(commentMapper);
        Integer c = wrapper.eq(Comment::getSource, source).eq(Comment::getType, type).count();
        WillFireCache.put(CommonConst.COMMENT_COUNT_CACHE + source.toString() + "_" + type, c, CommonConst.EXPIRE);
        return c;
    }

    public List<List<Integer>> getArticleIds(String searchText) {
        // 从缓存中获取文章列表
        List<Article> articles = (List<Article>) WillFireCache.get(CommonConst.ARTICLE_LIST);

        // 如果缓存中没有，则从数据库获取
        if (articles == null) {
            synchronized (CommonConst.ARTICLE_LIST.intern()) {
                articles = (List<Article>) WillFireCache.get(CommonConst.ARTICLE_LIST);
                if (articles == null) {
                    LambdaQueryChainWrapper<Article> wrapper = new LambdaQueryChainWrapper<>(articleMapper);
                    articles = wrapper.select(Article::getId, Article::getArticleTitle, Article::getArticleContent)
                            .orderByDesc(Article::getCreateTime)
                            .list();
                    WillFireCache.put(CommonConst.ARTICLE_LIST, articles);
                }
            }
        }

        // 根据搜索文本匹配文章标题和内容
        List<List<Integer>> ids = new ArrayList<>();
        List<Integer> titleIds = new ArrayList<>();
        List<Integer> contentIds = new ArrayList<>();

        for (Article article : articles) {
            if (StringUtil.matchString(article.getArticleTitle(), searchText)) {
                titleIds.add(article.getId());
            } else if (StringUtil.matchString(article.getArticleContent(), searchText)) {
                contentIds.add(article.getId());
            }
        }

        ids.add(titleIds);
        ids.add(contentIds);
        return ids;
    }

    // 获取分类信息
    public List<Sort> getSortInfo() {
        List<Sort> sortInfo = (List<Sort>) WillFireCache.get(CommonConst.SORT_INFO);
        if (sortInfo != null) {
            return sortInfo;
        }

        synchronized (CommonConst.SORT_INFO.intern()) {
            sortInfo = (List<Sort>) WillFireCache.get(CommonConst.SORT_INFO);
            if (sortInfo == null) {
                List<Sort> sorts = new LambdaQueryChainWrapper<>(sortMapper).list();
                if (!CollectionUtils.isEmpty(sorts)) {
                    sorts.forEach(sort -> {
                        LambdaQueryChainWrapper<Article> sortWrapper = new LambdaQueryChainWrapper<>(articleMapper);
                        Integer countOfSort = sortWrapper.eq(Article::getSortId, sort.getId()).count();
                        sort.setCountOfSort(countOfSort);

                        // 获取分类的标签
                        LambdaQueryChainWrapper<Label> wrapper = new LambdaQueryChainWrapper<>(labelMapper);
                        List<Label> labels = wrapper.eq(Label::getSortId, sort.getId()).list();
                        if (!CollectionUtils.isEmpty(labels)) {
                            labels.forEach(label -> {
                                LambdaQueryChainWrapper<Article> labelWrapper = new LambdaQueryChainWrapper<>(articleMapper);
                                Integer countOfLabel = labelWrapper.eq(Article::getLabelId, label.getId()).count();
                                label.setCountOfLabel(countOfLabel);
                            });
                            sort.setLabels(labels);
                        }
                    });
                }
                WillFireCache.put(CommonConst.SORT_INFO, sorts);
                return sorts;
            } else {
                return sortInfo;
            }
        }
    }
}
