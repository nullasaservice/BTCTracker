package com.example.btctracker

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.btctracker.data.Storage
import com.example.btctracker.repository.PortfolioRepository
import com.example.btctracker.ui.controller.FakePriceController
import com.example.btctracker.ui.controller.MainScreenController

class MainActivity : AppCompatActivity() {

    private lateinit var controller: MainScreenController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val output = findViewById<TextView>(R.id.txtOutput)
        val refresh = findViewById<ImageButton>(R.id.btnRefresh)
        val settings = findViewById<ImageButton>(R.id.btnSettings)
        val fakeBtn = findViewById<Button>(R.id.btnFakePrice)

        val repo = PortfolioRepository(Storage(this))

        val fakeController = FakePriceController(fakeBtn) {
            controller.load()
        }

        controller = MainScreenController(
            this,
            output,
            refresh,
            settings,
            fakeController,
            repo
        )

        controller.init()
    }
}