package com.example.btctracker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.btctracker.data.Storage
import com.example.btctracker.repository.PortfolioRepository
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var text: TextView
    private lateinit var storage: Storage
    private lateinit var repo: PortfolioRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val refresh = findViewById<ImageButton>(R.id.btnRefresh)
        val settings = findViewById<ImageButton>(R.id.btnSettings)
        text = findViewById(R.id.txtOutput)

        storage = Storage(this)
        repo = PortfolioRepository(storage)

        refresh.setOnClickListener {
            load()
        }

        settings.setOnClickListener {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }

        if (!storage.isConfigured()) {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        } else {
            load()
        }
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    private fun load() {

        CoroutineScope(Dispatchers.Main).launch {

            try {
                text.text = repo.loadPortfolio()
            } catch (e: Exception) {
                text.text = e.toString()
            }
        }
    }
}