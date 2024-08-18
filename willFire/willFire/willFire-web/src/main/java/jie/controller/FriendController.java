package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ResourcePathMapper;
import jie.entity.ResourcePath;
import jie.handle.WillFireRuntimeException;
import jie.utils.WillFireUtil;
import jie.vo.ResourcePathVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午6:47
 * @注释
 */

@RestController
@RequestMapping("/webInfo")
public class FriendController {

    @Autowired
    private ResourcePathMapper resourcePathMapper;

    /**
     * 保存友链
     * @param resourcePathVO
     * @return
     */
    @LoginCheck
    @PostMapping("/saveFriend")
    @SaveCheck
    public WillFireResult saveFriend(@RequestBody ResourcePathVO resourcePathVO) {
        if (!StringUtils.hasText(resourcePathVO.getTitle()) || !StringUtils.hasText(resourcePathVO.getCover()) ||
                !StringUtils.hasText(resourcePathVO.getUrl()) || !StringUtils.hasText(resourcePathVO.getIntroduction())) {
            throw new WillFireRuntimeException("信息不全~~", 500);
        }
        ResourcePath friend = new ResourcePath();
        friend.setClassify(CommonConst.DEFAULT_FRIEND);
        friend.setTitle(resourcePathVO.getTitle());
        friend.setIntroduction(resourcePathVO.getIntroduction());
        friend.setCover(resourcePathVO.getCover());
        friend.setUrl(resourcePathVO.getUrl());
        friend.setRemark(WillFireUtil.getUserId().toString());
        friend.setType(CommonConst.RESOURCE_PATH_TYPE_FRIEND);
        friend.setStatus(Boolean.FALSE);
        resourcePathMapper.insert(friend);
        return WillFireResult.success();
    }

    /**
     * 查询友链
     * @return
     */
    @GetMapping("/listFriend")
    public WillFireResult<Map<String, List<ResourcePathVO>>> listFriend() {
        LambdaQueryChainWrapper<ResourcePath> wrapper = new LambdaQueryChainWrapper<>(resourcePathMapper);
        List<ResourcePath> resourcePaths = wrapper.eq(ResourcePath::getType, CommonConst.RESOURCE_PATH_TYPE_FRIEND)
                .eq(ResourcePath::getStatus, Boolean.TRUE)
                .orderByAsc(ResourcePath::getCreateTime)
                .list();
        // 查询友链
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
