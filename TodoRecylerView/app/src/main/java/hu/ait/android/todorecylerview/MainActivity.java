package hu.ait.android.todorecylerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.ait.android.todorecylerview.adapter.TodoRecylerAdapter;
import hu.ait.android.todorecylerview.data.Todo;

public class MainActivity extends AppCompatActivity implements NewTodoDialog.TodoHandler {

    private TodoRecylerAdapter todoRecylerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTodoDialog();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerTodo);
        todoRecylerAdapter = new TodoRecylerAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerView.setAdapter(todoRecylerAdapter);
    }

    private void showNewTodoDialog() {
       new NewTodoDialog().show(getSupportFragmentManager(), "NewTodoDialog");
    }

    @Override
    public void onNewTodoCreated(String todo) {
        todoRecylerAdapter.addTodo(
                new Todo(todo,
                        new SimpleDateFormat(
                                "yyyy-MM-dd hh:mm:ss").format(
                                        new Date(System.currentTimeMillis())),
                        false)
        );
    }
}
