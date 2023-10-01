package com.pancredit.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiJsonHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiJsonHelper.class);

    private final ObjectMapper objectMapper;

    public ApiJsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed writing to json. Item: {}; Exception: ", object, e);
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String payload, Class<T> clazz) {
        try {
            return objectMapper.readValue(payload, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed converting from json. Item: {}; Exception: ", payload, e);
            throw new RuntimeException(e);
        }
    }
}
