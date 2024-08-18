package jie.controller;

import cn.hutool.core.util.StrUtil;
import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ResourcePathMapper;
import jie.entity.ResourcePath;
import jie.utils.WillFireUtil;
import jie.vo.BaseRequestVO;
import jie.vo.ResourcePathVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午9:43
 * @注释
 */
@RestController
@RequestMapping("/webInfo")
public class ResourceAggregationController {
    @Autowired
    private ResourcePathMapper resourcePathMapper;

    /**
     * 保存
     */
    @LoginCheck(0)
    @PostMapping("/saveResourcePath")
    public WillFireResult saveResourcePath(@RequestBody ResourcePathVO resourcePathVO) {
        if (!StringUtils.hasText(resourcePathVO.getTitle()) || !StringUtils.hasText(resourcePathVO.getType())) {
            return WillFireResult.fail("标题和资源类型不能为空！");
        }
        if (CommonConst.RESOURCE_PATH_TYPE_LOVE_PHOTO.equals(resourcePathVO.getType())) {
            resourcePathVO.setRemark(WillFireUtil.getAdminUser().getId().toString());
        }
        ResourcePath resourcePath = new ResourcePath();
        BeanUtils.copyProperties(resourcePathVO, resourcePath);
        resourcePathMapper.insert(resourcePath);
        return WillFireResult.success();
    }

    /**
     * 删除
     */
    @GetMapping("/deleteResourcePath")
    @LoginCheck(0)
    public WillFireResult deleteResourcePath(@RequestParam("id") Integer id) {
        resourcePathMapper.deleteById(id);
        return WillFireResult.success();
    }

    /**
     * 更新
     */
    @PostMapping("/updateResourcePath")
    @LoginCheck(0)
    public WillFireResult updateResourcePath(@RequestBody ResourcePathVO resourcePathVO) {
        if (!StringUtils.hasText(resourcePathVO.getTitle()) || !StringUtils.hasText(resourcePathVO.getType())) {
            return WillFireResult.fail("标题和资源类型不能为空！");
        }
        if (resourcePathVO.getId() == null) {
            return WillFireResult.fail("Id不能为空！");
        }
        if (CommonConst.RESOURCE_PATH_TYPE_LOVE_PHOTO.equals(resourcePathVO.getType())) {
            resourcePathVO.setRemark(WillFireUtil.getAdminUser().getId().toString());
        }
        ResourcePath resourcePath = new ResourcePath();
        BeanUtils.copyProperties(resourcePathVO, resourcePath);
        resourcePathMapper.updateById(resourcePath);
        return WillFireResult.success();
    }


    /**
     * 查询资源
     */
    @PostMapping("/listResourcePath")
    public WillFireResult<Page> listResourcePath(@RequestBody BaseRequestVO baseRequestVO) {
        LambdaQueryChainWrapper<ResourcePath> wrapper = new LambdaQueryChainWrapper<>(resourcePathMapper);
        wrapper.eq(StringUtils.hasText(baseRequestVO.getResourceType()), ResourcePath::getType, baseRequestVO.getResourceType());
        wrapper.eq(StringUtils.hasText(baseRequestVO.getClassify()), ResourcePath::getClassify, baseRequestVO.getClassify());

        Integer userId = WillFireUtil.getUserId();
        if (!WillFireUtil.getAdminUser().getId().equals(userId)) {
            wrapper.eq(ResourcePath::getStatus, Boolean.TRUE);
        } else {
            wrapper.eq(baseRequestVO.getStatus() != null, ResourcePath::getStatus, baseRequestVO.getStatus());
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(StringUtils.hasText(baseRequestVO.getOrder()) ? StrUtil.toUnderlineCase(baseRequestVO.getOrder()) : "create_time");
        orderItem.setAsc(!baseRequestVO.isDesc());
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        baseRequestVO.setOrders(orderItemList);

        wrapper.page(baseRequestVO);

        List<ResourcePath> resourcePaths = baseRequestVO.getRecords();
        if (!CollectionUtils.isEmpty(resourcePaths)) {
            List<ResourcePathVO> resourcePathVOs = resourcePaths.stream().map(rp -> {
                ResourcePathVO resourcePathVO = new ResourcePathVO();
                BeanUtils.copyProperties(rp, resourcePathVO);
                return resourcePathVO;
            }).collect(Collectors.toList());
            baseRequestVO.setRecords(resourcePathVOs);
        }
        return WillFireResult.success(baseRequestVO);
    }
}
