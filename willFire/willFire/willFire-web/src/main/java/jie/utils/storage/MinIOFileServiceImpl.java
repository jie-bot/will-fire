package jie.utils.storage;

import jie.constants.CommonConst;
import jie.entity.Resource;
import jie.enums.CodeMsg;
import jie.handle.WillFireRuntimeException;
import jie.service.ResourceService;
import jie.vo.FileVO;
import io.minio.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午11:46
 * @注释
 */
@Slf4j
@Service(value = CommonConst.MIN_IO)
public class MinIOFileServiceImpl implements FileStorageService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value(value = "${minio.endpoint}")
    private String endpoint;

    @Override
    public FileVO saveFile(FileVO fileVO) {
        try {
            createBucket();
            String fileName = getRandomFileName(fileVO.getFile().getInputStream());
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .contentType(fileVO.getFile().getContentType())
                    .bucket(bucketName)
                    .stream(fileVO.getFile().getInputStream(),fileVO.getFile().getSize(),0)
                    .object(fileName)
                    .build();
            minioClient.putObject(objectArgs);
            String url = endpoint + "/" + bucketName + "/" + fileName;
            FileVO result = new FileVO();
            BeanUtils.copyProperties(fileVO, result);
            result.setVisitPath(url);
            return result;
        }catch (Exception e){
            log.error("上传文件失败！");
            log.error(e.getMessage());
            throw new WillFireRuntimeException(CodeMsg.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public void deleteFile(List<String> filePaths) {
        if (CollectionUtils.isEmpty(filePaths)) {
            return;
        }

        for (String path : filePaths) {
            String name = path.substring(path.lastIndexOf("/") + 1);
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(name).build();
            try {
                minioClient.removeObject(removeObjectArgs);
                resourceService.lambdaUpdate().eq(Resource::getPath, path).remove();
            } catch (Exception e) {
                log.error("删除文件错误:" + e.getMessage());
                throw new WillFireRuntimeException(CodeMsg.FILE_DELETE_ERROR);
            }
        }
    }

    @SneakyThrows
    private void createBucket()  {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    private String getRandomFileName(InputStream inputStream) {
        String s = null;
        try {
            s = DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s;
    }


}
