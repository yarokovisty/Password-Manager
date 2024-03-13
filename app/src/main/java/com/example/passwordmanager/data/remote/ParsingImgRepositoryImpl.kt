package com.example.passwordmanager.data.remote

import android.os.AsyncTask
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import java.io.IOException

class ParsingImgRepositoryImpl(private val url: String) {

    private val client = OkHttpClient()

    fun fetchHtml(callback: (String) -> Unit) {
        try {
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response.body?.string() ?: "")
                }

            })
        } catch (ex: Exception) {
            callback("")
        }

    }

    fun extractIconLinkFromHtml(html: String): String {
        val doc = Jsoup.parse(html)
        val link = doc.select("link[rel=icon]").attr("href")
        return "https:$link"
    }

    private fun correctUrlIcon(link: String) : String {
        return if (HTTP !in link) {
            "$url$link"
        } else {
            link
        }
    }

    companion object{
        const val HTTP = "http"
    }
}