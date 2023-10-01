package com.pancredit.service;

import com.pancredit.data.DataStore;
import com.pancredit.exception.ResourceNotFoundException;
import com.pancredit.fixture.TestFixture;
import com.pancredit.model.TransactionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TransactionServiceTest {
    private final DataStore dataStore = DataStore.INSTANCE;
    private TransactionService underTest = new TransactionService(dataStore);

    @BeforeEach
    void prepare() {
        dataStore.getDataset().clear();
    }

    @Test
    void testGetAll() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        Collection<TransactionData> transactions = underTest.getAll();

        assertThat(transactions)
                .isNotEmpty()
                .hasSize(2)
                .hasSameElementsAs(Arrays.asList(TestFixture.TRANSACTION_DATA_1, TestFixture.TRANSACTION_DATA_2));
    }

    @Test
    void testGetAllWhenDatasetIsEmpty() {
        underTest = new TransactionService(dataStore);

        Collection<TransactionData> transactions = underTest.getAll();

        assertThat(transactions)
                .isEmpty();
    }

    @Test
    void testGetOne() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        TransactionData transaction = underTest.getOne("d2032222-47a6-4048-9894-11ab8ebb9f69");

        assertThat(transaction)
                .isNotNull()
                .isEqualTo(TestFixture.TRANSACTION_DATA_2);
    }

    @Test
    void testGetOneWhenNotFound() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        Throwable throwable = catchThrowable(() ->
                underTest.getOne("d2222032-47a6-4048-9894-bb9f6911ab8e"));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCreateOne() {
        underTest.createOne(TestFixture.TRANSACTION_DATA_1);

        assertThat(dataStore.getDataset().get(TestFixture.TRANSACTION_DATA_1.getId()))
                .isNotNull()
                .isEqualTo(TestFixture.TRANSACTION_DATA_1);
    }

    @Test
    void testDeleteOne() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        TransactionData transactionData = underTest.deleteOne("d2032222-47a6-4048-9894-11ab8ebb9f69");

        assertThat(transactionData)
                .isNotNull()
                .isEqualTo(TestFixture.TRANSACTION_DATA_2);
    }

    @Test
    void testDeleteOneWhenNotFound() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        Throwable throwable = catchThrowable(() ->
                underTest.deleteOne("d2222032-47a6-4048-9894-bb9f6911ab8e"));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testPutOne() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        final TransactionData modifiedTransactionData2 = new TransactionData(
                "d2032222-47a6-4048-9894-11ab8ebb9f69",
                197104,
                "Debit",
                "Payment",
                new BigDecimal("50.09"),
                "2016-08-01T00:00:00",
                true,
                "2016-08-02T00:00:00"
        );

        underTest.putOne("d2032222-47a6-4048-9894-11ab8ebb9f69", modifiedTransactionData2);

        assertThat(dataset.get(TestFixture.TRANSACTION_DATA_2.getId()))
                .isNotNull()
                .isEqualTo(modifiedTransactionData2);
    }

    @Test
    void testPutOneWhenNotFound() {
        final Map<String, TransactionData> dataset = TestFixture.getDataset();
        dataStore.getDataset().putAll(dataset);

        final TransactionData modifiedTransactionData = new TransactionData(
                "d2222032-47a6-4048-9894-bb9f6911ab8e",
                197104,
                "Debit",
                "Payment",
                new BigDecimal("50.09"),
                "2016-08-01T00:00:00",
                true,
                "2016-08-02T00:00:00"
        );

        Throwable throwable = catchThrowable(() ->
                underTest.putOne("d2222032-47a6-4048-9894-bb9f6911ab8e", modifiedTransactionData));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ResourceNotFoundException.class);
    }
}