package hu.bme.aut.android.highlowgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    public void onBackPressed() {
        Intent intentMain =
                new Intent(ResultActivity.this,
                        MainActivity.class);

        intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intentMain);
        finish();
    }
}
