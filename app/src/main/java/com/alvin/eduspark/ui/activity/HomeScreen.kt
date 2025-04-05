package com.alvin.eduspark.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.alvin.eduspark.R
import com.alvin.eduspark.model.Game
import com.alvin.eduspark.repository.GameRepository
import com.alvin.eduspark.ui.adapter.GameAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreen : AppCompatActivity() {

    private lateinit var rvGames: RecyclerView
    private val games = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        rvGames = findViewById(R.id.rvGames)

        setupRv()
        loadGames()
    }

    private fun loadGames() {
        val gameAdapter = GameAdapter(games) {

        }
        rvGames.apply {
            adapter = gameAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupRv() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = GameRepository.getGames("games")
                if (response.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        games.clear()
                        games.addAll(response)
                        rvGames.adapter?.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                lifecycleScope.launch(Dispatchers.Main) {
                    e.printStackTrace()
                }
            }
        }
    }
}