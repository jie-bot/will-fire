package jie.controller;

import jie.aop.LoginCheck;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.entity.Resource;
import jie.enums.CodeMsg;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.ResourceService;
import jie.utils.WillFireUtil;
import jie.utils.storage.FileStorageService;
import jie.vo.BaseRequestVO;
import jie.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午11:23
 * @注释 资源管理
 */
@RequestMapping("/resource")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 上传文件
     * @param file
     * @param fileVO
     * @return
     */
    @PostMapping("/upload")
    @LoginCheck(value = 2)
    public WillFireResult<String> upload(@RequestParam("file") MultipartFile file, FileVO fileVO) {
        if (file == null || !StringUtils.hasText(fileVO.getType()) || !StringUtils.hasText(fileVO.getRelativePath())) {
            throw new WillFireRuntimeException(CodeMsg.FILE_OR_RESOURCE_PATH_EMPTY);
        }

        fileVO.setFile(file);
        String storeType = fileVO.getStoreType();
        FileStorageService storeService = applicationContext.getBean(storeType, FileStorageService.class);
        FileVO result = storeService.saveFile(fileVO);

        Resource re = new Resource();
        re.setPath(result.getVisitPath());
        re.setType(fileVO.getType());
        re.setSize(Integer.valueOf(Long.toString(file.getSize())));
        re.setMimeType(file.getContentType());
        re.setStoreType(fileVO.getStoreType());
        re.setOriginalName(fileVO.getOriginalName());
        re.setUserId(WillFireUtil.getUserId());
        resourceService.save(re);
        return WillFireResult.success(result.getVisitPath());
    }

    /**
     * 查询资源
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listResource")
    @LoginCheck(0)
    public WillFireResult<Page> listResource(@RequestBody BaseRequestVO baseRequestVO) {
        resourceService.lambdaQuery()
                .eq(StringUtils.hasText(baseRequestVO.getResourceType()), Resource::getType, baseRequestVO.getResourceType())
                .orderByDesc(Resource::getCreateTime).page(baseRequestVO);
        return WillFireResult.success(baseRequestVO);
    }

    /**
     * 修改资源状态
     * @param id
     * @param flag
     * @return
     */
    @GetMapping("/changeResourceStatus")
    @LoginCheck(0)
    public WillFireResult changeResourceStatus(@RequestParam("id") Integer id, @RequestParam("flag") Boolean flag) {
        resourceService.lambdaUpdate().eq(Resource::getId, id).set(Resource::getStatus, flag).update();
        return WillFireResult.success();
    }

    /**
     * 表情包
     * @return
     */
    @GetMapping("/getImageList")
    @LoginCheck
    public WillFireResult<List<String>> getImageList() {
        List<Resource> list = resourceService.lambdaQuery().select(Resource::getPath)
                .eq(Resource::getType, CommonConst.PATH_TYPE_INTERNET_MEME)
                .eq(Resource::getStatus, WillFireEnum.STATUS_ENABLE.getCode())
                .eq(Resource::getUserId, WillFireUtil.getAdminUser().getId())
                .orderByDesc(Resource::getCreateTime)
                .list();
        List<String> paths = list.stream().map(Resource::getPath).collect(Collectors.toList());
        return WillFireResult.success(paths);
    }

    /**
     * 删除
     * @param path
     * @return
     */
    @PostMapping("/deleteResource")
    @LoginCheck(0)
    public WillFireResult deleteResource(@RequestParam("path") String path) {
        Resource resource = resourceService.lambdaQuery().select(Resource::getStoreType).eq(Resource::getPath, path).one();
        if (resource == null) {
            return WillFireResult.fail("文件不存在：" + path);
        }
        FileStorageService storeService = applicationContext.getBean(resource.getStoreType(), FileStorageService.class);
        storeService.deleteFile(Collections.singletonList(path));
        return WillFireResult.success();
    }

    /**
     * 保存
     * @param resource
     * @return
     */
    @PostMapping("/saveResource")
    @LoginCheck
    public WillFireResult saveResource(@RequestBody Resource resource) {
        if (!StringUtils.hasText(resource.getType()) || !StringUtils.hasText(resource.getPath())) {
            return WillFireResult.fail("资源类型和资源路径不能为空！");
        }
        Resource re = new Resource();
        re.setPath(resource.getPath());
        re.setType(resource.getType());
        re.setSize(resource.getSize());
        re.setOriginalName(resource.getOriginalName());
        re.setMimeType(resource.getMimeType());
        re.setStoreType(resource.getStoreType());
        re.setUserId(WillFireUtil.getUserId());
        resourceService.save(re);
        return WillFireResult.success();
    }
}
