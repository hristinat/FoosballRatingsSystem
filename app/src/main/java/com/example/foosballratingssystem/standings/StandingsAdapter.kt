package com.example.foosballratingssystem.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foosballratingssystem.R

class StandingsAdapter :
    RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder>() {
    private var standings: List<Standings>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.standings, parent, false)
        return StandingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        val standings = standings!![position]
        holder.rating.text = standings.ratings.toString()
        holder.player.text = standings.person
        holder.result.text = standings.result.toString()
    }

    override fun getItemCount(): Int {
        return if (null != standings) standings!!.size else 0
    }

    fun setStandings(standings: List<Standings>?) {
        this.standings = standings
        notifyDataSetChanged()
    }

    inner class StandingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rating: TextView = itemView.findViewById(R.id.rating)
        var player: TextView = itemView.findViewById(R.id.player)
        var result: TextView = itemView.findViewById(R.id.result)
    }
}
