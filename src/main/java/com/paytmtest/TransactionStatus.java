package com.paytmtest;

import com.paytm.pg.merchant.PaytmChecksum;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

/**
 * Tests the Transaction Status API.
 * Requires: Transaction Status API access to be enabled for the MID by PayTM.
 *
 * API: POST https://secure.paytmpayments.com/v3/order/status
 */
public class TransactionStatus {

    static final String MID          = "YOUR_MID_HERE";
    static final String MERCHANT_KEY = "YOUR_MERCHANT_KEY_HERE";

    public static void main(String[] args) throws Exception {
        String orderId = "YOUR_ORDER_ID_HERE"; // replace with a real order ID

        TreeMap<String, String> paytmParams = new TreeMap<>();
        paytmParams.put("mid",     MID);
        paytmParams.put("orderId", orderId);
        paytmParams.put("txnType", ""); // optional: PREAUTH, CAPTURE, RELEASE, WITHDRAW

        String signature = PaytmChecksum.generateSignature(paytmParams, MERCHANT_KEY);

        String body = "{"
            + "\"body\":{"
            + "\"mid\":\""     + paytmParams.get("mid")     + "\","
            + "\"orderId\":\"" + paytmParams.get("orderId") + "\","
            + "\"txnType\":\"" + paytmParams.get("txnType") + "\""
            + "},"
            + "\"head\":{"
            + "\"version\":\"v1\","
            + "\"channelId\":\"\","
            + "\"requestTimestamp\":\"\","
            + "\"clientId\":\"\","
            + "\"signature\":\"" + signature + "\""
            + "}"
            + "}";

        System.out.println("Request body: " + body);

        URL url = new URL("https://secure.paytmpayments.com/v3/order/status");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        String response = new String(
            (status >= 400 ? conn.getErrorStream() : conn.getInputStream()).readAllBytes(),
            StandardCharsets.UTF_8
        );

        System.out.println("Response status: " + status);
        System.out.println("Response body: " + response);
    }
}
