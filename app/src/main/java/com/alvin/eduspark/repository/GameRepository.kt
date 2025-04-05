package com.alvin.eduspark.repository

import com.alvin.eduspark.http.HttpHandler
import com.alvin.eduspark.model.Game
import com.alvin.eduspark.model.Leaderboard
import org.json.JSONArray
import org.json.JSONObject

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

    fun getLeaderboards(endpoint: String): List<Leaderboard> {
        val leaderboards = mutableListOf<Leaderboard>()
        try {
            val response = HttpHandler.getRequest(endpoint)
            if (response.isNotEmpty()) {
                val jsonArray = JSONArray(response)
                parseLeaderboardsFromJson(leaderboards, jsonArray)
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return leaderboards
    }

    private fun parseLeaderboardsFromJson(
        leaderboards: MutableList<Leaderboard>,
        jsonArray: JSONArray
    ) {
        for (i in 0 until jsonArray.length()) {
            try {
                val jsonObject = jsonArray.getJSONObject(i)
                leaderboards.add(
                    Leaderboard(
                        id = jsonObject.getInt("id"),
                        nickname = jsonObject.getString("nickname"),
                        totalPoint = jsonObject.getInt("totalPoint")
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postScoreToLeaderboard(gameId: Int, nickname: String, totalPoint: Int): String {
        val json = JSONObject().apply {
            put("gameID", gameId)
            put("nickname", nickname)
            put("totalPoint", totalPoint)
        }
        return HttpHandler.postRequest("leaderboards", json)
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