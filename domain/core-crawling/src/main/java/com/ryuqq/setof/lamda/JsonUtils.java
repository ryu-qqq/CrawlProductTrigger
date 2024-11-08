package com.ryuqq.setof.lamda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    public static <T> T fromJson(String json, Class<T> valueType){
        try{
            return objectMapper.readValue(json, valueType);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> valueType) {
        try {
            JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, valueType);
            return objectMapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
