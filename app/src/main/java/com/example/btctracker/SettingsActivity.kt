package com.example.btctracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val storage = Storage(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val key = EditText(this)
        key.hint = "Binance API Key"
        key.setText(storage.getBinanceKey())

        val secret = EditText(this)
        secret.hint = "Binance Secret"
        secret.setText(storage.getBinanceSecret())

        val addresses = EditText(this)
        addresses.hint = "BTC addresses comma separated"
        addresses.setText(storage.getAddresses())

        val fake = EditText(this)
        fake.hint = "Fake BTC price USD"
        fake.setText(storage.getFakePrice())

        val save = Button(this)
        save.text = "Save"

        save.setOnClickListener {

            storage.setBinanceKey(key.text.toString())
            storage.setBinanceSecret(secret.text.toString())
            storage.setAddresses(addresses.text.toString())
            storage.setFakePrice(fake.text.toString())

            finish()
        }

        layout.addView(key)
        layout.addView(secret)
        layout.addView(addresses)
        layout.addView(fake)
        layout.addView(save)

        setContentView(layout)
    }
}