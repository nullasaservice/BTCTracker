package com.example.btctracker.repository

import com.example.btctracker.data.Storage
import com.example.btctracker.network.*
import com.example.btctracker.state.AppState

class PortfolioRepository(private val storage: Storage) {

    suspend fun loadPortfolio(): String {

        val fakeUsd = AppState.fakePriceUsd

        val priceData =
            if (fakeUsd != null) {
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

        if (AppState.fakePriceUsd != null) {
            sb.appendLine("⚠️ Fake price mode active ⚠️")
            sb.appendLine()
        }

        sb.appendLine("###############")
        sb.appendLine("Bitcoin prices:")
        sb.appendLine("USD: $usd")
        sb.appendLine("EUR: $eur")
        sb.appendLine("###############")
        sb.appendLine()

        sb.appendLine("#################")
        sb.appendLine("Wallet addresses:")
        for (a in addresses) {
            sb.appendLine("////")
            val bal = BlockchainApi.getBalance(a)
            total += bal
            sb.appendLine("$a\n$bal BTC")
            sb.appendLine("////")
        }
        sb.appendLine("#################")
        sb.appendLine()

        sb.appendLine("################")
        sb.appendLine("Binance balance:")
        val key = storage.getBinanceKey()
        val secret = storage.getBinanceSecret()
        if (key.isNotBlank() && secret.isNotBlank()) {
            val binance = BinanceApi.getBTC(key, secret)
            total += binance
            sb.appendLine("----- $binance BTC -----")
        }
        sb.appendLine("################")
        sb.appendLine()

        sb.appendLine("################")
        sb.appendLine("Total:")
        sb.appendLine("----- $total BTC -----")
        sb.appendLine("----- ${String.format("%.2f", total * usd).replace(",", ".")} USD -----")
        sb.appendLine("----- ${String.format("%.2f", total * eur).replace(",", ".")} EUR -----")
        sb.appendLine("################")
        sb.appendLine()

        return sb.toString()
    }
}