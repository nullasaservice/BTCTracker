package com.example.btctracker.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

data class PriceData(
    val btcUsd: Double,
    val btcEur: Double,
    val usdToEur: Double
)

object PriceApi {

    suspend fun fetchBTCPrice(apiKey: String): PriceData {

        return withContext(Dispatchers.IO) {

            val url =
                "https://api.coingecko.com/api/v3/simple/price" +
                        "?vs_currencies=usd,eur&ids=bitcoin"

            val response = HttpClient.get(
                url,
                mapOf(
                    "x-cg-demo-api-key" to apiKey
                )
            )

            val json = JSONObject(response)

            val btc =
                json.getJSONObject("bitcoin")

            val usd =
                btc.getDouble("usd")

            val eur =
                btc.getDouble("eur")

            val usdToEur = eur / usd

            PriceData(
                btcUsd = usd,
                btcEur = eur,
                usdToEur = usdToEur
            )
        }
    }
}