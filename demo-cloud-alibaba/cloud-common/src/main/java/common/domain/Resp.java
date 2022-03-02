package common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
@Data
public class Resp<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;

    public static<T> Resp<T> ok() {
        return result(200, "ok", null);
    }

    public static<T> Resp<T> ok(String msg, T data) {
        return result(200, msg, data);
    }

    public static<T> Resp<T> ok(T data) {
        return result(200, "ok", data);
    }

    public static<T> Resp<T> fail(String msg) {
        return result(500, msg, null);
    }

    public static<T> Resp<T> fail(Integer code, String msg) {
        return result(code, msg, null);
    }

    private static <T> Resp<T> result(Integer code, String msg, T data) {
        Resp<T> resp = new Resp<T>();
        resp.setCode(code);
        resp.setMsg(msg);
        resp.setData(data);
        return resp;
    }
}
