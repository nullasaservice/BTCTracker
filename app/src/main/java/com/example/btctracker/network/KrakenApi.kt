package com.example.btctracker.network

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object KrakenApi {
    private const val BASE_URL = "https://api.kraken.com"
    private const val BALANCE_PATH = "/0/private/Balance"

    suspend fun getBTC(key: String, secret: String): Double {
        return withContext(Dispatchers.IO) {
            val nonce = System.currentTimeMillis() * 1000
            val postData = "nonce=$nonce"
            val signature = createSignature(BALANCE_PATH, nonce, postData, secret)

            val jsonString = HttpClient.post(
                url = "$BASE_URL$BALANCE_PATH",
                body = postData,
                headers = mapOf(
                    "API-Key" to key,
                    "API-Sign" to signature,
                    "Content-Type" to "application/x-www-form-urlencoded"
                )
            )

            val json = JSONObject(jsonString)
            val errors = json.getJSONArray("error")

            if (errors.length() > 0) {
                throw Exception(errors.join(", "))
            }

            val result = json.getJSONObject("result")

            when {
                result.has("XXBT") ->
                    result.getString("XXBT").toDouble()

                result.has("XBT") ->
                    result.getString("XBT").toDouble()

                else ->
                    0.0
            }
        }
    }

    private fun createSignature(
        path: String,
        nonce: Long,
        postData: String,
        secret: String
    ): String {
        val sha256Input = nonce.toString() + postData

        val sha256 = MessageDigest.getInstance("SHA-256")
                .digest(
                    sha256Input.toByteArray(
                        StandardCharsets.UTF_8
                    )
                )

        val message = path.toByteArray(StandardCharsets.UTF_8) + sha256
        val secretBytes = Base64.decode(secret, Base64.DEFAULT)
        val mac = Mac.getInstance("HmacSHA512")

        mac.init(SecretKeySpec(secretBytes, "HmacSHA512"))

        val signature = mac.doFinal(message)

        return Base64.encodeToString(signature, Base64.NO_WRAP)
    }
}