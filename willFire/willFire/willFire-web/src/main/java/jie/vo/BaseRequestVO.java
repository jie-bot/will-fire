package jie.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/14 上午12:05
 * @注释
 */
@Data
public class BaseRequestVO extends Page {

    private String order;

    private boolean desc = true;

    private Integer source;

    private String commentType;

    private Integer floorCommentId;

    private String searchKey;

    private String articleSearch;

    // 是否推荐[0:否，1:是]
    private Boolean recommendStatus;

    private Integer sortId;

    private Integer labelId;

    private Boolean userStatus;

    private Integer userType;

    private Integer userId;

    private String resourceType;

    private Boolean status;

    private String classify;
}