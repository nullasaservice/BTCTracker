package com.example.btctracker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val text = TextView(this)

        setContentView(text)

        val storage = Storage(this)

        if (!storage.isConfigured()) {

            text.text = "Not configured"

            startActivity(
                Intent(this, SettingsActivity::class.java)
            )

            return
        }

        CoroutineScope(Dispatchers.Main).launch {

            try {

                val fake = storage.getFakePrice()

                val (usd, eur) = if (fake.isNotBlank()) {

                    val p = fake.toDouble()

                    Pair(p, p * EUR_RATE)

                } else fetchBTCPrice()

                val addresses =
                    storage.getAddresses()
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }

                var total = 0.0

                val sb = StringBuilder()

                sb.append("BTC USD: $usd\nBTC EUR: $eur\n\n")

                for (a in addresses) {

                    val bal = fetchAddressBalance(a)

                    total += bal

                    sb.append("$a\n$bal BTC\n\n")
                }

                val binanceKey = storage.getBinanceKey()
                val binanceSecret = storage.getBinanceSecret()

                if (binanceKey.isNotBlank() &&
                    binanceSecret.isNotBlank()
                ) {

                    val binance = fetchBinanceBTC(
                        binanceKey,
                        binanceSecret
                    )

                    total += binance

                    sb.append("BINANCE: $binance BTC\n\n")
                }

                sb.append("TOTAL: $total BTC\n")
                sb.append("USD: ${total * usd}\n")

                text.text = sb.toString()

            } catch (e: Exception) {

                text.text = e.toString()
            }
        }
    }
}