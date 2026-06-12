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

## Current Errors

These are the responses we are getting when running the test classes against the production endpoint:

**OrderList**
```json
Request body: {"body":{"mid":"VELOCI01414270241377","fromDate":"2026-06-04T00:00:00+05:30","toDate":"2026-06-04T23:59:59+05:30","orderSearchType":"TRANSACTION","orderSearchStatus":"SUCCESS","pageNumber":"1","pageSize":"20","merchantOrderId":"","payMode":"","isSort":"","searchConditions":""},"head":{"signature":"7Q2oOZ3eAmpbXxawXtSHhEzsVePI140NhOBCoJVTJwvODU+ZkuRfB+0f60mbAmoNiNaDLpaInNBW5BFybjvGs6IezlKg5MiuBbv99vjzB/M=","tokenType":"CHECKSUM","requestTimestamp":""}}
Response status: 500
Response body: {"status":"FAILURE","count":0,"resultCode":"00000010","errorMessage":"FAILURE"}
```

**TransactionStatus**
```json
Request body: {"body":{"mid":"VELOCI01414270241377","orderId":"YOUR_ORDER_ID_HERE","txnType":""},"head":{"version":"v1","channelId":"","requestTimestamp":"","clientId":"","signature":"5ZXJVqivfDBTyQegVkBByDaMMopamyfjC+pWAWf8CjxPYJJ67m9eQ7dkfP00ZUu4gZ8mNtQR8VnY2Pugvi/jxWTkiCyxQ+0EZFootxsIbq0="}}
Response status: 200
Response body: {"head":{"responseTimestamp":"1781280827329","version":"v1"},"body":{"resultInfo":{"resultStatus":"TXN_FAILURE","resultCode":"501","resultMsg":"System Error."}}}
```

## APIs We Need Enabled

Please enable the following APIs for our merchant account:

1. **Merchant Passbook - Order List API** — to fetch daily transaction lists for reconciliation
2. **Transaction Status API** — to check status of individual orders
