package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ArticleMapper;
import jie.entity.Article;
import jie.entity.WeiYan;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.WeiYanService;
import jie.utils.StringUtil;
import jie.utils.WillFireUtil;
import jie.vo.BaseRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 上午12:06
 * @注释
 */

@RestController
@RequestMapping("/weiYan")
public class WeiYanController {

    @Autowired
    private WeiYanService weiYanService;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 查询最新进展
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listNews")
    public WillFireResult<BaseRequestVO> listNews(@RequestBody BaseRequestVO baseRequestVO) {
        if (baseRequestVO.getSource() == null) {
            throw new WillFireRuntimeException("来源类型不能为空", 500);
        }
        LambdaQueryChainWrapper<WeiYan> lambdaQuery = weiYanService.lambdaQuery();
        lambdaQuery.eq(WeiYan::getType, CommonConst.WEIYAN_TYPE_NEWS);
        lambdaQuery.eq(WeiYan::getSource, baseRequestVO.getSource());
        lambdaQuery.eq(WeiYan::getIsPublic, WillFireEnum.PUBLIC.getCode());

        lambdaQuery.orderByDesc(WeiYan::getCreateTime).page(baseRequestVO);
        return WillFireResult.success(baseRequestVO);
    }

    /**
     * 保存最新进展
     * @param weiYanVO
     * @return
     */
    @PostMapping("/saveNews")
    @LoginCheck
    public WillFireResult saveNews(@RequestBody WeiYan weiYanVO) {
        if (!StringUtils.hasText(weiYanVO.getContent()) || weiYanVO.getSource() == null) {
            throw new WillFireRuntimeException("信息不足!", 500);
        }

        if (weiYanVO.getCreateTime() == null) {
            weiYanVO.setCreateTime(LocalDateTime.now());
        }

        Integer userId = WillFireUtil.getUserId();
        LambdaQueryChainWrapper<Article> wrapper = new LambdaQueryChainWrapper<>(articleMapper);
        Integer count = wrapper.eq(Article::getId, weiYanVO.getSource()).eq(Article::getUserId, userId).count();

        if (count == null || count < 1) {
            throw new WillFireRuntimeException("来源不存在!", 500);
        }

        WeiYan weiYan = new WeiYan();
        weiYan.setUserId(userId);
        weiYan.setContent(weiYanVO.getContent());
        weiYan.setIsPublic(Boolean.TRUE);
        weiYan.setSource(weiYanVO.getSource());
        weiYan.setCreateTime(weiYanVO.getCreateTime());
        weiYan.setType(CommonConst.WEIYAN_TYPE_NEWS);
        weiYanService.save(weiYan);
        return WillFireResult.success();
    }

    /**
     * 保存微言
     * @param weiYanVO
     * @return
     */
    @PostMapping("/saveWeiYan")
    @LoginCheck
    @SaveCheck
    public WillFireResult saveWeiYan(@RequestBody WeiYan weiYanVO) {
        if (!StringUtils.hasText(weiYanVO.getContent())) {
            throw new WillFireRuntimeException("微言不能为空~~", 500);
        }

        String content = StringUtil.removeHtml(weiYanVO.getContent());
        if (!StringUtils.hasText(content)) {
            throw new WillFireRuntimeException("内容不能为空~~", 500);
        }
        weiYanVO.setContent(content);

        WeiYan weiYan = new WeiYan();
        weiYan.setUserId(WillFireUtil.getUserId());
        weiYan.setContent(weiYanVO.getContent());
        weiYan.setIsPublic(weiYanVO.getIsPublic());
        weiYan.setType(CommonConst.WEIYAN_TYPE_FRIEND);
        weiYanService.save(weiYan);
        return WillFireResult.success();
    }

    /**
     * 删除微言
     * @param id
     * @return
     */
    @GetMapping("/deleteWeiYan")
    @LoginCheck
    public WillFireResult deleteWeiYan(@RequestParam("id") Integer id) {
        Integer userId = WillFireUtil.getUserId();
        weiYanService.lambdaUpdate().eq(WeiYan::getId, id)
                .eq(WeiYan::getUserId, userId)
                .remove();
        return WillFireResult.success();
    }

    /**
     * 查询微言列表
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listWeiYan")
    public WillFireResult<BaseRequestVO> listWeiYan(@RequestBody BaseRequestVO baseRequestVO) {
        LambdaQueryChainWrapper<WeiYan> lambdaQuery = weiYanService.lambdaQuery();
        lambdaQuery.eq(WeiYan::getType, CommonConst.WEIYAN_TYPE_FRIEND);
        if (baseRequestVO.getUserId() == null) {
            if (WillFireUtil.getUserId() != null) {
                lambdaQuery.eq(WeiYan::getUserId, WillFireUtil.getUserId());
            } else {
                lambdaQuery.eq(WeiYan::getIsPublic, WillFireEnum.PUBLIC.getCode());
                lambdaQuery.eq(WeiYan::getUserId, WillFireUtil.getAdminUser().getId());
            }
        } else {
            if (!baseRequestVO.getUserId().equals(WillFireUtil.getUserId())) {
                lambdaQuery.eq(WeiYan::getIsPublic, WillFireEnum.PUBLIC.getCode());
            }
            lambdaQuery.eq(WeiYan::getUserId, baseRequestVO.getUserId());
        }

        lambdaQuery.orderByDesc(WeiYan::getCreateTime).page(baseRequestVO);
        return WillFireResult.success(baseRequestVO);
    }
}
