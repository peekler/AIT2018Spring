package hu.ait.android.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    private Button btnStart;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = findViewById(R.id.layoutContent);
        btnStart = findViewById(R.id.btnStart);
        tvMessage = findViewById(R.id.tvMessage);

        final Animation anim = AnimationUtils.loadAnimation(
                MainActivity.this, R.anim.button_anim
        );
        final Animation sendAnim = AnimationUtils.loadAnimation(
                MainActivity.this, R.anim.send_anim
        );
        sendAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(MainActivity.this,
                        "Message sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnStart.startAnimation(anim);
                //layoutContent.startAnimation(anim);
                tvMessage.startAnimation(sendAnim);
            }
        });

    }
}
