package jie.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ResourcePathMapper;
import jie.entity.ResourcePath;
import jie.vo.ResourcePathVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午10:07
 * @注释
 */
@RestController
@RequestMapping("/webInfo")
public class CollectController {
    @Autowired
    private ResourcePathMapper resourcePathMapper;

    /**
     * 查询收藏
     */
    @GetMapping("/listCollect")
    public WillFireResult<Map<String, List<ResourcePathVO>>> listCollect() {
        LambdaQueryChainWrapper<ResourcePath> wrapper = new LambdaQueryChainWrapper<>(resourcePathMapper);
        List<ResourcePath> resourcePaths = wrapper.eq(ResourcePath::getType, CommonConst.RESOURCE_PATH_TYPE_FAVORITES)
                .eq(ResourcePath::getStatus, Boolean.TRUE)
                .orderByAsc(ResourcePath::getTitle)
                .list();
        Map<String, List<ResourcePathVO>> collect = new HashMap<>();
        if (!CollectionUtils.isEmpty(resourcePaths)) {
            collect = resourcePaths.stream().map(rp -> {
                ResourcePathVO resourcePathVO = new ResourcePathVO();
                BeanUtils.copyProperties(rp, resourcePathVO);
                return resourcePathVO;
            }).collect(Collectors.groupingBy(ResourcePathVO::getClassify));
        }
        return WillFireResult.success(collect);
    }
}
