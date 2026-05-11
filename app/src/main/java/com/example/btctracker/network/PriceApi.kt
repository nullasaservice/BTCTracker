package com.example.btctracker.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

object PriceApi {

    suspend fun fetchBTCPrice(): Pair<Double, Double> {

        return withContext(Dispatchers.IO) {

            val url =
                "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"

            try {

                val connection = URL(url).openConnection()

                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000

                val response = HttpClient.get(url)

                val json = JSONObject(response)

                val usd =
                    json.getJSONObject("bitcoin")
                        .getDouble("usd")

                Pair(usd, usd * FxApi.getUsdToEur())

            } catch (e: Exception) {

                e.printStackTrace()

                throw RuntimeException("CoinGecko fetch failed: ${e.message}")
            }
        }
    }
}