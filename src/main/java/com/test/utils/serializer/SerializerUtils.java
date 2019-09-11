package com.test.utils.serializer;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * 序列化工具
 *
 * @author lijn
 * @version 1.0
 * @date 2019/8/20 17:47
 */
@NoArgsConstructor
public class SerializerUtils {
    @SneakyThrows
    public static String serialize(Object object) {
        return JacksonObjectMapper.getInstance().writeValueAsString(object);
    }
}
