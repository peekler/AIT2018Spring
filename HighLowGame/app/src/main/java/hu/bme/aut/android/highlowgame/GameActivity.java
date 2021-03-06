package hu.bme.aut.android.highlowgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.security.interfaces.RSAKey;
import java.util.Random;

import hu.bme.aut.android.highlowgame.data.DataManager;

public class GameActivity extends AppCompatActivity {

    public static final String KEY_GEN = "KEY_GEN";
    public static final String KEY_COUNT = "KEY_COUNT";
    private int generated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final EditText etGuess = findViewById(R.id.etGuess);
        final TextView tvStatus = findViewById(R.id.tvStatus);
        Button btnGuess = findViewById(R.id.btnGuess);

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!TextUtils.isEmpty(etGuess.getText())) {
                        int number = Integer.parseInt(etGuess.getText().toString());

                        if (number < generated) {
                            tvStatus.setText("Your number is too small");
                        } else if (number > generated) {
                            tvStatus.setText("Your number is too high");
                        } else {
                            tvStatus.setText("YOU HAVE WON!");

                            DataManager.getInstance().setCoutner(3);

                            Intent intent = new Intent(GameActivity.this,
                                    ResultActivity.class);
                            intent.putExtra(KEY_COUNT, 2);
                            startActivity(intent);

                            //  finish();
                        }
                    } else {
                        etGuess.setError("This field can not be empty");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (savedInstanceState != null &&
                savedInstanceState.containsKey(KEY_GEN)) {
            generated = savedInstanceState.getInt(KEY_GEN);
        } else {
            generateNewNumber();
        }

        ShimmerFrameLayout shimmer = findViewById(R.id.shimmer);
        shimmer.startShimmerAnimation();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_GEN, generated);
    }

    private void generateNewNumber() {
        Random rand = new Random(System.currentTimeMillis());
        generated = rand.nextInt(3); // 0..99
    }

}
