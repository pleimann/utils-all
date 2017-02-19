package com.pleimann.random;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

public class SecureRandomUtils {
    private static final int MIN_RANDOM_STRING_LENGTH = 30;
    private static final int MAX_RANDOM_STRING_LENGTH = 100;

    public static final CharSequence generateSecureRandomPassword() {
        return generateSecureRandomPassword(MIN_RANDOM_STRING_LENGTH);
    }

    public static final CharSequence generateSecureRandomPassword(int length){
        int nominalLength = Math.min(MAX_RANDOM_STRING_LENGTH, Math.max(length, MIN_RANDOM_STRING_LENGTH));

        // Calculate the number of bytes of entropy necessary to generate a Base64 string of requested length
        int bytesLength = calculateByteLength(nominalLength);

        SecureRandom random = new SecureRandom();

        byte[] randomBytes = new byte[bytesLength];
        random.nextBytes(randomBytes);

        // Uses URL save version to avoid '=' padding characters
        ByteBuffer randomBase64Bytes = ByteBuffer.wrap(Base64.encodeBase64URLSafe(randomBytes));

        // clear array in memory. after return it will be out
        // of scope and therefore eligible for garbage collection
        Arrays.fill(randomBytes, (byte)0);

        CharSequence result = StandardCharsets.ISO_8859_1.decode(randomBase64Bytes);

        // The byte length calculation can for some lengths
        // (ex. length=49) result in an extra character
        return result.subSequence(0, nominalLength);
    }

    private static final int calculateByteLength(int length){
        int result = (int)Math.ceil((3 * length) / 4.0f);

        return result;
    }
}
