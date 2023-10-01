package com.pancredit.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancredit.model.TransactionData;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DatasetProvider {
    private final ObjectMapper objectMapper;
    private final DataStore dataStore;

    public DatasetProvider(ObjectMapper objectMapper, DataStore dataStore) {
        this.objectMapper = objectMapper;
        this.dataStore = dataStore;
    }

    public void prepareDataset(String rawData) throws IOException {
        List<TransactionData> asList = objectMapper.readValue(rawData, new TypeReference<List<TransactionData>>() {
        });

        Map<String, TransactionData> transactions = asList.stream()
                .collect(Collectors.toMap(TransactionData::getId, Function.identity()));

        dataStore.setDataset(transactions);
    }
}
