package cn.edu.ecust.common.exception;

/**
 * @Description:
 * @Author: Yesheng Xu
 * @Date: Created in 16:46 2022/6/2
 */
public class AppException extends RuntimeException{

    private static final long serialVersionUID = 273298619093831596L;

    private String errCode;

    public AppException(String msg) {
        super(msg);
    }

    public AppException(String msg, String errCode) {
        super(msg);
        this.errCode = errCode;
    }

    public AppException(String msg, Throwable cause) {
        super(msg,cause);
    }

    public AppException(String msg, String errCode, Throwable cause) {
        super(msg, cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
