package cn.wenzhuo4657.middr.domain.model.Exception;

/**
 * @author: wenzhuo4657
 * @date: 2025/1/1
 * @description:
 */
public class DynamicThreadPoolAppException extends RuntimeException {

    private static final long serialVersionUID = 5317680961212299217L;

    /** 异常码 */
    private String code;

    /** 异常信息 */
    private String info;

    public DynamicThreadPoolAppException(String code) {
        this.code = code;
    }

    public DynamicThreadPoolAppException(String code, Throwable cause) {
        this.code = code;
        super.initCause(cause);
    }

    public DynamicThreadPoolAppException(String code, String message) {
        this.code = code;
        this.info = message;
    }

    public DynamicThreadPoolAppException(String code, String message, Throwable cause) {
        this.code = code;
        this.info = message;
        super.initCause(cause);
    }

    @Override
    public String toString() {
        return "cn.wenzhuo4657.BigMarket.types.exception.AppException.{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }


}

