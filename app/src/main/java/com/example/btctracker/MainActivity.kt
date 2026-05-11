package com.example.btctracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        text = TextView(this)

        setContentView(text)

        storage = Storage(this)
        repo = PortfolioRepository(storage)

        if (!storage.isConfigured()) {

            startActivity(
                Intent(this, SettingsActivity::class.java)
            )

            text.text = "Open settings to configure"

            return
        }

        load()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menu.add(0, 1, 0, "Refresh")
        menu.add(0, 2, 1, "Settings")

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            1 -> {
                load()
                true
            }

            2 -> {
                startActivity(
                    Intent(this, SettingsActivity::class.java)
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}