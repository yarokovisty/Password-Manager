package com.example.passwordmanager.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup

object ParsingImgImpl {

    fun fetchHtml(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()

        return response.body?.string()
    }

    fun extractIconLinkFromHtml(html: String): String? {
        val doc = Jsoup.parse(html)
        val link = doc.select("link[rel=icon]").firstOrNull()
        return link?.attr("href")
    }
}