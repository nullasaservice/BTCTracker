package com.example.btctracker.ui

import android.content.Context
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

object FakePriceDialog {

    fun show(
        context: Context,
        onSet: (Double) -> Unit
    ) {

        val input = EditText(context).apply {
            hint = "Fake BTC price (USD)"
            inputType =
                InputType.TYPE_CLASS_NUMBER or
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        AlertDialog.Builder(context)
            .setTitle("Set Fake BTC Price")
            .setView(input)
            .setPositiveButton("Set") { _, _ ->

                val value = input.text.toString().toDoubleOrNull()

                if (value != null) {
                    onSet(value)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}