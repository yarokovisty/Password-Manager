package com.example.passwordmanager.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class RemoteRepositoryImpl {

    private val client = OkHttpClient()

    fun getUrlIcon(url: String, callback: (String) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    callback(fetchUrlIcon(response.body?.string() ?: ""))

                } else {
                    callback("")
                }
            }

        })
    }

    private fun fetchUrlIcon(html: String): String {
        val doc = Jsoup.parse(html)
        val link = doc.select("link[rel=icon]")
        return link.attr("href")
    }
}