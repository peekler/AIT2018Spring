package hu.ait.android.aittime.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import hu.ait.android.aittime.R;

public class MainActivity extends AppCompatActivity {


    public static final String TAG_UI = "TAG_UI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        Button btnPress = findViewById(R.id.btnPress);
        TextView tvData = findViewById(R.id.tvData);

        final EditText etName = findViewById(R.id.etName);

        setupTouchEvents(btnPress, tvData, etName);
    }

    private void setupTouchEvents(Button btnPress, final TextView tvData, final EditText etName) {
        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateTime =
                        etName.getText().toString() +
                        getString(R.string.date_head,
                          new Date(System.currentTimeMillis()).toString());

                Log.d(TAG_UI, "the date value: "+dateTime);

                /*Toast.makeText(MainActivity.this,
                        dateTime,
                        Toast.LENGTH_SHORT).show();*/
                Snackbar.make(findViewById(R.id.layoutRoot),
                        dateTime,
                        Snackbar.LENGTH_LONG).show();

                tvData.setText(dateTime);

                Log.d(TAG_UI, "Everything is ok");
            }
        });
    }

}
