package com.pleimann.random;

import org.apache.commons.codec.binary.Base64;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.Matchers.*;

public class SecureRandomUtilsTest {
    @Rule
    public ErrorCollector errors = new ErrorCollector();

    private void checkResult(int n, String randomString) {
        errors.checkThat(randomString, not(isEmptyOrNullString()));
        errors.checkThat(randomString, not(containsString("=")));
        errors.checkThat(randomString.length(), is(equalTo(n)));
        errors.checkThat(Base64.isBase64(randomString), is(true));
    }

    @Test
    public void testGenerateSecureRandomString() {
        int n = 50;

        String randomString = SecureRandomUtils.generateSecureRandomCharSequence(n).toString();

        checkResult(n, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringWithLength() {
        int n = 23;

        String randomString = SecureRandomUtils.generateSecureRandomCharSequence(n).toString();

        checkResult(n, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringMinLength() {
        int n = 15;

        String randomString = SecureRandomUtils.generateSecureRandomCharSequence(n).toString();

        checkResult(20, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringMaxLength() {
        int n = 1000;

        String randomString = SecureRandomUtils.generateSecureRandomCharSequence(n).toString();

        checkResult(n, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringGreaterThanMaxLength() {
        int n = 1487;

        String randomString = SecureRandomUtils.generateSecureRandomCharSequence(n).toString();

        checkResult(1000, randomString);
    }

    @Test
    public void testEnsureSecureRandomStringWithoutPadding() {
        int n = 49;

        String randomString = SecureRandomUtils.generateSecureRandomCharSequence(n).toString();

        checkResult(n, randomString);
    }
}