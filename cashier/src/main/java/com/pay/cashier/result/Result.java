package com.pay.cashier.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer retcode; //编码：1成功，0和其它数字为失败
    private String retmsg; //错误信息
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.retcode = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.retcode = 1;
        return result;
    }

    public static <T> Result<T> error(String retmsg) {
        Result result = new Result();
        result.retmsg = retmsg;
        result.retcode = 0;
        return result;
    }

}
