package hu.ait.android.sharedpreferencesdemo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_LAST_START_TIME = "KEY_LAST_START_TIME";
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = findViewById(R.id.tvData);

        showLastStartTime();
    }

    private void showLastStartTime() {
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(this);
        String lastStartTime = sp.getString(KEY_LAST_START_TIME,
                "This is the first time");
        tvData.setText(lastStartTime);
    }

    @Override
    protected void onStart() {
        super.onStart();
        saveLastStartTime();
    }

    private void saveLastStartTime() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_LAST_START_TIME, new Date(System.currentTimeMillis()).toString());
        edit.commit();
    }


}
