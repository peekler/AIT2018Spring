package hu.ait.android.viewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvData)
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ImageView ivIcon = findViewById(R.id.ivIcon);
        ivIcon.setImageResource(R.mipmap.ic_launcher);
    }

    @OnClick(R.id.btnShow)
    public void showButtonClicked() {
        String text = new Date(System.currentTimeMillis()).toString();

        tvData.setText(text);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
