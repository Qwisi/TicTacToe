package com.example.bmta.model

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.example.bmta.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStreamReader

class WriteAndRead(private var firstPlayer: String? = null, private var secondPlayer: String? = null) {

    private lateinit var playerList: ArrayList<Player>
    private val filePlayers = "players.json"

    fun updateScores(context: Context, scoreData: ScoreData) {

        if (!fileExists(context)) {
            exceptionDialog(
                context, "File was not found\n" +
                        "Your progress will not be save"
            )
            return
        }

        playerList = readPlayers(context)
        if (containsPlayerName(playerList, firstPlayer)) {
            updateScore(firstPlayer, scoreData.crossesScore)
        } else {
            addPlayer(firstPlayer, scoreData.crossesScore)
        }

        if (containsPlayerName(playerList, secondPlayer)) {
            updateScore(secondPlayer, scoreData.noughtsScore)
        } else {
            addPlayer(secondPlayer, scoreData.noughtsScore)
        }

        updateFile(context)
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    private fun containsPlayerName(playerList: ArrayList<Player>, playerName: String?): Boolean {
        return playerList.any { it.name == playerName }
    }

    private fun updateScore(playerName: String?, score: Int) {
        val player = playerList.find { it.name == playerName }
        if (player != null) {
            player.score += score
        }
    }

    private fun addPlayer(playerName: String?, score: Int) {
        playerName?.let { playerList.add(Player(it, score)) }
    }

    private fun updateFile(context: Context) {
        val jsonString = Gson().toJson(playerList)
        val fileOutputStream = context.openFileOutput(filePlayers, Context.MODE_PRIVATE)
        fileOutputStream.write(jsonString.toByteArray())
        fileOutputStream.close()
    }

    fun readPlayers(context: Context): ArrayList<Player> {
        if(fileExists(context)){
            val fileInputStream = context.openFileInput(filePlayers)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val playerType = object : TypeToken<List<Player>>() {}.type
            return Gson().fromJson(inputStreamReader, playerType)
        }
        return ArrayList()
    }

    private fun fileExists(context: Context): Boolean {
        val file = File(context.filesDir, filePlayers)
        return file.exists()
    }

    private fun exceptionDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

}