package hu.ait.android.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ActivityB extends AppCompatActivity {

    public static final String KEY_CONTENT = "KEY_CONTENT";
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.d("B","onCreate");

        content = (EditText) findViewById(R.id.content);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentString=content.getText().toString();
                Intent resultIntent=new Intent();
                resultIntent.putExtra(KEY_CONTENT,contentString);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
    }

    /*@Override
    public void onBackPressed() {
        Toast.makeText(this, "You never leave this Activity",
                Toast.LENGTH_SHORT).show();
    }*/

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
