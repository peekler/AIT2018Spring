package hu.ait.android.autocompletedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    private final String[] cityNames = new String[]{"Budapest", "Bukarest",
            "BuCity",
            "Krakkó", "Bécs"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView tv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewCities);
        ArrayAdapter<String> cityAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.
                                simple_dropdown_item_1line, cityNames);
        tv.setAdapter(cityAdapter);
    }
}