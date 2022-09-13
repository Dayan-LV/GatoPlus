package com.example.mygatodayan

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        btnSelected(v as Button)
    }

    // Inicializar X y O
    private val x="X"
    private val o="0"

    private var xWins = 0
    private var oWins = 0

    private var cells= mutableMapOf<Int,String>()
    private var isX=true
    private var winner:String=""
    private val maxCell=36
    private lateinit var tvResult:TextView

    private var btns= arrayOfNulls<Button>(maxCell)

    // Inicializar la matriz de combinaciones
    private val winningPositions:Array<IntArray> = arrayOf(

        // FILAS

        intArrayOf(0,1,2,3),
        intArrayOf(1,2,3,4),
        intArrayOf(2,3,4,5),

        intArrayOf(6,7,8,9),
        intArrayOf(7,8,9,10),
        intArrayOf(8,9,10,11),

        intArrayOf(12,13,14,15),
        intArrayOf(13,14,15,16),
        intArrayOf(14,15,16,17),

        intArrayOf(18,19,20,21),
        intArrayOf(19,20,21,22),
        intArrayOf(20,21,22,23),

        intArrayOf(24,25,26,27),
        intArrayOf(25,26,27,28),
        intArrayOf(26,27,28,29),

        intArrayOf(30,31,32,33),
        intArrayOf(31,32,33,34),
        intArrayOf(32,33,34,35),

        // COLUMNAS

        intArrayOf(0,6,12,18),
        intArrayOf(6,12,18,24),
        intArrayOf(12,18,24,30),
        intArrayOf(1,7,13,19),
        intArrayOf(7,13,19,25),
        intArrayOf(13,19,25,31),
        intArrayOf(2,8,14,20),
        intArrayOf(8,14,20,26),
        intArrayOf(14,20,26,32),
        intArrayOf(3,9,15,21),
        intArrayOf(9,15,21,27),
        intArrayOf(15,21,27,33),
        intArrayOf(4,10,16,22),
        intArrayOf(10,16,23,28),
        intArrayOf(16,22,28,34),
        intArrayOf(5,11,17,23),
        intArrayOf(11,17,24,29),
        intArrayOf(17,23,29,35),

        /////////////////////////////////////////

        intArrayOf(2,9,16,23),
        intArrayOf(1,8,15,22),
        intArrayOf(8,15,22,29),
        intArrayOf(0,7,14,21),
        intArrayOf(7,14,21,28),
        intArrayOf(14,21,28,35),
        intArrayOf(6,13,20,27),
        intArrayOf(13,20,27,34),
        intArrayOf(12,19,26,33),

        intArrayOf(3,8,13,18),
        intArrayOf(4,9,14,19),
        intArrayOf(9,14,19,24),
        intArrayOf(5,10,15,20),
        intArrayOf(10,15,20,25),
        intArrayOf(15,20,25,30),
        intArrayOf(11,16,21,26),
        intArrayOf(16,21,26,31),
        intArrayOf(17,22,27,32)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         initTable()
    }

    private fun initTable() {
        tvResult=findViewById(R.id.txtResult)

        for(i in 1..maxCell){
            var button=findViewById<Button>(resources.getIdentifier("btn$i", "id", packageName ))
            button.setOnClickListener(this)
            btns[i-1]=button
        }
    }

    private fun btnSelected(button: Button){
        var index=0
        when(button.id){
            R.id.btn1->index=0
            R.id.btn2->index=1
            R.id.btn3->index=2
            R.id.btn4->index=3
            R.id.btn5->index=4
            R.id.btn6->index=5
            R.id.btn7->index=6
            R.id.btn8->index=7
            R.id.btn9->index=8
            R.id.btn10->index=9
            R.id.btn11->index=10
            R.id.btn12->index=11
            R.id.btn13->index=12
            R.id.btn14->index=13
            R.id.btn15->index=14
            R.id.btn16->index=15
            R.id.btn17->index=16
            R.id.btn18->index=17
            R.id.btn19->index=18
            R.id.btn20->index=19
            R.id.btn21->index=20
            R.id.btn22->index=21
            R.id.btn23->index=22
            R.id.btn24->index=23
            R.id.btn25->index=24
            R.id.btn26->index=25
            R.id.btn27->index=26
            R.id.btn28->index=27
            R.id.btn29->index=28
            R.id.btn30->index=29
            R.id.btn31->index=30
            R.id.btn32->index=31
            R.id.btn33->index=32
            R.id.btn34->index=33
            R.id.btn35->index=34
            R.id.btn36->index=35
        }
        startGame(index,button)
        checkWinner()
        updateState()
    }
    private fun checkWinner(){
        if(cells.isNotEmpty()) {
            for (combination in winningPositions) {
                var (a, b, c, d) = combination

                if (cells[a] != null && cells[a] == cells[b] && cells[a] == cells[c] && cells[a] == cells[d]) {
                    this.winner = cells[a].toString()
                }
            }
        }
    }
    private fun updateState(){
        when{
            winner.isNotEmpty()->{
                var rlWin = findViewById<RelativeLayout>(R.id.rlWin)
                var tvWin = findViewById<TextView>(R.id.tvWin)
                var resetButton = findViewById<Button>(R.id.btnReset)
                rlWin.visibility = RelativeLayout.VISIBLE
                tvWin.text = "Ha ganado $winner"
                resetButton.isClickable = false

                tvResult=findViewById(R.id.txtResult)

                for(i in 1..maxCell){
                    var button=findViewById<Button>(resources.getIdentifier("btn$i", "id", packageName ))
                    button.setOnClickListener(null)
                }

                if (winner == x) {
                    xWins++
                } else {
                    oWins++
                }

            }
            cells.size==maxCell->{
                tvResult.text="Empate"
            }
            else->{
                tvResult.text=resources.getString(R.string.next_player,if(isX)x else o)
            }
        }
    }

    public fun closeWin(v: View) {
        var rlWin = findViewById<RelativeLayout>(R.id.rlWin)
        var resetButton = findViewById<Button>(R.id.btnReset)
        rlWin.visibility = RelativeLayout.GONE
        resetButton.isClickable = true

        newGame()
        initTable()
    }


    private fun startGame(index:Int, button: Button){

        if(!winner.isNullOrEmpty()){
            Toast.makeText(this,"El juego ha acabado", Toast.LENGTH_LONG).show()
            return
        }
        when{
            isX->cells[index]=x
            else->cells[index]=o
        }

        // Ponerle X o O
        button.text=cells[index]

        // Desactivar la celda
        button.isEnabled=false

        // Cambiar turno
        isX=!isX
    }
    fun resetButton(){
        for(i in 1..maxCell){
            var button=btns[i-1]
            button?.text=""
            button?.isEnabled=true
        }
    }
    fun newGame(){
        var tvScore1 = findViewById<TextView>(R.id.tvScore1)
        var tvScore2 = findViewById<TextView>(R.id.tvScore2)

        tvScore1.text = xWins.toString()
        tvScore2.text = oWins.toString()

        cells= mutableMapOf()
        isX=true
        winner=""
        tvResult.text=resources.getString(R.string.next_player, x)
        tvResult.setTextColor(Color.BLACK)
        resetButton()
    }
    fun reset(View: View) {
        newGame()
    }
}