package jie.utils.storage;

import jie.vo.FileVO;

import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午11:40
 * @注释
 */
public interface FileStorageService {

    FileVO saveFile(FileVO fileVO);

    void deleteFile(List<String> strings);
}
