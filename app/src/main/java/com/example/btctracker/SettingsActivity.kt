package com.example.btctracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.btctracker.data.Storage
import com.example.btctracker.ui.controller.SettingsScreenController

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val storage = Storage(this)

        val controller = SettingsScreenController(
            storage,
            findViewById(R.id.etBinanceKey),
            findViewById(R.id.etBinanceSecret),
            findViewById(R.id.etAddresses),
            findViewById(R.id.etCgKey),
            findViewById(R.id.btnSave)
        ) {
            finish()
        }

        controller.init()
    }
}