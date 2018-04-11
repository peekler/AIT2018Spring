package hu.ait.android.todorecylerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hu.ait.android.todorecylerview.adapter.TodoRecylerAdapter;
import hu.ait.android.todorecylerview.data.AppDatabase;
import hu.ait.android.todorecylerview.data.Todo;
import hu.ait.android.todorecylerview.touch.TodoItemTouchHelperCallback;

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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        initTodos(recyclerView);
    }

    public void initTodos(final RecyclerView recyclerView) {
        new Thread(){
            @Override
            public void run() {
                final List<Todo> todos =
                        AppDatabase.getAppDatabase(MainActivity.this).todoDao().getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todoRecylerAdapter = new TodoRecylerAdapter(todos, MainActivity.this);
                        recyclerView.setAdapter(todoRecylerAdapter);

                        ItemTouchHelper.Callback callback =
                                new TodoItemTouchHelperCallback(todoRecylerAdapter);
                        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                        touchHelper.attachToRecyclerView(recyclerView);
                    }
                });
            }
        }.start();
    }

    private void showNewTodoDialog() {
       new NewTodoDialog().show(getSupportFragmentManager(), "NewTodoDialog");
    }

    @Override
    public void onNewTodoCreated(final String todo) {
        new Thread(){
            @Override
            public void run() {
                final Todo newTodo = new Todo(todo,
                        new SimpleDateFormat(
                                "yyyy-MM-dd hh:mm:ss").format(
                                new Date(System.currentTimeMillis())),
                        false);
                AppDatabase.getAppDatabase(MainActivity.this).todoDao().insertTodo(newTodo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todoRecylerAdapter.addTodo(newTodo);
                    }
                });
            }
        }.start();
    }
}
