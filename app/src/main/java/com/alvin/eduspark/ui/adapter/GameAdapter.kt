package com.alvin.eduspark.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alvin.eduspark.R
import com.alvin.eduspark.model.Game

class GameAdapter(private val games: List<Game>, private val onClick: (Game) -> Unit): RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    class GameViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvGameName)
        val category: TextView = view.findViewById(R.id.tvGameCategory)
        val players: TextView = view.findViewById(R.id.tvGamePlayers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_game, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.name.text = game.name
        holder.category.text = game.category
        holder.players.text = "${game.totalPlayer} players"

        holder.itemView.setOnClickListener { onClick(game) }

    }
}