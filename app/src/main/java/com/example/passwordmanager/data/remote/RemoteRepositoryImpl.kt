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
        try {
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback("")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        callback(fetchUrlIcon(response.body?.string() ?: "", url))

                    } else {
                        callback("")
                    }
                }

            })
        } catch (ex: Exception) {
            callback("")
        }

    }

    private fun fetchUrlIcon(html: String, url: String): String {
        val doc = Jsoup.parse(html)
        val links = doc.select("head > link[href]")

        for (link in links) {
            val href = link.attr("href")

            for (format in FORMAT_IMG) {
                if (format in href) {

                    return validateUrlIcon(url, href)
                }
            }
        }

        return ""
    }

    companion object {
        private const val HTTP = "http"
        private val FORMAT_IMG = listOf(
            "png",
            "jpeg",
            "jpg",
            "ico",
            "bmp",
            "webp",
            "gif"
        )

        fun validateUrlIcon(url: String, urlIcon: String): String {
            return if (HTTP in urlIcon) urlIcon
            else "$url$urlIcon"
        }
    }

}