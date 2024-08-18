package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ResourcePathMapper;
import jie.entity.ResourcePath;
import jie.handle.WillFireRuntimeException;
import jie.vo.ResourcePathVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午6:52
 * @注释
 */
@RequestMapping("/webInfo")
@RestController
public class MusicController {

    @Autowired
    private ResourcePathMapper resourcePathMapper;

    /**
     * 查询音乐列表
     * @return
     */
    @GetMapping("/listFunny")
    public WillFireResult<List<Map<String, Object>>> listFunny() {
        QueryWrapper<ResourcePath> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("classify, count(*) as count")
                .eq("status", Boolean.TRUE)
                .eq("type", CommonConst.RESOURCE_PATH_TYPE_FUNNY)
                .groupBy("classify");
        List<Map<String, Object>> maps = resourcePathMapper.selectMaps(queryWrapper);
        return WillFireResult.success(maps);
    }

    /**
     * 保存音乐
     * @param resourcePathVO
     * @return
     */
    @LoginCheck
    @SaveCheck
    @PostMapping("/saveFunny")
    public WillFireResult saveFunny(@RequestBody ResourcePathVO resourcePathVO) {
        if (!StringUtils.hasText(resourcePathVO.getClassify()) || !StringUtils.hasText(resourcePathVO.getCover()) ||
                !StringUtils.hasText(resourcePathVO.getUrl()) || !StringUtils.hasText(resourcePathVO.getTitle())) {
            throw new WillFireRuntimeException("信息不全", 500);
        }
        ResourcePath funny = new ResourcePath();
        funny.setClassify(resourcePathVO.getClassify());
        funny.setTitle(resourcePathVO.getTitle());
        funny.setCover(resourcePathVO.getCover());
        funny.setUrl(resourcePathVO.getUrl());
        funny.setType(CommonConst.RESOURCE_PATH_TYPE_FUNNY);
        funny.setStatus(Boolean.FALSE);
        resourcePathMapper.insert(funny);
        return WillFireResult.success();
    }
}
