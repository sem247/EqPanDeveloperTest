package com.pancredit.service;

import com.pancredit.data.DataStore;
import com.pancredit.exception.ResourceNotFoundException;
import com.pancredit.model.TransactionData;

import java.util.Collection;
import java.util.Optional;

public class TransactionService {
    private static final String NOT_FOUND_MESSAGE = "Resource identified by '%s' not found.";

    private final DataStore dataStore;

    public TransactionService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Collection<TransactionData> getAll() {
        return dataStore.getDataset().values();
    }

    public TransactionData getOne(String resourceId) {
        return Optional.ofNullable(dataStore.getDataset().get(resourceId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, resourceId)));
    }

    public void createOne(TransactionData transactionData) {
        dataStore.getDataset().put(transactionData.getId(), transactionData);
    }

    public TransactionData deleteOne(String resourceId) {
        return Optional.ofNullable(dataStore.getDataset().get(resourceId))
                .map(it -> {
                    dataStore.getDataset().remove(resourceId);
                    return it;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, resourceId)));
    }

    public void putOne(String resourceId, TransactionData transactionData) {
        Optional.ofNullable(dataStore.getDataset().get(resourceId))
                .map(it -> dataStore.getDataset().put(resourceId, transactionData))
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, resourceId)));
    }
}
