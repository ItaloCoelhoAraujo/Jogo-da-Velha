package br.italoaraujo.jogodavelha

import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val arrayX: Array<String>
    private val arrayO: Array<String>
    private var turn: String
    private var playCount: Int
    val matrix: Array<Array<String?>> = Array(3) { arrayOfNulls<String>(3) }

    fun play(button: Button, X: Int, Y: Int) {
        matrix[X][Y] = turn
        button.text = matrix[X][Y]
        playCount++
        if (turn == "O") {
            turn = "X"
            return
        }
        if (turn == "X") {
            turn = "O"
        }
    }

    fun checkWinner(
        context: Context,
        buttons: Array<Array<Button?>>,
        reset: Button
    ) {
        if (checkRows(context, buttons, reset)) return
        if (checkColumns(context, buttons, reset)) return
        if (checkDiagonals(context, buttons, reset)) return
        if (playCount == 9) showWinner("Empate!", context, buttons, reset)
    }

    private fun showWinner(
        winner: String,
        context: Context,
        buttons: Array<Array<Button?>>,
        reset: Button
    ) {
        val builder = AlertDialog.Builder(context)
        val message: String = if (winner == "Empate!") winner else "O \"$winner\" ganhou!"
        builder.setTitle("Parabéns!")
            .setMessage(message)
            .setOnCancelListener {
                reset.isEnabled = true
                for (row in buttons) {
                    for (node in row) {
                        node!!.isClickable = false
                    }
                }
            }
            .setPositiveButton("Novo jogo") { _, _ -> resetGame(buttons) }
        val dialog = builder.create()
        dialog.show()
    }

    fun resetGame(buttons: Array<Array<Button?>>) {
        resetButtons(buttons)
        resetMatrix()
        playCount = 0
    }

    private fun resetButtons(buttons: Array<Array<Button?>>) {
        for (row in buttons) {
            for (node in row) {
                node!!.text = ""
                node.isClickable = true
            }
        }
    }

    private fun resetMatrix() {
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                matrix[i][j] = null
            }
        }
    }

    private fun checkRows(
        context: Context,
        buttons: Array<Array<Button?>>,
        reset: Button
    ): Boolean {
        for (row in matrix) {
            if (row.contentEquals(arrayX)) {
                showWinner("X", context, buttons, reset)
                return true
            }
            if (row.contentEquals(arrayO)) {
                showWinner("O", context, buttons, reset)
                return true
            }
        }
        return false
    }

    private fun checkColumns(
        context: Context,
        buttons: Array<Array<Button?>>,
        reset: Button
    ): Boolean {
        val columns =
            Array(3) { arrayOfNulls<String>(3) }
        columns[0] = arrayOf(
            matrix[0][0],
            matrix[1][0],
            matrix[2][0]
        )
        columns[1] = arrayOf(
            matrix[0][1],
            matrix[1][1],
            matrix[2][1]
        )
        columns[2] = arrayOf(
            matrix[0][2],
            matrix[1][2],
            matrix[2][2]
        )
        for (column in columns) {
            if (column.contentEquals(arrayX)) {
                showWinner("X", context, buttons, reset)
                return true
            }
            if (column.contentEquals(arrayO)) {
                showWinner("O", context, buttons, reset)
                return true
            }
        }
        return false
    }

    private fun checkDiagonals(
        context: Context,
        buttons: Array<Array<Button?>>,
        reset: Button
    ): Boolean {
        val diagonals =
            Array(2) { arrayOfNulls<String>(3) }
        diagonals[0] = arrayOf(
            matrix[0][0],
            matrix[1][1],
            matrix[2][2]
        )
        diagonals[1] = arrayOf(
            matrix[0][2],
            matrix[1][1],
            matrix[2][0]
        )
        for (diagonal in diagonals) {
            if (diagonal.contentEquals(arrayX)) {
                showWinner("X", context, buttons, reset)
                return true
            }
            if (diagonal.contentEquals(arrayO)) {
                showWinner("O", context, buttons, reset)
                return true
            }
        }
        return false
    }

    init {
        playCount = 0
        turn = "X"
        arrayO = arrayOf("O", "O", "O")
        this.arrayX = arrayOf("X", "X", "X")
    }
}