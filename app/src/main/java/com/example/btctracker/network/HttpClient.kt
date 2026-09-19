package com.example.btctracker.network

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object HttpClient {

    fun get(url: String, headers: Map<String, String> = emptyMap()): String {
        val connection = URL(url).openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.connectTimeout = 10_000
        connection.readTimeout = 10_000

        connection.setRequestProperty("User-Agent", "BTCTracker/1.0")

        for ((k, v) in headers) {
            connection.setRequestProperty(k, v)
        }

        val code = connection.responseCode

        val stream =
            if (code in 200..299)
                connection.inputStream
            else
                connection.errorStream

        val responseBody = stream.bufferedReader().use {
            it.readText()
        }

        if (code !in 200..299) {
            throw IOException("HTTP $code\n$url\n$responseBody")
        }

        return responseBody
    }

    fun post(url: String, body: String, headers: Map<String, String> = emptyMap()): String {
        val connection = URL(url).openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.connectTimeout = 10_000
        connection.readTimeout = 10_000
        connection.doOutput = true

        connection.setRequestProperty("User-Agent", "BTCTracker/1.0")

        for ((k, v) in headers) {
            connection.setRequestProperty(k, v)
        }

        connection.outputStream.use { output ->
            output.write(body.toByteArray(Charsets.UTF_8))
        }

        val code = connection.responseCode

        val stream =
            if (code in 200..299)
                connection.inputStream
            else
                connection.errorStream

        val responseBody = stream.bufferedReader().use {
            it.readText()
        }

        if (code !in 200..299) {
            throw IOException("HTTP $code\n$url\n$responseBody")
        }

        return responseBody
    }
}