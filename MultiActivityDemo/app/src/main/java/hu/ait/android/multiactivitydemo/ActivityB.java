package hu.ait.android.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


public class ActivityB extends AppCompatActivity {

    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.d("B","onCreate");

        content = (EditText) findViewById(R.id.content);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("B","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("B", "onResume");
    }


    @Override
    protected void onPause() {
        String contentString=content.getText().toString();
        Intent resultIntent=new Intent();
        resultIntent.putExtra("content",contentString);
        setResult(101,resultIntent);
        finish();

        Log.d("B","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("B","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("B","onDestroy");
        super.onDestroy();
    }
}
