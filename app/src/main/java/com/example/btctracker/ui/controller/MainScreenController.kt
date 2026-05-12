package com.example.btctracker.ui.controller

import android.content.Intent
import android.widget.ImageButton
import android.widget.TextView
import com.example.btctracker.MainActivity
import com.example.btctracker.SettingsActivity
import com.example.btctracker.repository.PortfolioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenController(
    private val activity: MainActivity,
    private val output: TextView,
    private val refreshBtn: ImageButton,
    private val settingsBtn: ImageButton,
    private val fakeController: FakePriceController,
    private val repo: PortfolioRepository
) {

    fun init() {

        fakeController.bind()

        refreshBtn.setOnClickListener {
            load()
        }

        settingsBtn.setOnClickListener {
            activity.startActivity(
                Intent(activity, SettingsActivity::class.java)
            )
        }

        load()
    }

    fun load() {

        CoroutineScope(Dispatchers.Main).launch {
            output.text = "Loading..."

            try {

                val result = repo.loadPortfolio()

                output.text = result

            } catch (e: Exception) {

                output.text = "Error: ${e.message}"
            }
        }

    }
}