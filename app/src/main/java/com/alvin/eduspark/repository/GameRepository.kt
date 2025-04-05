package com.alvin.eduspark.repository

import com.alvin.eduspark.http.HttpHandler
import com.alvin.eduspark.model.Game
import org.json.JSONArray

object GameRepository {

    fun getGames(endpoint: String): List<Game> {
        val games = mutableListOf<Game>()
        try {
            val response = HttpHandler.getRequest(endpoint)
            if (response.isNotEmpty()) {
                val jsonArray = JSONArray(response)
                parseGamesFromJson(games, jsonArray)
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return games
    }

    private fun parseGamesFromJson(games: MutableList<Game>, jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            try {
                val jsonObject = jsonArray.getJSONObject(i)
                games.add(
                    Game(
                        id = jsonObject.getInt("id"),
                        name = jsonObject.getString("name"),
                        category = jsonObject.getString("category"),
                        totalPlayer = jsonObject.getInt("totalPlayer")
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}