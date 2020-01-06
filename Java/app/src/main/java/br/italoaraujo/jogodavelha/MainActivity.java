package br.italoaraujo.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    private Button[][] buttons = new Button[3][3];
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        resetButton = findViewById(R.id.resetButton);

        initButtons();
        setOnClickListeners();
        refillButtons();
    }

    //Whenever the Activity is recreated this will make sure the buttons will retain
    //their text value and be not clickable as they should be
    private void refillButtons() {
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[0].length; j++) {
                if(viewModel.getMatrix()[i][j] != null) {
                    buttons[i][j].setText(viewModel.getMatrix()[i][j]);
                    buttons[i][j].setClickable(false);
                }
            }
        }
    }

    private void initButtons() {
        buttons[0][0] = findViewById(R.id.button);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);
        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);
        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);
    }

    private void setOnClickListeners() {
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[0].length; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.play(buttons[finalI][finalJ], finalI, finalJ);
                        viewModel.checkWinner(MainActivity.this, buttons, resetButton);
                        buttons[finalI][finalJ].setClickable(false);
                    }
                });
            }
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.resetGame(buttons);
                resetButton.setEnabled(false);
            }
        });
    }
}
