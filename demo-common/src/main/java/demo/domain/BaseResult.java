package demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> {

    private Integer code;

    private String msg;

    private T data;

    public static BaseResult ok() {
        return create(BaseCode.OK, null);
    }

    public static BaseResult ok(String msg) {
        return create(BaseCode.OK.getCode(), msg, null);
    }

    public static BaseResult customErr() {
        return create(BaseCode.CUSTOM_ERR, null);
    }

    public static BaseResult customErr(String msg) {
        return create(BaseCode.CUSTOM_ERR.getCode(), msg, null);
    }

    public static BaseResult sysErr() {
        return create(BaseCode.SYS_ERR, null);
    }

    public static BaseResult sysErr(String msg) {
        return create(BaseCode.SYS_ERR.getCode(), msg, null);
    }

    public static <T> BaseResult create(BaseCode baseCode, T data) {
        return new BaseResult(baseCode.getCode(), baseCode.getMsg(), data);
    }

    public static <T> BaseResult create(Integer code, String msg, T data) {
        return new BaseResult(code, msg, data);
    }

    @Getter
    @AllArgsConstructor
    public enum BaseCode {

        OK(200, "ok"),
        CUSTOM_ERR(400, "客户端异常"),
        SYS_ERR(500, "系统繁忙,请稍后再试!");

        private final Integer code;
        private final String msg;
    }
}
