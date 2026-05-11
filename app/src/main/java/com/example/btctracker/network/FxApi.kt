package com.example.btctracker.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object FxApi {

    suspend fun getUsdToEur(): Double {

        return withContext(Dispatchers.IO) {

            val url =
                "https://api.exchangerate.host/latest?base=USD&symbols=EUR"

            try {
                val jsonString =
                    HttpClient.get(url)

                val json = JSONObject(jsonString)

                json.getJSONObject("rates")
                    .getDouble("EUR")
            } catch (e: Exception) {

                e.printStackTrace()

                throw RuntimeException("Fx fetch failed: ${e.message}")
            }
        }
    }
}