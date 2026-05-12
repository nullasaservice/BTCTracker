package com.example.btctracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.btctracker.data.Storage

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        val storage = Storage(this)

        val key = findViewById<EditText>(R.id.etBinanceKey)
        val secret = findViewById<EditText>(R.id.etBinanceSecret)
        val addresses = findViewById<EditText>(R.id.etAddresses)
        val fake = findViewById<EditText>(R.id.etFakePrice)
        val cg = findViewById<EditText>(R.id.etCgKey)

        val save = findViewById<Button>(R.id.btnSave)

        key.setText(storage.getBinanceKey())
        secret.setText(storage.getBinanceSecret())
        addresses.setText(storage.getAddresses())
        fake.setText(storage.getFakePrice())
        cg.setText(storage.getCoinGeckoApiKey())

        save.setOnClickListener {

            storage.setBinanceKey(key.text.toString())
            storage.setBinanceSecret(secret.text.toString())
            storage.setAddresses(addresses.text.toString())
            storage.setFakePrice(fake.text.toString())
            storage.setCoinGeckoApiKey(cg.text.toString())

            finish()
        }
    }
}