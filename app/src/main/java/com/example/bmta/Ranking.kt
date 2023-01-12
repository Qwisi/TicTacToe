package com.example.bmta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmta.databinding.ActivityRankingBinding
import com.example.bmta.model.Player
import com.example.bmta.model.RankingAdapter
import com.example.bmta.model.WriteAndRead

class Ranking : AppCompatActivity() {

    private lateinit var binding: ActivityRankingBinding
    private var playerList = ArrayList<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        playerList = WriteAndRead().readPlayers(this)

        val rvRanking = binding.rvRanking
        val adapter = RankingAdapter(playerList)

        rvRanking.setHasFixedSize(true)
        rvRanking.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvRanking.adapter = adapter
    }
}