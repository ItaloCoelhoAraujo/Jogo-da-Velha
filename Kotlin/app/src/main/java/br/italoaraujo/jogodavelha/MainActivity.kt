package br.italoaraujo.jogodavelha

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private val viewModel: GameViewModel = ViewModelProviders.of(this)
        .get(GameViewModel::class.java)
    private val buttons = Array(3) { arrayOfNulls<Button>(3) }
    private val resetButton: Button = findViewById(R.id.resetButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtons()
        setOnClickListeners()
        refillButtons()
    }

    //Whenever the Activity is recreated this will make sure the buttons will retain
    //their text value and be not clickable as they should be
    private fun refillButtons() {
        for (i in buttons.indices) {
            for (j in buttons[0].indices) {
                if (viewModel.matrix[i][j] != null) {
                    buttons[i][j]!!.text = viewModel.matrix[i][j]
                    buttons[i][j]!!.isClickable = false
                }
            }
        }
    }

    private fun initButtons() {
        buttons[0][0] = findViewById(R.id.button)
        buttons[0][1] = findViewById(R.id.button2)
        buttons[0][2] = findViewById(R.id.button3)
        buttons[1][0] = findViewById(R.id.button4)
        buttons[1][1] = findViewById(R.id.button5)
        buttons[1][2] = findViewById(R.id.button6)
        buttons[2][0] = findViewById(R.id.button7)
        buttons[2][1] = findViewById(R.id.button8)
        buttons[2][2] = findViewById(R.id.button9)
    }

    private fun setOnClickListeners() {
        for (i in buttons.indices) {
            for (j in buttons[0].indices) {
                buttons[i][j]!!.setOnClickListener {
                    viewModel.play(buttons[i][j]!!, i, j)
                    viewModel.checkWinner(this@MainActivity, buttons, resetButton)
                    buttons[i][j]!!.isClickable = false
                }
            }
        }
        resetButton.setOnClickListener {
            viewModel.resetGame(buttons)
            resetButton.isEnabled = false
        }
    }
}