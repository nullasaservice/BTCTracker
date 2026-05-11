package com.example.btctracker.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object BinanceApi {

    suspend fun getBTC(key: String, secret: String): Double {

        return withContext(Dispatchers.IO) {

            val ts = getServerTime() - 1000 // 1 second behind = avoids “ahead of server” errors completely

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

            val jsonString = HttpClient.get(
                url,
                mapOf("X-MBX-APIKEY" to key)
            )

            val json = JSONObject(jsonString)

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

    private fun getServerTime(): Long {

        val url = "https://api.binance.com/api/v3/time"

        val json = org.json.JSONObject(
            com.example.btctracker.network.HttpClient.get(url)
        )

        return json.getLong("serverTime")
    }
}