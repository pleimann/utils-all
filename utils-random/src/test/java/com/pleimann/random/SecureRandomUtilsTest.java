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

        // Estimate of entropy bits
        // n: password length
        // c: password cardinality: the size of the symbol space
        long entropy = n * 6;

        System.out.println(String.format("Entropy: %d Pass: %s", entropy, randomString));
        errors.checkThat(entropy, is(greaterThanOrEqualTo(120l)));
    }

    @Test
    public void testGenerateSecureRandomString() {
        int n = 50;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(n, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringWithLength() {
        int n = 36;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(n, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringNegativeLength() {
        int n = -15;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(30, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringMinLength() {
        int n = 15;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(30, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringMaxLength() {
        int n = 100;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(n, randomString);
    }

    @Test
    public void testGenerateSecureRandomStringGreaterThanMaxLength() {
        int n = 1487;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(100, randomString);
    }

    @Test
    public void testEnsureSecureRandomStringWithoutPadding() {
        int n = 49;

        String randomString = SecureRandomUtils.generateSecureRandomPassword(n).toString();

        checkResult(n, randomString);
    }
}