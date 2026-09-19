package com.example.btctracker.ui.controller

import android.widget.Button
import android.widget.EditText
import com.example.btctracker.data.Storage

class SettingsScreenController(
    private val storage: Storage,
    private val binanceKey: EditText,
    private val binanceSecret: EditText,
    private val krakenKey: EditText,
    private val krakenSecret: EditText,
    private val addresses: EditText,
    private val cg: EditText,
    private val saveBtn: Button,
    private val onSaved: () -> Unit
) {

    fun init() {

        binanceKey.setText(storage.getBinanceKey())
        binanceSecret.setText(storage.getBinanceSecret())
        krakenKey.setText(storage.getKrakenKey())
        krakenSecret.setText(storage.getKrakenSecret())
        addresses.setText(storage.getAddresses())
        cg.setText(storage.getCoinGeckoApiKey())

        saveBtn.setOnClickListener {

            storage.setBinanceKey(binanceKey.text.toString())
            storage.setBinanceSecret(binanceSecret.text.toString())
            storage.setKrakenKey(krakenKey.text.toString())
            storage.setKrakenSecret(krakenSecret.text.toString())
            storage.setAddresses(addresses.text.toString())
            storage.setCoinGeckoApiKey(cg.text.toString())

            onSaved()
        }
    }
}