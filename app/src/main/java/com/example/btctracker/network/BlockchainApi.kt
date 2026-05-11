package com.example.btctracker.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object BlockchainApi {

    suspend fun getBalance(address: String): Double {

        return withContext(Dispatchers.IO) {

            val url =
                "https://blockchain.info/q/addressbalance/$address"

            try {

                val result = HttpClient.get(url)

                result.toLong() / 100_000_000.0

            } catch (e: Exception) {

                e.printStackTrace()

                throw RuntimeException("Blockstream fetch failed: ${e.message}")
            }
        }
    }
}