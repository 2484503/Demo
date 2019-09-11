package com.test.utils.serializer;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author lijn
 * @version 1.0
 * @date 2019/8/20 17:47
 */
@NoArgsConstructor
public class AceSerializer {
    @SneakyThrows
    public static String serialize(Object object) {
        return JacksonObjectMapper.getInstance().writeValueAsString(object);
    }
}
