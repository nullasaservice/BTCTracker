package com.example.btctracker.repository

import com.example.btctracker.data.Storage
import com.example.btctracker.network.*

class PortfolioRepository(private val storage: Storage) {

    suspend fun loadPortfolio(): String {

        val fake = storage.getFakePrice()

        val priceData =
            if (fake.isNotBlank()) {

                val fakeUsd = fake.toDouble()

                val realPrices =
                    PriceApi.fetchBTCPrice(storage.getCoinGeckoApiKey())

                PriceData(
                    btcUsd = fakeUsd,
                    btcEur = fakeUsd * realPrices.usdToEur,
                    usdToEur = realPrices.usdToEur
                )

            } else {
                PriceApi.fetchBTCPrice(storage.getCoinGeckoApiKey())
            }

        val usd = priceData.btcUsd
        val eur = priceData.btcEur

        val addresses = storage.getAddresses()
            .split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }

        var total = 0.0

        val sb = StringBuilder()

        sb.append("BTC USD: $usd\nBTC EUR: $eur\n\n")

        for (a in addresses) {

            val bal = BlockchainApi.getBalance(a)

            total += bal

            sb.append("$a\n$bal BTC\n\n")
        }

        val key = storage.getBinanceKey()
        val secret = storage.getBinanceSecret()

        if (key.isNotBlank() && secret.isNotBlank()) {

            val binance = BinanceApi.getBTC(key, secret)

            total += binance

            sb.append("BINANCE: $binance BTC\n\n")
        }

        sb.append("TOTAL: $total BTC\n")
        sb.append("USD: ${total * usd}\n")
        sb.append("EUR: ${total * eur}\n")

        return sb.toString()
    }
}