package hu.ait.android.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartIntentService = findViewById(R.id.btnStartIntentService);
        btnStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(MainActivity.this,
                        MyIntentService.class);
                intentStart.putExtra(MyIntentService.KEY_MSG, "http://eztcsinald.hu");
                startService(
                     intentStart
                );
            }
        });

        final Intent intentTimeService = new Intent(
                MainActivity.this, TimeService.class
        );

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intentTimeService);
            }
        });

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intentTimeService);
            }
        });


        final EditText etInterval = findViewById(R.id.etInterval);
        Button btnInterval = findViewById(R.id.btnInterval);
        btnInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeService != null) {
                    timeService.changeInterval(Long.valueOf(etInterval.getText().toString()));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        bindService(
                new Intent(MainActivity.this, TimeService.class),
                serviceConnection,
                BIND_AUTO_CREATE
        );
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(serviceConnection);
    }

    private TimeService timeService = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timeService = ((TimeService.TimeServiceBinder)service).getService();
            btnStart.setEnabled(true);
            btnStop.setEnabled(true);

            Toast.makeText(timeService, "Bind ready", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
