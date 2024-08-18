package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.service.ArticleService;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.vo.ArticleVO;
import jie.vo.BaseRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:42
 * @注释
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询分类文章List
     * @return
     */
    @GetMapping("/listSortArticle")
    public WillFireResult<Map<Integer, List<ArticleVO>>> listSortArticle() {
        Map<Integer, List<ArticleVO>> map = articleService.listSortArticle();
        return WillFireResult.success(map);
    }

    /**
     * 获取文章列表
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listArticle")
    public WillFireResult<Page> listArticle(@RequestBody BaseRequestVO baseRequestVO) {
        Page page = articleService.listArticle(baseRequestVO);
        return WillFireResult.success(page);
    }

    /**
     * 保存文章
     * @param articleVO
     * @return
     */
    @PostMapping("/saveArticle")
    @LoginCheck(1)
    public WillFireResult saveArticle(@Validated @RequestBody ArticleVO articleVO) {
        // 刷新缓存
        WillFireCache.remove(CommonConst.USER_ARTICLE_LIST + WillFireUtil.getUserId().toString());
        WillFireCache.remove(CommonConst.ARTICLE_LIST);
        WillFireCache.remove(CommonConst.SORT_ARTICLE_LIST);
        articleService.saveArticle(articleVO);
        return WillFireResult.success();
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @GetMapping("/deleteArticle")
    @LoginCheck(1)
    public WillFireResult deleteArticle(@RequestParam("id") Integer id) {
        // 刷新缓存
        WillFireCache.remove(CommonConst.USER_ARTICLE_LIST + WillFireUtil.getUserId().toString());
        WillFireCache.remove(CommonConst.ARTICLE_LIST);
        WillFireCache.remove(CommonConst.SORT_ARTICLE_LIST);
        articleService.deleteArticle(id);
        return WillFireResult.success();
    }

    /**
     * 更新文章信息
     * @param articleVO
     * @return
     */
    @PostMapping("/updateArticle")
    @LoginCheck(1)
    public WillFireResult updateArticle(@Validated @RequestBody ArticleVO articleVO) {
        WillFireCache.remove(CommonConst.ARTICLE_LIST);
        WillFireCache.remove(CommonConst.SORT_ARTICLE_LIST);
        articleService.updateArticle(articleVO);
        return WillFireResult.success();
    }

    /**
     * id查询文章
     * @param id
     * @param password
     * @return
     */
    @GetMapping("/getArticleById")
    public WillFireResult<ArticleVO> getArticleById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password) {
        ArticleVO vo = articleService.getArticleById(id, password);
        return WillFireResult.success(vo);
    }
}
