package com.pancredit.json;

import com.pancredit.config.ApiModule;
import com.pancredit.fixture.TestFixture;
import com.pancredit.model.TransactionData;
import org.junit.jupiter.api.Test;

//import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ApiJsonHelperTest {
    private final ApiJsonHelper underTest = new ApiJsonHelper(ApiModule.INSTANCE.getObjectMapper());

    @Test
    void testToJson() {
        final String expectedJson = "{\n" +
                "        \"Id\": \"3f2b12b8-2a06-45b4-b057-45949279b4e5\",\n" +
                "        \"ApplicationId\": 197104,\n" +
                "        \"Type\": \"Debit\",\n" +
                "        \"Summary\": \"Payment\",\n" +
                "        \"Amount\": 58.26,\n" +
                "        \"PostingDate\": \"2016-07-01T00:00:00\",\n" +
                "        \"IsCleared\": true,\n" +
                "        \"ClearedDate\": \"2016-07-02T00:00:00\"\n" +
                "    }";


        final String actualJson = underTest.toJson(TestFixture.TRANSACTION_DATA_1);

//        assertThatJson(actualJson).isEqualTo(expectedJson);
    }

    @Test
    void testFromJson() {
        final String inputJson = "{\n" +
                "        \"Id\": \"3f2b12b8-2a06-45b4-b057-45949279b4e5\",\n" +
                "        \"ApplicationId\": 197104,\n" +
                "        \"Type\": \"Debit\",\n" +
                "        \"Summary\": \"Payment\",\n" +
                "        \"Amount\": 58.26,\n" +
                "        \"PostingDate\": \"2016-07-01T00:00:00\",\n" +
                "        \"IsCleared\": true,\n" +
                "        \"ClearedDate\": \"2016-07-02T00:00:00\"\n" +
                "    }";

        final TransactionData transactionData = underTest.fromJson(inputJson, TransactionData.class);

        assertThat(transactionData).isNotNull().isEqualTo(TestFixture.TRANSACTION_DATA_1);
    }
}