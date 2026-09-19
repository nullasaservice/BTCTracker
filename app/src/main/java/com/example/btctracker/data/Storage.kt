package com.example.btctracker.data

import android.content.Context

class Storage(context: Context) {

    private val prefs =
        context.getSharedPreferences("btc", Context.MODE_PRIVATE)

    fun setBinanceKey(v: String) =
        prefs.edit().putString("binance_key", v).apply()

    fun setBinanceSecret(v: String) =
        prefs.edit().putString("binance_secret", v).apply()

    fun setKrakenKey(v: String) =
        prefs.edit().putString("kraken_key", v).apply()

    fun setKrakenSecret(v: String) =
        prefs.edit().putString("kraken_secret", v).apply()

    fun setAddresses(v: String) =
        prefs.edit().putString("addresses", v).apply()

    fun setCoinGeckoApiKey(v: String) =
        prefs.edit().putString("cg_key", v).apply()

    fun getCoinGeckoApiKey() =
        prefs.getString("cg_key", "") ?: ""

    fun getBinanceKey() =
        prefs.getString("binance_key", "") ?: ""

    fun getBinanceSecret() =
        prefs.getString("binance_secret", "") ?: ""

    fun getKrakenKey() =
        prefs.getString("kraken_key", "") ?: ""

    fun getKrakenSecret() =
        prefs.getString("kraken_secret", "") ?: ""

    fun getAddresses() =
        prefs.getString("addresses", "") ?: ""

    fun isConfigured(): Boolean = getAddresses().isNotBlank()
            || (getBinanceKey().isNotBlank() && getBinanceSecret().isNotBlank())
}