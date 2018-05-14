package hu.ait.android.dynamicfragmentdemo;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewParent;

public class SlideShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        ViewPager pager = findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter =
                new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }
}
