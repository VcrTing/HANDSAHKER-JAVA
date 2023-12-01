package com.qiong.handshaker.utils.tool.result;

import com.qiong.handshaker.utils.define.result.QResponse;
import org.springframework.http.HttpStatus;

public class QResponseTool {

    /**
    * 方便的
    * @params
    * @return
    */
    public static <T> QResponse<T> restfull(boolean isOk, T data) {
        if (isOk) return new QResponse<T>(HttpStatus.OK.value(), "操作成功", data);
        return new QResponse<T>(HttpStatus.BAD_REQUEST.value(), "操作失败", null);
    }

    /**
    *
    * @params
    * @return
    */
    public static <T> QResponse<T> genRes(HttpStatus code, String msg, T data) {
        return new QResponse<T>(code.value(), msg, data);
    }

    public static <T> QResponse<T> genSuccess(String msg, T data) {
        return new QResponse<T>(HttpStatus.OK.value(), msg, data);
    }

    public static <T> QResponse<T> genBad(String msg, T data) {
        return new QResponse<T>(HttpStatus.BAD_REQUEST.value(), msg, data);
    }
}
