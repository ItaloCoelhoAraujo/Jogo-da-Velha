package br.italoaraujo.jogodavelha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;

import androidx.lifecycle.ViewModel;

import java.util.Arrays;

public class MainActivityViewModel extends ViewModel {
    private final String[] X;
    private final String[] O;

    private String turn;
    private int playCount;
    private String[][] matrix;

    public MainActivityViewModel() {
        matrix = new String[3][3];
        playCount = 0;
        turn = "X";
        O = new String[]{"O", "O", "O"};
        X = new String[]{"X", "X", "X"};
    }

    public String[][] getMatrix() {
        return matrix;
    }

    void play(Button button, int X, int Y) {
        matrix[X][Y] = turn;
        button.setText(matrix[X][Y]);

        playCount++;

        if(turn.equals("O")) {
            turn = "X";
            return;
        }
        if(turn.equals("X")) {
            turn = "O";
        }
    }

    void checkWinner(Context context, Button[][] buttons, Button reset) {
        if(checkRows(context, buttons, reset)) return;
        if(checkColumns(context, buttons, reset)) return;
        if(checkDiagonals(context, buttons, reset)) return;
        if(playCount == 9) showWinner("Empate!", context, buttons, reset);
    }

    private void showWinner(String winner, Context context, final Button[][] buttons, final Button reset) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message;
        if(winner.equals("Empate!"))
            message = winner;
        else
            message = "O \"" + winner + "\" ganhou!";
        builder.setTitle("Parabéns!")
                .setMessage(message)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        reset.setEnabled(true);
                        for(Button[] row : buttons) {
                            for(Button node : row) {
                                node.setClickable(false);
                            }
                        }
                    }
                })
                .setPositiveButton("Novo jogo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetGame(buttons);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void resetGame(Button[][] buttons) {
        resetButtons(buttons);
        resetMatrix();
        playCount = 0;
    }

    private void resetButtons(Button[][] buttons) {
        for(Button[] row : buttons) {
            for(Button node : row) {
                node.setText("");
                node.setClickable(true);
            }
        }
    }

    private void resetMatrix() {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = null;
            }
        }
    }

    private boolean checkRows(Context context, Button[][] buttons, Button reset) {
        for (String[] row : matrix) {
            if (Arrays.equals(row, X)) {
                showWinner("X", context, buttons, reset);
                return true;
            }
            if (Arrays.equals(row, O)) {
                showWinner("O", context, buttons, reset);
                return true;
            }
        }

        return false;
    }

    private boolean checkColumns(Context context, Button[][] buttons, Button reset) {
        String[][] columns = new String[3][3];
        columns[0] = new String[] {matrix[0][0], matrix[1][0], matrix[2][0]};
        columns[1] = new String[] {matrix[0][1], matrix[1][1], matrix[2][1]};
        columns[2] = new String[] {matrix[0][2], matrix[1][2], matrix[2][2]};

        for(String[] column : columns) {
            if(Arrays.equals(column, X)) {
                showWinner("X", context, buttons, reset);
                return true;
            }
            if(Arrays.equals(column, O)) {
                showWinner("O", context, buttons, reset);
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonals(Context context, Button[][] buttons, Button reset) {
        String[][] diagonals = new String[2][3];
        diagonals[0] = new String[]{matrix[0][0], matrix[1][1], matrix[2][2]};
        diagonals[1] = new String[]{matrix[0][2], matrix[1][1], matrix[2][0]};

        for (String[] diagonal : diagonals) {
            if(Arrays.equals(diagonal, X)) {
                showWinner("X", context, buttons, reset);
                return true;
            }
            if(Arrays.equals(diagonal, O)) {
                showWinner("O", context, buttons, reset);
                return true;
            }
        }

        return false;
    }
}


