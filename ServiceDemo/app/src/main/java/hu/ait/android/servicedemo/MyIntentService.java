package hu.ait.android.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class MyIntentService extends IntentService {

    public static final String KEY_MSG = "KEY_MSG";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String message = intent.getStringExtra(KEY_MSG);

        // e.g. message is an url where we have to download

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyIntentService.this,
                        message+" DONE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
