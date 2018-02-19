package hu.bme.aut.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hu.bme.aut.android.tictactoe.model.TicTacToeModel;
import hu.bme.aut.android.tictactoe.ui.TicTacToeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnClear = findViewById(R.id.btnClear);
        final TicTacToeView ticTacToeView = findViewById(R.id.ticTacToe);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTacToeView.clearBoard();
            }
        });
    }


}
