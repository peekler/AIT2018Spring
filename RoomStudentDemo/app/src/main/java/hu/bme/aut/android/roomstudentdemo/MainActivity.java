package hu.bme.aut.android.roomstudentdemo;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import hu.bme.aut.android.roomstudentdemo.data.AppDatabase;
import hu.bme.aut.android.roomstudentdemo.data.Grade;

public class MainActivity extends AppCompatActivity {

    private EditText etStudentId;
    private EditText etGrade;
    private Button btnSubmit;
    private Button btnSearch;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStudentId = findViewById(R.id.etStudentId);
        etGrade = findViewById(R.id.etGrade);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSearch = findViewById(R.id.btnSearch);
        tvResult = findViewById(R.id.tvResult);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-grade").build();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        Grade tmpGrade = new Grade(
                                etStudentId.getText().toString(),
                                etGrade.getText().toString()
                        );

                        db.gradeDao().insertAll(tmpGrade);
                    }
                }.start();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("");



                new Thread() {
                    public void run() {
                        List<Grade> grades = db.gradeDao().getAllGrades();
                        for (final Grade grade : grades) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.append(
                                            grade.getStudentId() + ", " +
                                                    grade.getGrade() + "\n");
                                }
                            });
                        }
                    }
                }.start();


            }
        });
    }
}
