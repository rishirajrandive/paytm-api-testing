# PayTM API Test

Standalone Java test project to verify PayTM API access for a production merchant account.

## Setup

1. Install Java 11+ and Maven
2. Replace `YOUR_MID_HERE` and `YOUR_MERCHANT_KEY_HERE` in each file with the merchant credentials
3. Build: `mvn compile`

## Test Classes

Each class has a `main()` method — run them individually from your IDE or via Maven.

| Class | API | What it tests |
|---|---|---|
| `OrderList` | `/merchant-passbook/search/list/order/v2` | Fetch list of orders for a date range |
| `TransactionStatus` | `/v3/order/status` | Fetch status of a specific order by ID |
| `Settlements` | `/merchant-passbook/settlement/details/v1` | Fetch settlement details for a date range |
| `WebhookTest` | — | Verify checksum of an incoming webhook payload |

## APIs We Need Enabled

Please enable the following for MID `VELOCI01414270241377`:

1. **Merchant Passbook - Order List API** — to fetch daily transaction lists for reconciliation
2. **Transaction Status API** — to check status of individual orders
3. **Settlement Details API** — to fetch settlement reports
4. **Webhook / Payment Notification** — to receive real-time transaction updates on our server
