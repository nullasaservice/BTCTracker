package com.example.btctracker.data

import android.content.Context

class Storage(context: Context) {

    private val prefs =
        context.getSharedPreferences("btc", Context.MODE_PRIVATE)

    fun setBinanceKey(v: String) =
        prefs.edit().putString("key", v).apply()

    fun setBinanceSecret(v: String) =
        prefs.edit().putString("secret", v).apply()

    fun setAddresses(v: String) =
        prefs.edit().putString("addresses", v).apply()

    fun setFakePrice(v: String) =
        prefs.edit().putString("fake", v).apply()

    fun getBinanceKey() =
        prefs.getString("key", "") ?: ""

    fun getBinanceSecret() =
        prefs.getString("secret", "") ?: ""

    fun getAddresses() =
        prefs.getString("addresses", "") ?: ""

    fun getFakePrice() =
        prefs.getString("fake", "") ?: ""

    fun isConfigured(): Boolean =
        getAddresses().isNotBlank()
}