package com.example.organizer.api.requests

import com.example.organizer.data.entity.ExerciseEntity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Wger() {
    private val client = OkHttpClient()

    fun getPage(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).execute().use { response ->
            response.body?.string() ?: ""
        }
    }

    fun getExerciseCard(): MutableList<ExerciseEntity> {
        var count = 0
        val result = mutableListOf<ExerciseEntity>()
        var next = "https://wger.de/api/v2/exerciseinfo"
        var hasNext = true
        while(count < 10) {
            count++
            val page = getPage(next)

            val root = JSONObject(page)
            val results = root.getJSONArray("results")


            for (i in 0 until results.length()) {
                var name = ""
                val exercise = results.getJSONObject(i)
                val imagesArray = exercise.optJSONArray("images")
                //image
                val image = if (imagesArray.length() > 0) {
                    "https://wger.de" + imagesArray.getJSONObject(0).getString("image")
                } else {
                    continue
                }
                //category
                val category = exercise.optJSONObject("category")
                    ?.optString("name") ?: ""

                val translations = exercise.optJSONArray("translations") ?: continue
                for (j in 0 until translations.length()) {
                    val t = translations.getJSONObject(j)
                    if (t.getInt("language") == 2) {
                        //name
                        name = t.getString("name")
                        break
                    }
                }

                result.add(ExerciseEntity(name = name, imageUrl = image, category = category))
            }
            next = root.getString("next")
            hasNext = !next.isBlank()
        }
        return result
    }
}