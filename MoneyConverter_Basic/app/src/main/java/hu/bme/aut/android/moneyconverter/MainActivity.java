package hu.bme.aut.android.moneyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import hu.bme.aut.android.moneyconverter.data.MoneyResult;


public class MainActivity extends AppCompatActivity {

    private final String URL_BASE =
            "http://api.fixer.io/latest?base=USD";

    private Button btnGetRates;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        btnGetRates = findViewById(R.id.btnGetRates);

        btnGetRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = URL_BASE;
                new HttpGetTask(getApplicationContext()).
                        execute(query);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                brWeatherReceiver,
                new IntentFilter(HttpGetTask.FILTER_RESULT)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(
                this).unregisterReceiver(brWeatherReceiver);
    }

    private BroadcastReceiver brWeatherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String rawResult = intent.getStringExtra(
                    HttpGetTask.KEY_RESULT);

            try {
                /*JSONObject jsonObject = new JSONObject(rawResult);

                String huf = jsonObject.getJSONObject(
                        "rates").getString("HUF");
                String eur = jsonObject.getJSONObject(
                        "rates").getString("EUR");*/

                Gson gson = new Gson();
                MoneyResult moneyResult = gson.fromJson(rawResult, MoneyResult.class);

                tvResult.setText(""+moneyResult.getRates().gethUF()+
                                "\n"+
                                moneyResult.getRates().geteUR()
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
