package com.example.bmta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmta.databinding.ActivityRankingBinding
import com.example.bmta.model.Player
import com.example.bmta.model.RankingAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Ranking : AppCompatActivity() {

    private lateinit var binding: ActivityRankingBinding
    var playerList = arrayListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        readPlayers()
        val rvRanking = binding.rvRanking
        val adapter = RankingAdapter(playerList)

        rvRanking.setHasFixedSize(true)
        rvRanking.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvRanking.adapter = adapter

    }

    private fun readPlayers() {
        val jsonString: String = assets.open("players.json").bufferedReader().use { it.readText() }
        val playerType = object : TypeToken<List<Player>>() {}.type
        playerList = Gson().fromJson<ArrayList<Player>>(jsonString, playerType)
    }

}