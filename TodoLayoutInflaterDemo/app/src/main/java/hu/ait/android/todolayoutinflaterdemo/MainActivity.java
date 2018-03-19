package hu.ait.android.todolayoutinflaterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etTodo)
    EditText etTodo;
    @BindView(R.id.cbImportant)
    CheckBox cbImportant;
    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddTodo)
    public void addTodoButtonClicked() {
        // inflate one todo row
        final View todoRow =
                getLayoutInflater().inflate(
                        R.layout.todo_row, null, false);

        // set the content of the todo row
        TextView tvTodoText = todoRow.findViewById(R.id.tvTodoText);
        tvTodoText.setText(etTodo.getText().toString());
        ImageView ivIcon = todoRow.findViewById(R.id.ivIcon);

        if (cbImportant.isChecked()) {
            ivIcon.setImageResource(R.drawable.highprio);
        } else {
            ivIcon.setImageResource(R.drawable.lowprio);
        }

        Button btnDel = todoRow.findViewById(R.id.btnDelTodo);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutContent.removeView(todoRow);
            }
        });

        // add todo row to the main layout
        layoutContent.addView(todoRow);
    }

    @OnClick(R.id.btnClearAll)
    public void clearAllClicked() {
        layoutContent.removeAllViews();
    }
}
