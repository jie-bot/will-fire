package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ArticleMapper;
import jie.dao.LabelMapper;
import jie.dao.SortMapper;
import jie.entity.Article;
import jie.entity.Label;
import jie.entity.Sort;
import jie.enums.CodeMsg;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.SortService;
import jie.utils.CommonQuery;
import jie.utils.cache.WillFireCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 下午10:30
 * @注释 分类和标签
 */
@RequestMapping("/webInfo")
@RestController
public class SortLabelController {

    @Autowired
    private SortService sortService;
    @Autowired
    private CommonQuery commonQuery;
    @Autowired
    private SortMapper sortMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取分类信息
     * @return
     */
    @GetMapping("/getSortInfo")
    public WillFireResult<List<Sort>> getSortInfo() {
        List<Sort> list = commonQuery.getSortInfo();
        return WillFireResult.success(list);
    }

    /**
     * 获取分类和标签信息
     * @return
     */
    @GetMapping("/listSortAndLabel")
    public WillFireResult<Map> listSortAndLabel() {
        Map<String, List> map = new HashMap<>();
        map.put("sorts", new LambdaQueryChainWrapper<>(sortMapper).list());
        map.put("labels", new LambdaQueryChainWrapper<>(labelMapper).list());
        return WillFireResult.success(map);
    }

    /**
     * 获取标签信息
     * @return
     */
    @GetMapping("/listLabel")
    public WillFireResult<List<Label>> listLabel() {
        return WillFireResult.success(new LambdaQueryChainWrapper<>(labelMapper).list());
    }

    /**
     * 更新标签信息
     * @param label
     * @return
     */
    @PostMapping("/updateLabel")
    @LoginCheck(0)
    public WillFireResult updateLabel(@RequestBody Label label) {
        labelMapper.updateById(label);
        // 刷新缓存的标签内容
        WillFireCache.remove(CommonConst.SORT_INFO);
        return WillFireResult.success();
    }

    /**
     * 删除标签信息
     * @param id
     * @return
     */
    @GetMapping("/deleteLabel")
    @LoginCheck(0)
    public WillFireResult deleteLabel(@RequestParam("id") Integer id) {
        // 先判断该标签是否关联着文章
        // 获取文章的标签Id
        LambdaQueryWrapper<Article> aw = new LambdaQueryWrapper<>();
        aw.eq(Article::getDeleted, WillFireEnum.STATUS_ENABLE).eq(Article::getLabelId, id);
        List<Article> articles = articleMapper.selectList(aw);
        if (!CollectionUtils.isEmpty(articles)) {
            throw new WillFireRuntimeException(CodeMsg.LABLE_ATACH_ARTICLE);
        }
        labelMapper.deleteById(id);
        // 刷新标签缓存
        WillFireCache.remove(CommonConst.SORT_INFO);
        return WillFireResult.success();
    }

    /**
     * 保存标签
     * @param label
     * @return
     */
    @PostMapping("/saveLabel")
    @LoginCheck(0)
    public WillFireResult saveLabel(@RequestBody Label label) {
        if (!StringUtils.hasText(label.getLabelName()) || !StringUtils.hasText(label.getLabelDescription()) || label.getSortId() == null) {
            return WillFireResult.fail("标签名称和标签描述和分类Id不能为空！");
        }
        labelMapper.insert(label);
        // 刷新缓存
        WillFireCache.remove(CommonConst.SORT_INFO);
        return WillFireResult.success();
    }

    /**
     * 获取分类信息
     * @return
     */
    @GetMapping("/listSort")
    public WillFireResult<List<Sort>> listSort() {
        return WillFireResult.success(new LambdaQueryChainWrapper<>(sortMapper).list());
    }

    /**
     * 更新分类信息
     * @param sort
     * @return
     */
    @PostMapping("/updateSort")
    @LoginCheck(0)
    public WillFireResult updateSort(@RequestBody Sort sort) {
        sortMapper.updateById(sort);
        // 刷新缓存
        WillFireCache.remove(CommonConst.SORT_INFO);
        return WillFireResult.success();
    }

    /**
     * 删除分类信息
     * @param id
     * @return
     */
    @GetMapping("/deleteSort")
    @LoginCheck(0)
    public WillFireResult deleteSort(@RequestParam("id") Integer id) {
        // 先判断有没有标签关联着分类
        LambdaQueryWrapper<Label> aw = new LambdaQueryWrapper<>();
        aw.eq(Label::getSortId, id);
        List<Label> labels = labelMapper.selectList(aw);
        if (!CollectionUtils.isEmpty(labels)) {
            throw new WillFireRuntimeException(CodeMsg.SORT_ATACH_LABLE);
        }
        sortMapper.deleteById(id);
        // 刷新缓存
        WillFireCache.remove(CommonConst.SORT_INFO);
        return WillFireResult.success();
    }

    /**
     * 保存分类信息
     * @param sort
     * @return
     */
    @PostMapping("/saveSort")
    @LoginCheck(0)
    public WillFireResult saveSort(@RequestBody Sort sort) {
        // 条件判断
        if (!StringUtils.hasText(sort.getSortName()) || !StringUtils.hasText(sort.getSortDescription())) {
            return WillFireResult.fail("分类名称和分类描述不能为空！");
        }
        if (sort.getPriority() == null) {
            return WillFireResult.fail("分类必须配置优先级！");
        }

        sortMapper.insert(sort);
        // 刷新缓存
        WillFireCache.remove(CommonConst.SORT_INFO);
        return WillFireResult.success();
    }

}
