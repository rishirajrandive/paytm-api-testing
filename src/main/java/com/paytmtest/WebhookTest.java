package com.paytmtest;

import com.paytm.pg.merchant.PaytmChecksum;

import java.util.TreeMap;

/**
 * Tests PayTM webhook checksum verification.
 * When PayTM sends a webhook/notification to your server, it includes a checksum in the payload.
 * This class shows how to verify that checksum to confirm the notification is genuine.
 *
 * Paste the webhook payload params and checksum from a real PayTM notification to test.
 */
public class WebhookTest {

    static final String MERCHANT_KEY = "YOUR_MERCHANT_KEY_HERE";

    public static void main(String[] args) throws Exception {
        // Paste the params from the webhook notification payload here
        TreeMap<String, String> webhookParams = new TreeMap<>();
        webhookParams.put("BANKNAME",        "ICICI");
        webhookParams.put("BANKTXNID",       "SAMPLE_BANK_TXN_ID");
        webhookParams.put("CURRENCY",        "INR");
        webhookParams.put("GATEWAYNAME",     "ICICI");
        webhookParams.put("MID",             "YOUR_MID_HERE");
        webhookParams.put("ORDERID",         "YOUR_ORDER_ID_HERE");
        webhookParams.put("PAYMENTMODE",     "UPI");
        webhookParams.put("RESPCODE",        "01");
        webhookParams.put("RESPMSG",         "Txn Success");
        webhookParams.put("STATUS",          "TXN_SUCCESS");
        webhookParams.put("TXNAMOUNT",       "100.00");
        webhookParams.put("TXNDATE",         "2026-06-04 10:00:00.0");
        webhookParams.put("TXNID",           "SAMPLE_PAYTM_TXN_ID");

        // Paste the checksum from the webhook payload here
        String receivedChecksum = "PASTE_CHECKSUM_FROM_WEBHOOK_HERE";

        boolean isValid = PaytmChecksum.verifySignature(webhookParams, MERCHANT_KEY, receivedChecksum);

        System.out.println("Webhook checksum valid: " + isValid);
        if (!isValid) {
            System.out.println("Checksum mismatch — either the payload was tampered or the key is wrong.");
        }
    }
}
