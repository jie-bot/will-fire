package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.ResourcePathMapper;
import jie.entity.ResourcePath;
import jie.utils.WillFireUtil;
import jie.vo.ResourcePathVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午5:59
 * @注释
 */

@RestController
@RequestMapping("/webInfo")
public class PictureController {

    @Autowired
    private ResourcePathMapper resourcePathMapper;

    /**
     * 查询收藏图片
     */
    @GetMapping("/listAdminLovePhoto")
    public WillFireResult<List<Map<String, Object>>> listAdminLovePhoto() {
        QueryWrapper<ResourcePath> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("classify, count(*) as count")
                .eq("status", Boolean.TRUE)
                .eq("remark", WillFireUtil.getAdminUser().getId().toString())
                .eq("type", CommonConst.RESOURCE_PATH_TYPE_LOVE_PHOTO)
                .groupBy("classify");
        List<Map<String, Object>> maps = resourcePathMapper.selectMaps(queryWrapper);

        return WillFireResult.success(maps);
    }

    /**
     * 保存
     */
    @LoginCheck
    @SaveCheck
    @PostMapping("/saveLovePhoto")
    public WillFireResult saveLovePhoto(@RequestBody ResourcePathVO resourcePathVO) {
        if (!StringUtils.hasText(resourcePathVO.getClassify()) || !StringUtils.hasText(resourcePathVO.getCover()) ||
                !StringUtils.hasText(resourcePathVO.getTitle())) {
            return WillFireResult.fail("信息不全！");
        }
        ResourcePath lovePhoto = new ResourcePath();
        lovePhoto.setClassify(resourcePathVO.getClassify());
        lovePhoto.setTitle(resourcePathVO.getTitle());
        lovePhoto.setCover(resourcePathVO.getCover());
        lovePhoto.setRemark(WillFireUtil.getUserId().toString());
        lovePhoto.setType(CommonConst.RESOURCE_PATH_TYPE_LOVE_PHOTO);
        lovePhoto.setStatus(Boolean.FALSE);
        resourcePathMapper.insert(lovePhoto);
        return WillFireResult.success();
    }
}