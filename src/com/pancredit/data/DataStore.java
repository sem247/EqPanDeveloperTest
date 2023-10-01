package com.pancredit.data;

import com.pancredit.model.TransactionData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum DataStore {
    INSTANCE;
    private final ConcurrentMap<String, TransactionData> dataset = new ConcurrentHashMap<>();

    void setDataset(Map<String, TransactionData> dataset) {
        this.dataset.putAll(dataset);
    }

    public Map<String, TransactionData> getDataset() {
        return dataset;
    }
}
