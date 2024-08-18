package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.entity.Family;
import jie.service.FamilyService;
import jie.utils.CommonQuery;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.vo.BaseRequestVO;
import jie.vo.FamilyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午5:54
 * @注释
 */
@RestController
@RequestMapping("/family")
public class FamilyController {
    @Autowired
    private FamilyService familyService;

    @Autowired
    private CommonQuery commonQuery;

    /**
     * 保存
     * @param familyVO
     * @return
     */
    @PostMapping("/saveFamily")
    @LoginCheck
    public WillFireResult saveFamily(@Validated @RequestBody FamilyVO familyVO) {
        Integer userId = WillFireUtil.getUserId();
        familyVO.setUserId(userId);
        Family oldFamily = familyService.lambdaQuery().select(Family::getId).eq(Family::getUserId, userId).one();
        Family family = new Family();
        BeanUtils.copyProperties(familyVO, family);
        if (userId.intValue() == WillFireUtil.getAdminUser().getId().intValue()) {
            family.setStatus(Boolean.TRUE);
        } else {
            family.setStatus(Boolean.FALSE);
        }

        if (oldFamily != null) {
            family.setId(oldFamily.getId());
            familyService.updateById(family);
        } else {
            family.setId(null);
            familyService.save(family);
        }
        if (userId.intValue() == WillFireUtil.getAdminUser().getId().intValue()) {
            WillFireCache.put(CommonConst.ADMIN_FAMILY, family);
        }
        WillFireCache.remove(CommonConst.FAMILY_LIST);
        return WillFireResult.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/deleteFamily")
    @LoginCheck(0)
    public WillFireResult deleteFamily(@RequestParam("id") Integer id) {
        familyService.removeById(id);
        WillFireCache.remove(CommonConst.FAMILY_LIST);
        return WillFireResult.success();
    }

    /**
     * 获取
     * @return
     */
    @GetMapping("/getFamily")
    @LoginCheck
    public WillFireResult<FamilyVO> getFamily() {
        Integer userId = WillFireUtil.getUserId();
        Family family = familyService.lambdaQuery().eq(Family::getUserId, userId).one();
        if (family == null) {
            return WillFireResult.success();
        } else {
            FamilyVO familyVO = new FamilyVO();
            BeanUtils.copyProperties(family, familyVO);
            return WillFireResult.success(familyVO);
        }
    }

    /**
     * 获取
     * @return
     */
    @GetMapping("/getAdminFamily")
    public WillFireResult<FamilyVO> getAdminFamily() {
        Family family = (Family) WillFireCache.get(CommonConst.ADMIN_FAMILY);
        if (family == null) {
            return WillFireResult.fail("请初始化表白墙");
        }
        FamilyVO familyVO = new FamilyVO();
        BeanUtils.copyProperties(family, familyVO);
        return WillFireResult.success(familyVO);
    }

    /**
     * 查询随机家庭
     * @param size
     * @return
     */
    @GetMapping("/listRandomFamily")
    public WillFireResult<List<FamilyVO>> listRandomFamily(@RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<FamilyVO> familyList = commonQuery.getFamilyList();
        if (familyList.size() > size) {
            Collections.shuffle(familyList);
            familyList = familyList.subList(0, size);
        }
        return WillFireResult.success(familyList);
    }

    /**
     * 查询
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listFamily")
    @LoginCheck(0)
    public WillFireResult<Page> listFamily(@RequestBody BaseRequestVO baseRequestVO) {
        familyService.lambdaQuery()
                .eq(baseRequestVO.getStatus() != null, Family::getStatus, baseRequestVO.getStatus())
                .orderByDesc(Family::getCreateTime).page(baseRequestVO);
        return WillFireResult.success(baseRequestVO);
    }

    /**
     * 修改
     * @param id
     * @param flag
     * @return
     */
    @GetMapping("/changeLoveStatus")
    @LoginCheck(0)
    public WillFireResult changeLoveStatus(@RequestParam("id") Integer id, @RequestParam("flag") Boolean flag) {
        familyService.lambdaUpdate().eq(Family::getId, id).set(Family::getStatus, flag).update();
        WillFireCache.remove(CommonConst.FAMILY_LIST);
        return WillFireResult.success();
    }
}
