package hu.ait.draganddropdemo;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder =
                        new View.DragShadowBuilder(
                                view);
                view.startDrag(data, shadowBuilder, view, 0);
                draggedView = view;

                return true;
            }
            return false;
        }
    }

    private View draggedView = null;
    private TextView colorChooser;
    private MyTouchListener myTouchListener = new MyTouchListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View color1 = findViewById(R.id.color1);
        View color2 = findViewById(R.id.color2);
        View color3 = findViewById(R.id.color3);
        View color4 = findViewById(R.id.color4);

        color1.setOnTouchListener(myTouchListener);
        color2.setOnTouchListener(myTouchListener);
        color3.setOnTouchListener(myTouchListener);
        color4.setOnTouchListener(myTouchListener);

        colorChooser = (TextView) findViewById(R.id.colorChooserListener);
        colorChooser.setOnDragListener(new MyDragListener());
    }

    private final class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Toast.makeText(MainActivity.this, "Entered", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Toast.makeText(MainActivity.this, "Exited", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case DragEvent.ACTION_DROP:
                    Toast.makeText(MainActivity.this, "Drop", Toast.LENGTH_SHORT)
                            .show();
                    if (draggedView != null) {
                        colorChooser.setBackground(
                                draggedView.getBackground());
                    }
                    break;
            }
            return true;
        }
    }
}
