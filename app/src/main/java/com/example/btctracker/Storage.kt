package com.example.btctracker

import android.content.Context

class Storage(context: Context) {

    private val prefs =
        context.getSharedPreferences("btc", Context.MODE_PRIVATE)

    fun setBinanceKey(v: String) {
        prefs.edit().putString("key", v).apply()
    }

    fun setBinanceSecret(v: String) {
        prefs.edit().putString("secret", v).apply()
    }

    fun setAddresses(v: String) {
        prefs.edit().putString("addresses", v).apply()
    }

    fun setFakePrice(v: String) {
        prefs.edit().putString("fake", v).apply()
    }

    fun getBinanceKey(): String {
        return prefs.getString("key", "") ?: ""
    }

    fun getBinanceSecret(): String {
        return prefs.getString("secret", "") ?: ""
    }

    fun getAddresses(): String {
        return prefs.getString("addresses", "") ?: ""
    }

    fun getFakePrice(): String {
        return prefs.getString("fake", "") ?: ""
    }

    fun isConfigured(): Boolean {
        return getAddresses().isNotBlank()
    }
}