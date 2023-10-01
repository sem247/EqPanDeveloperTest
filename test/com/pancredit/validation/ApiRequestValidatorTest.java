package com.pancredit.validation;

import com.pancredit.fixture.TestFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ApiRequestValidatorTest {

    private final ApiRequestValidator underTest = new ApiRequestValidator();

    @Test
    void testValidatePathInfoShouldPassValidation() {
        final String pathInfo = "abc/fc099e30-6629-4015-a9e8-91edf442d69e";

        Throwable throwable = catchThrowable(() -> underTest.validatePathInfo(pathInfo));

        assertThat(throwable)
                .isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "6629-0812-a9e8-df442d6",
            "abc/6629-0812-a9e8-df442d6",
            "fc099e30-6629-4015-a9e8-91edf442d69e",
            "fc099e30-6629-4015-a9e8-91edf442d69e/",
            "abc/fc099e30-6629-4015-a9e8-91edf442d69e/123"
    })
    void testValidatePathShouldFailValidation(String pathInfo) {
        Throwable throwable = catchThrowable(() -> underTest.validatePathInfo(pathInfo));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"/"})
    void testValidateForPostShouldPassValidation(String pathInfo) {
        Throwable throwable = catchThrowable(() -> underTest.validatePathInfoForPost(pathInfo));

        assertThat(throwable)
                .isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/abc", "abc", "abc/"})
    void testValidateForPostShouldFailValidation(String pathInfo) {
        Throwable throwable = catchThrowable(() -> underTest.validatePathInfoForPost(pathInfo));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testValidatePayloadShouldPassValidation() {
        Throwable throwable = catchThrowable(() -> underTest.validatePayload(TestFixture.TRANSACTION_DATA_1));

        assertThat(throwable)
                .isNull();
    }

    @Test
    void testValidatePayloadShouldFailValidation() {
        Throwable throwable = catchThrowable(() -> underTest.validatePayload(TestFixture.TRANSACTION_DATA_INVALID_TYPE));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testValidateResourceIdsShouldPassValidation() {
        Throwable throwable = catchThrowable(() ->
                underTest.validateResourceIds("3f2b12b8-2a06-45b4-b057-45949279b4e5", TestFixture.TRANSACTION_DATA_1));

        assertThat(throwable)
                .isNull();
    }

    @Test
    void testValidateResourceIdsShouldFailValidation() {
        Throwable throwable = catchThrowable(() ->
                underTest.validateResourceIds("2b83f2b1-2a06-45b4-57b0-9b4e54594927", TestFixture.TRANSACTION_DATA_1));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ValidationException.class);
    }
}