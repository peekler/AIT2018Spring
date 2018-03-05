package hu.ait.android.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.ait.android.multiactivitydemo.data.DataManager;

public class ActivityA extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.d("A", "onCreate");

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityA.this,
                        ActivityB.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getStringExtra(ActivityB.KEY_CONTENT);
            Toast.makeText(ActivityA.this, result, Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("A", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("A", "onResume");
    }


    @Override
    protected void onPause() {
        Log.d("A", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("A", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("A", "onDestroy");
        super.onDestroy();
    }
}
