package jie.enums;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:02
 * @注释
 */
public enum CodeMsg {
    SUCCESS(200, "成功！"),
    PARAMETER_ERROR(400, "参数异常！"),
    NOT_LOGIN(300, "未登陆，请登陆后再进行操作！"),
    LOGIN_EXPIRED(300, "登录已过期，请重新登陆！"),
    SYSTEM_REPAIR(301, "系统维护中，敬请期待！"),
    FAIL(500, "服务异常！"),
    FILE_OR_RESOURCE_PATH_EMPTY(500, "文件或资源路径不能为空~~"),
    FILE_UPLOAD_ERROR(500, "文件上传失败~~"),
    GET_WEB_INFO_ERROR(500, "获取网站信息错误~~"), USER_STATUS_ERROR(500, "账号异常~~"),
    NOT_ADMIN_ACCOUNT(500, "非管理员账号~~"), USERNAME_PASSWPRD_ERROR(500, "账号密码错误~~"),
    CANT_ROOT_STATUS(500, "站长状态不能修改~~"), CANT_ROOT_TYPE(500, "站长类型不能修改~~"),
    FILE_DELETE_ERROR(500, "资源删除失败~~"),
    SET_ARTICLE_PASSWORD(500, "请设置文章密码"), ARTICLE_NOT_EXIST(500, "文章不存在~~"),
    LABLE_ATACH_ARTICLE(500, "标签关联着文章~~"), SORT_ATACH_LABLE(500, "分类关联着标签"),
    SYS_CONFIG_ERROR(500, "完善配置信息!!"), SYS_CONFIG_TYPE_ERROR(500, "配置类型错误!!"),
    PERMISSION_DENIED(500, "权限不够~~"), COMMENT_CLOSE(500, "评论功能已经关闭~~");
    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
