package com.paytmtest;

import com.paytm.pg.merchant.PaytmChecksum;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

/**
 * Tests the Order List API.
 * Requires: Merchant Passbook API access to be enabled for the MID by PayTM.
 *
 * API: POST https://secure.paytmpayments.com/merchant-passbook/search/list/order/v2
 */
public class OrderList {

    static final String MID          = "YOUR_MID_HERE";
    static final String MERCHANT_KEY = "YOUR_MERCHANT_KEY_HERE";

    public static void main(String[] args) throws Exception {
        String date = "2026-06-04"; // change date as needed

        TreeMap<String, String> paytmParams = new TreeMap<>();
        paytmParams.put("mid",               MID);
        paytmParams.put("fromDate",          date + "T00:00:00+05:30");
        paytmParams.put("toDate",            date + "T23:59:59+05:30");
        paytmParams.put("orderSearchType",   "TRANSACTION");
        paytmParams.put("orderSearchStatus", "SUCCESS");
        paytmParams.put("pageNumber",        "1");
        paytmParams.put("pageSize",          "20");
        paytmParams.put("merchantOrderId",   "");
        paytmParams.put("payMode",           "");
        paytmParams.put("isSort",            "");
        paytmParams.put("searchConditions",  "");

        String signature = PaytmChecksum.generateSignature(paytmParams, MERCHANT_KEY);

        String body = "{"
            + "\"body\":{"
            + "\"mid\":\""               + paytmParams.get("mid")               + "\","
            + "\"fromDate\":\""          + paytmParams.get("fromDate")          + "\","
            + "\"toDate\":\""            + paytmParams.get("toDate")            + "\","
            + "\"orderSearchType\":\""   + paytmParams.get("orderSearchType")   + "\","
            + "\"orderSearchStatus\":\"" + paytmParams.get("orderSearchStatus") + "\","
            + "\"pageNumber\":\""        + paytmParams.get("pageNumber")        + "\","
            + "\"pageSize\":\""          + paytmParams.get("pageSize")          + "\","
            + "\"merchantOrderId\":\""   + paytmParams.get("merchantOrderId")   + "\","
            + "\"payMode\":\""           + paytmParams.get("payMode")           + "\","
            + "\"isSort\":\""            + paytmParams.get("isSort")            + "\","
            + "\"searchConditions\":\""  + paytmParams.get("searchConditions")  + "\""
            + "},"
            + "\"head\":{"
            + "\"signature\":\""  + signature + "\","
            + "\"tokenType\":\"CHECKSUM\","
            + "\"requestTimestamp\":\"\""
            + "}"
            + "}";

        System.out.println("Request body: " + body);

        URL url = new URL("https://secure.paytmpayments.com/merchant-passbook/search/list/order/v2");
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
