package hu.ait.android.aittime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        Button btnPressMe = findViewById(R.id.btnPressMe);
        final TextView tvTime = findViewById(R.id.tvTime);

        btnPressMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new Date(System.currentTimeMillis()).toString();
                Toast.makeText(MainActivity.this,
                        date,
                        Toast.LENGTH_SHORT).show();
                tvTime.setText(date);
            }
        });
    }
}
