package com.qiong.handshaker.utils.utils.usefull;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class QJsonUtil {
    // static ObjectMapper mapper = new ObjectMapper();

    public static <T> String serToString(T data) throws JsonProcessingException {
        // ObjectMapper mapper = new ObjectMapper();
        // return mapper.writeValueAsString(data);
        return JSONUtil.toJsonStr(data);
    }

    public static <T> T serToObject(String src, Class<T> resultClass) throws JsonProcessingException {
        // ObjectMapper mapper = new ObjectMapper();
        // return mapper.readValue(src, resultClass);
        return JSONUtil.toBean(src, resultClass);
    }
}
