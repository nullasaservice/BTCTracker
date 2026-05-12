package com.example.btctracker.ui.controller

import android.widget.Button
import com.example.btctracker.state.AppState
import com.example.btctracker.ui.FakePriceDialog

class FakePriceController(
    private val button: Button,
    private val onChanged: () -> Unit
) {

    fun bind() {
        render()
    }

    fun render() {

        if (AppState.fakePriceUsd == null) {

            button.text = "Add Fake Price"

            button.setOnClickListener {
                FakePriceDialog.show(button.context) { value ->
                    AppState.fakePriceUsd = value
                    render()
                    onChanged()
                }
            }

        } else {

            button.text = "Remove Fake Price"

            button.setOnClickListener {
                AppState.fakePriceUsd = null
                render()
                onChanged()
            }
        }
    }
}