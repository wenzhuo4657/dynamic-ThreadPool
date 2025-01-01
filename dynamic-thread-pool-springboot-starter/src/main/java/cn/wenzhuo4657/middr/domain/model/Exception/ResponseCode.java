package cn.wenzhuo4657.middr.domain.model.Exception;

/**
 * @author: wenzhuo4657
 * @date: 2025/1/1
 * @description:
 */
public enum ResponseCode {
    ApplicationName_ERROR("ERR_BIZ_001","缺少必要参数，应用名称")
    ;

    private String code;
    private String info;

    ResponseCode(String code, String info) {
        this.code=code;
        this.info=info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
