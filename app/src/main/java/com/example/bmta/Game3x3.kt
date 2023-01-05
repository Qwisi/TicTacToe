package com.example.bmta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bmta.databinding.ActivityGame3x3Binding
import com.example.bmta.model.ScoreData
import com.google.gson.Gson
import java.io.InputStream

class Game3x3 : AppCompatActivity(){

    enum class Turn
    {
        NOUGHT,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var scoreData = ScoreData(0, 0)

    private var boardList = mutableListOf<Button>()

    private val fileName = "scores.json"

    private lateinit var binding : ActivityGame3x3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame3x3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        readScores()
        binding.back.setOnClickListener {
            //updateScores()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun readScores(){
        val inputStream : InputStream = assets.open(fileName)
        val jsonString: String = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()
        val scores = gson.fromJson(jsonString, ScoreData::class.java)

        scoreData.crossesScore = scores.crossesScore
        scoreData.noughtsScore = scores.noughtsScore
    }

    /*private fun updateScores() {
        val inputStream: InputStream = assets.open(fileName)
        val jsonString: String = inputStream.bufferedReader().use { it.readText() }
        inputStream.close()
        val json: JSONObject = JSONObject(jsonString)
        json.put("crossesScore", scoreData.crossesScore)
        json.put("noughtsScore", scoreData.noughtsScore)

        try {
            val outputStream: OutputStream = assets.openFd(fileName).createOutputStream()
            outputStream.write(json.toString().toByteArray())
            outputStream.close()
        }catch (e: java.lang.Exception){
            Log.e("TAG", "Exception occurred", e)
        }

    }*/

    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT))
        {
            scoreData.noughtsScore++
            result("Noughts Win!")
        }
        else if(checkForVictory(CROSS))
        {
            scoreData.crossesScore++
            result("Crosses Win!")
        }

        if(fullBoard())
        {
            result("Draw")
        }

    }

    private fun checkForVictory(s: String): Boolean
    {
        //Horizontal Victory
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        //Vertical Victory
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        //Diagonal Victory
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String)
    {
        val message = "\nNoughts ${scoreData.noughtsScore}\n\nCrosses ${scoreData.crossesScore}"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard()
    {
        for(button in boardList)
        {
            button.text = ""
        }

        if(firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if(firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(currentTurn == Turn.NOUGHT)
        {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        }
        else if(currentTurn == Turn.CROSS)
        {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel()
    {
        var turnText = ""
        if(currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if(currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"

        binding.turnTV.text = turnText
    }

    companion object
    {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}