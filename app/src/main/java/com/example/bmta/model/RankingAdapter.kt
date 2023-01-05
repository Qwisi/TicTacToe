package com.example.bmta.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmta.R

class RankingAdapter(private var playerList: List<Player>) :
    RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).
            inflate(R.layout.player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = playerList[position]
        holder.viewName.text = player.name
        holder.viewScore.text = player.score.toString()
        holder.viewRank.text = (position + 1).toString() // set rank starting from 1
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewName: TextView = itemView.findViewById(R.id.textViewName)
        var viewScore: TextView = itemView.findViewById(R.id.textViewScore)
        var viewRank: TextView = itemView.findViewById(R.id.textViewRank)
    }
}
