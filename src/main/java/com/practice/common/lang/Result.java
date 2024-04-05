package com.practice.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于异步统一返回的结果封装。包含：
 * <p>
 * - 是否成功，可用code表示（如200表示成功，400表示异常）
 * - 结果消息
 * - 结果数据
 */
@Data
public class Result implements Serializable {
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 400;

    private Integer code;
    private String msg;
    private Object data;

    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setCode(Result.SUCCESS_CODE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result error(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result error(String msg) {
        return error(Result.FAIL_CODE, msg, null);
    }
    public static Result error(String msg, Object data) {
        return error(Result.FAIL_CODE, msg, data);
    }
}