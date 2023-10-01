package com.pancredit.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancredit.fixture.TestFixture;
import com.pancredit.model.TransactionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DatasetProviderTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DataStore dataStore = DataStore.INSTANCE;

    private DatasetProvider underTest;

    private static final Map<String, TransactionData> EXPECTED_DATASET = TestFixture.getDataset();

    private static final String DATA = "[\n" +
            "  {\n" +
            "    \"Id\": \"3f2b12b8-2a06-45b4-b057-45949279b4e5\",\n" +
            "    \"ApplicationId\": 197104,\n" +
            "    \"Type\": \"Debit\",\n" +
            "    \"Summary\": \"Payment\",\n" +
            "    \"Amount\": 58.26,\n" +
            "    \"PostingDate\": \"2016-07-01T00:00:00\",\n" +
            "    \"IsCleared\": true,\n" +
            "    \"ClearedDate\": \"2016-07-02T00:00:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Id\": \"d2032222-47a6-4048-9894-11ab8ebb9f69\",\n" +
            "    \"ApplicationId\": 197104,\n" +
            "    \"Type\": \"Debit\",\n" +
            "    \"Summary\": \"Payment\",\n" +
            "    \"Amount\": 50.09,\n" +
            "    \"PostingDate\": \"2016-08-01T00:00:00\",\n" +
            "    \"IsCleared\": true,\n" +
            "    \"ClearedDate\": \"2016-08-02T00:00:00\"\n" +
            "  }\n" +
            "]";

    @BeforeEach
    void prepare() {
        underTest = new DatasetProvider(objectMapper, dataStore);
    }

    @Test
    void testGetDataset() throws Exception {
        underTest.prepareDataset(DATA);

        assertThat(dataStore.getDataset())
                .isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrderEntriesOf(EXPECTED_DATASET);
    }
}