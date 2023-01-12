package com.example.bmta

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bmta.databinding.ActivityGame3x3Binding
import com.example.bmta.model.ScoreData
import com.example.bmta.model.WriteAndRead

class Game3x3 : AppCompatActivity(){

    enum class Turn
    {
        NOUGHT,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var scoreData: ScoreData = ScoreData(0, 0)

    private var boardList = mutableListOf<Button>()

    private var firstPlayer : String = ""
    private var secondPlayer : String = ""

    private lateinit var binding : ActivityGame3x3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame3x3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initBoard()

        binding.back.setOnClickListener {
            WriteAndRead(firstPlayer, secondPlayer).updateScores(this, scoreData)
        }
    }

    private fun initBoard()
    {
        firstPlayer = intent.getStringExtra("firstPlayer").toString()
        secondPlayer = intent.getStringExtra("secondPlayer").toString()

        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)

        binding.turnTV.text = "$firstPlayer ( $CROSS )"
    }

    fun boardTapped(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT))
        {
            scoreData.noughtsScore++
            result("$secondPlayer Win!")
        }
        else if(checkForVictory(CROSS))
        {
            scoreData.crossesScore++
            result("$firstPlayer Win!")
        }else{
            if(fullBoard())
            {
                result("Draw")
            }
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
            turnText = "$firstPlayer ( $CROSS )"
        else if(currentTurn == Turn.NOUGHT)
            turnText = "$secondPlayer ( $NOUGHT )"

        binding.turnTV.text = turnText
    }

    companion object
    {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("turn", binding.turnTV.text.toString())

        outState.putString("a1", binding.a1.text.toString())
        outState.putString("a2", binding.a2.text.toString())
        outState.putString("a3", binding.a3.text.toString())
        outState.putString("b1", binding.b1.text.toString())
        outState.putString("b2", binding.b2.text.toString())
        outState.putString("b3", binding.b3.text.toString())
        outState.putString("c1", binding.c1.text.toString())
        outState.putString("c2", binding.c2.text.toString())
        outState.putString("c3", binding.c3.text.toString())

        outState.putInt("crossesScore", scoreData.crossesScore)
        outState.putInt("noughtsScore", scoreData.noughtsScore)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.turnTV.text = savedInstanceState.getString("turn")

        binding.a1.text = savedInstanceState.getString("a1")
        binding.a2.text = savedInstanceState.getString("a2")
        binding.a3.text = savedInstanceState.getString("a3")
        binding.b1.text = savedInstanceState.getString("b1")
        binding.b2.text = savedInstanceState.getString("b2")
        binding.b3.text = savedInstanceState.getString("b3")
        binding.c1.text = savedInstanceState.getString("c1")
        binding.c2.text = savedInstanceState.getString("c2")
        binding.c3.text = savedInstanceState.getString("c3")

        scoreData.crossesScore = savedInstanceState.getInt("crossesScore")
        scoreData.noughtsScore = savedInstanceState.getInt("noughtsScore")
    }

}













