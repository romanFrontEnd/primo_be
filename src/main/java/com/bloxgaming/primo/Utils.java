package com.bloxgaming.primo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class Utils {

    /**
     * Hashed a string
     *
     * @param {string} value
     * @returns {string}
     */
    public static String sha512(String value) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] bytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    /**
     * Generate a 256 characters string
     *
     * @returns {string}
     */

    public static String generateServerSeed() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < 256) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, 256);
    }

    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }


    /**
     * Custom function for concatenating server-seed, client-seed and nonce.
     *
     * @param serverSeed
     * @param clientSeed
     * @param nonce
     * @returns {*}
     */
    public static String combine(String serverSeed, String clientSeed, String nonce) {
        return serverSeed + clientSeed + nonce;
    }

    static int getResult(String hashedValue) {
        //the offset of the interval
        int index = 0;
        // result variable
        int result;

        do {
            // get the decimal value from an interval of 2 hex letters
            result = Integer.parseInt(hashedValue.substring(index, index + 1), 16);
            // increment the offset in case we will need to repeat the operation above
            index += 1;
            // if all the numbers were over 255 FF
            if (index  + 1 > 255) {
                result = 255;
                break;
            }
        } while (result >= 1e2);
        // the result is between 0-100 and we need to convert if into a 1-20
        return (result % 20);
    }


    //2, 3, 5, 7, 11, 13, 17, 19
    static boolean isPrime(int n) {
        if (n <= 1) {
            System.out.println(n+" is not prime number");
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                System.out.println(n+" is not prime number");
                return false;
            }
        }
        System.out.println(n+" is prime number");
        return true;
    }


}