package hu.ait.android.musicplayerdemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements MediaPlayer.OnPreparedListener {

    private MediaPlayer myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myPlayer = MediaPlayer.create(MainActivity.this,
                            R.raw.music);
                    myPlayer.setOnPreparedListener(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        myPlayer.start();
    }

    @Override
    protected void onStop() {
        myPlayer.stop();
        super.onStop();
    }
}
