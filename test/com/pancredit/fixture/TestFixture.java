package com.pancredit.fixture;

import com.pancredit.model.TransactionData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class TestFixture {
    private TestFixture() {
    }

    public static final TransactionData TRANSACTION_DATA_1 = new TransactionData(
            "3f2b12b8-2a06-45b4-b057-45949279b4e5",
            197104,
            "Debit",
            "Payment",
            new BigDecimal("58.26"),
            "2016-07-01T00:00:00",
            true,
            "2016-07-02T00:00:00"
    );

    public static final TransactionData TRANSACTION_DATA_2 = new TransactionData(
            "d2032222-47a6-4048-9894-11ab8ebb9f69",
            197104,
            "Debit",
            "Payment",
            new BigDecimal("50.09"),
            "2016-08-01T00:00:00",
            true,
            "2016-08-02T00:00:00"
    );

    public static final TransactionData TRANSACTION_DATA_INVALID_TYPE = new TransactionData(
            "3f2b12b8-2a06-45b4-b057-45949279b4e5",
            197104,
            "debit",
            "Payment",
            new BigDecimal("58.26"),
            "2016-07-01T00:00:00",
            true,
            "2016-07-02T00:00:00"
    );

    public static Map<String, TransactionData> getDataset() {
        Map<String, TransactionData> dataMap = new HashMap<>();
        dataMap.put(TRANSACTION_DATA_1.getId(), TRANSACTION_DATA_1);
        dataMap.put(TRANSACTION_DATA_2.getId(), TRANSACTION_DATA_2);

        return dataMap;
    }

}
