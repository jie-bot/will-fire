package jie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jie.config.WillFireResult;
import jie.entity.Article;
import jie.vo.ArticleVO;
import jie.vo.BaseRequestVO;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:47
 * @注释
 */
public interface ArticleService extends IService<Article> {
    Map<Integer, List<ArticleVO>> listSortArticle();

    Page listArticle(BaseRequestVO baseRequestVO);

    WillFireResult<Page> listAdminArticle(BaseRequestVO baseRequestVO, boolean b);

    void saveArticle(ArticleVO articleVO);

    void deleteArticle(Integer id);

    ArticleVO getArticleByIdForUser(Integer id);

    void updateArticle(ArticleVO articleVO);

    ArticleVO getArticleById(Integer id, String password);
}
