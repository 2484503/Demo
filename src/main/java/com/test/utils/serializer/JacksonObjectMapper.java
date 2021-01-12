package com.test.utils.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 维护序列化的配置
 * @author lijn
 * @version 1.0
 * @date 2019/8/20 17:48
 */
public class JacksonObjectMapper {

    private static final ObjectMapper INSTANCE;

    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static{
        INSTANCE = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        // 使用Jackson提供的时间序列化与反序列化
        INSTANCE.registerModule(javaTimeModule);
        INSTANCE.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        INSTANCE.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 不对空值进行序列化
        INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }
    public static ObjectMapper getInstance(){
        return INSTANCE;
    }

}
