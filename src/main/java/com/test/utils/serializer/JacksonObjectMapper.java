package com.test.utils.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/8/20 17:48
 */
public class JacksonObjectMapper {

    private static final ObjectMapper INSTANCE;

    static{
        INSTANCE = new ObjectMapper();
        // 使用Jackson提供的时间序列化与反序列化
        INSTANCE.registerModule(new JavaTimeModule());
        INSTANCE.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        INSTANCE.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 不对空值进行序列化
        INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }
    public static ObjectMapper getInstance(){
        return INSTANCE;
    }

}
