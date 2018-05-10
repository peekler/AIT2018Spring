package hu.ait.android.intentdemo;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDemo = findViewById(R.id.btnDemo);
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    /*Intent intent = new Intent(Intent.ACTION_CALL,
                            Uri.parse("tel:911"));
                    startActivity(intent);*/

                   /* Intent intentTest = new Intent(Intent.ACTION_WEB_SEARCH);
                    intentTest.putExtra(SearchManager.QUERY, "AIT Budapest");
                    startActivity(intentTest);*/


                    Intent intentTest = new Intent(Intent.ACTION_SEND);
                    intentTest.setType("text/plain");
                    intentTest.putExtra(Intent.EXTRA_TEXT, "Hello from AIT");
                    intentTest.setPackage("com.facebook.katana");
                    startActivity(Intent.createChooser(intentTest, "Select share app"));


                    /*String wazeUri = "waze://?favorite=Home&navigate=yes";
                    String wazeUri2 = "waze://?ll=47.474097, 19.049473&navigate=yes";
                    String wazeuri3 = "waze://?q=MOL&navigate=yes";

                    Intent intentTest = new Intent(Intent.ACTION_VIEW);
                    intentTest.setData(Uri.parse(wazeuri3));*/

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        requestNeededPermission();
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(MainActivity.this,
                        "I need it for call", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    101);
        } else {
            // már van engedély
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "CALL perm granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "CALL perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
