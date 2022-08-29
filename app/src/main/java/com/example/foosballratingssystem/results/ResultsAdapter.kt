package com.example.foosballratingssystem.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foosballratingssystem.R
import com.example.foosballratingssystem.database.Results

class ResultsAdapter(private val clickHandler: ResultsAdapterOnClickHandler) :
    RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>() {
    private var results: List<Results>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result, parent, false)
        return ResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val result = results!![position]
        holder.playerOne.text = result.playerOne
        holder.playerTwo.text = result.playerTwo
        holder.scorePlayerOne.text = result.scorePlayerOne.toString()
        holder.scorePlayerTwo.text = result.scorePlayerTwo.toString()

        holder.itemView.setOnClickListener {
            clickHandler.onClick(result)
        }
    }

    override fun getItemCount(): Int {
        return if (null != results) results!!.size else 0
    }

    fun setResults(results: List<Results>?) {
        this.results = results
        notifyDataSetChanged()
    }

    inner class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playerOne: TextView = itemView.findViewById(R.id.player_one)
        var playerTwo: TextView = itemView.findViewById(R.id.player_two)
        var scorePlayerOne: TextView = itemView.findViewById(R.id.score_player_one)
        var scorePlayerTwo: TextView = itemView.findViewById(R.id.score_player_two)
    }

    interface ResultsAdapterOnClickHandler {
        fun onClick(result: Results)
    }
}