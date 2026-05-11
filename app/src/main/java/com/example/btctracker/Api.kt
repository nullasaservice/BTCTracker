package com.example.btctracker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val EUR_RATE = 0.92

// ---------------- PRICE ----------------

suspend fun fetchBTCPrice(): Pair<Double, Double> {

    return withContext(Dispatchers.IO) {

        val url =
            "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"

        val json =
            JSONObject(URL(url).readText())

        val usd =
            json.getJSONObject("bitcoin")
                .getDouble("usd")

        Pair(usd, usd * EUR_RATE)
    }
}

// ---------------- ADDRESS ----------------

suspend fun fetchAddressBalance(address: String): Double {

    return withContext(Dispatchers.IO) {

        val url =
            "https://blockchain.info/q/addressbalance/$address"

        URL(url).readText()
            .toLong() / 100_000_000.0
    }
}

// ---------------- BINANCE ----------------

suspend fun fetchBinanceBTC(
    key: String,
    secret: String
): Double {

    return withContext(Dispatchers.IO) {

        val ts = System.currentTimeMillis()

        val query = "timestamp=$ts"

        val mac = Mac.getInstance("HmacSHA256")

        mac.init(
            SecretKeySpec(
                secret.toByteArray(),
                "HmacSHA256"
            )
        )

        val signature =
            mac.doFinal(query.toByteArray())
                .joinToString("") { "%02x".format(it) }

        val url =
            "https://api.binance.com/api/v3/account?$query&signature=$signature"

        val conn = URL(url).openConnection()

        conn.setRequestProperty("X-MBX-APIKEY", key)

        val json =
            JSONObject(
                conn.getInputStream()
                    .bufferedReader()
                    .readText()
            )

        val balances = json.getJSONArray("balances")

        for (i in 0 until balances.length()) {

            val obj = balances.getJSONObject(i)

            if (obj.getString("asset") == "BTC") {

                val free = obj.getString("free").toDouble()
                val locked = obj.getString("locked").toDouble()

                return@withContext free + locked
            }
        }

        0.0
    }
}