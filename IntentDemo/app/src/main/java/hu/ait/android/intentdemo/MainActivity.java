package hu.ait.android.intentdemo;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIntent = findViewById(R.id.btnIntent);
        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intentTest = new Intent(Intent.ACTION_WEB_SEARCH);
                intentTest.putExtra(SearchManager.QUERY, "AIT Budapest");
                startActivity(intentTest);*/


                /*Intent intentTest = new Intent(Intent.ACTION_SEND);
                intentTest.setType("text/plain");
                intentTest.putExtra(Intent.EXTRA_TEXT, "Hello from AIT");
                intentTest.setPackage("com.facebook.katana");
                startActivity(Intent.createChooser(intentTest, "Select share app"));*/

                String wazeUri = "waze://?favorite=Home&navigate=yes";

                Intent intentTest = new Intent(Intent.ACTION_VIEW);
                intentTest.setData(Uri.parse(wazeUri));
                startActivity(intentTest);

            }
        });
    }
}
