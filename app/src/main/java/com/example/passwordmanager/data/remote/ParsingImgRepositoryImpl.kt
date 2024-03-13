package com.example.passwordmanager.data.remote

import android.os.AsyncTask
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import java.io.IOException

class ParsingImgRepositoryImpl {

    private val client = OkHttpClient()

    fun fetchHtml(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()
        var responseBody: String? = null


        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                responseBody = response.body?.string()
            }

        }
        )

        return responseBody
    }

    fun extractIconLinkFromHtml(html: String): String? {
        val doc = Jsoup.parse(html)
        val link = doc.select("link[rel=icon]").firstOrNull()
        return link?.attr("href")
    }
}