package com.example.btctracker.ui.controller

import android.widget.Button
import android.widget.EditText
import com.example.btctracker.data.Storage

class SettingsScreenController(
    private val storage: Storage,
    private val key: EditText,
    private val secret: EditText,
    private val addresses: EditText,
    private val cg: EditText,
    private val saveBtn: Button,
    private val onSaved: () -> Unit
) {

    fun init() {

        key.setText(storage.getBinanceKey())
        secret.setText(storage.getBinanceSecret())
        addresses.setText(storage.getAddresses())
        cg.setText(storage.getCoinGeckoApiKey())

        saveBtn.setOnClickListener {

            storage.setBinanceKey(key.text.toString())
            storage.setBinanceSecret(secret.text.toString())
            storage.setAddresses(addresses.text.toString())
            storage.setCoinGeckoApiKey(cg.text.toString())

            onSaved()
        }
    }
}