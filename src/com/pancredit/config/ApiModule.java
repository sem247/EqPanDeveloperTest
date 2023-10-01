package com.pancredit.config;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pancredit.data.DataStore;
import com.pancredit.service.TransactionService;
import com.pancredit.validation.ApiRequestValidator;

public enum ApiModule {
    INSTANCE;

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final TransactionService transactionService = new TransactionService(DataStore.INSTANCE);

    private final ApiRequestValidator apiRequestValidator = new ApiRequestValidator();

    public ObjectMapper getObjectMapper() {
        objectMapper.configOverride(String.class)
                .setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP));

        return objectMapper;
    }

    public TransactionService provideCreditService() {
        return transactionService;
    }

    public ApiRequestValidator provideApiRequestValidator() {
        return apiRequestValidator;
    }
}
