package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.TreeHoleMapper;
import jie.entity.TreeHole;
import jie.utils.WillFireUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/16 下午10:56
 * @注释
 */

@RestController
@RequestMapping("/webInfo")
public class TreeHoleController {
    @Autowired
    private TreeHoleMapper treeHoleMapper;

    /**
     * 保存
     */
    @PostMapping("/saveTreeHole")
    @SaveCheck
    public WillFireResult<TreeHole> saveTreeHole(@RequestBody TreeHole treeHole) {
        if (!StringUtils.hasText(treeHole.getMessage())) {
            return WillFireResult.fail("留言不能为空！");
        }
        treeHoleMapper.insert(treeHole);
        if (!StringUtils.hasText(treeHole.getAvatar())) {
            treeHole.setAvatar(WillFireUtil.getRandomAvatar(null));
        }
        return WillFireResult.success(treeHole);
    }


    /**
     * 删除
     */
    @GetMapping("/deleteTreeHole")
    @LoginCheck(0)
    public WillFireResult deleteTreeHole(@RequestParam("id") Integer id) {
        treeHoleMapper.deleteById(id);
        return WillFireResult.success();
    }


    /**
     * 查询List
     */
    @GetMapping("/listTreeHole")
    public WillFireResult<List<TreeHole>> listTreeHole() {
        List<TreeHole> treeHoles;
        Integer count = new LambdaQueryChainWrapper<>(treeHoleMapper).count();
        if (count > CommonConst.TREE_HOLE_COUNT) {
            int i = new Random().nextInt(count + 1 - CommonConst.TREE_HOLE_COUNT);
            treeHoles = treeHoleMapper.queryAllByLimit(i, CommonConst.TREE_HOLE_COUNT);
        } else {
            treeHoles = new LambdaQueryChainWrapper<>(treeHoleMapper).list();
        }

        treeHoles.forEach(treeHole -> {
            if (!StringUtils.hasText(treeHole.getAvatar())) {
                treeHole.setAvatar(WillFireUtil.getRandomAvatar(treeHole.getId().toString()));
            }
        });
        return WillFireResult.success(treeHoles);
    }
}
