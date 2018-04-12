package hu.ait.android.todorecylerview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hu.ait.android.todorecylerview.adapter.TodoRecylerAdapter;
import hu.ait.android.todorecylerview.data.AppDatabase;
import hu.ait.android.todorecylerview.data.Todo;
import hu.ait.android.todorecylerview.touch.TodoItemTouchHelperCallback;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity implements TodoCreateAndEditDialog.TodoHandler {

    public static final String KEY_TODO_TO_EDIT = "KEY_TODO_TO_EDIT";
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

        if (isFirstRun()) {
            new MaterialTapTargetPrompt.Builder(MainActivity.this)
                    .setTarget(findViewById(R.id.fab))
                    .setPrimaryText("New Todo")
                    .setSecondaryText("Tap here to create new todo")
                    .show();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerTodo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        initTodos(recyclerView);

        saveThatItWasStarted();
    }

    public boolean isFirstRun() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                "KEY_FIRST", true
        );
    }

    public void saveThatItWasStarted() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("KEY_FIRST", false);
        editor.commit();
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
       new TodoCreateAndEditDialog().show(getSupportFragmentManager(), "TodoCreateAndEditDialog");
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
                long id = AppDatabase.getAppDatabase(MainActivity.this).
                        todoDao().insertTodo(newTodo);
                newTodo.setTodoId(id);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todoRecylerAdapter.addTodo(newTodo);
                    }
                });
            }
        }.start();
    }

    @Override
    public void onTodoUpdated(final Todo todo) {
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(MainActivity.this).todoDao().update(todo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todoRecylerAdapter.updateTodo(todo);
                    }
                });
            }
        }.start();
    }

    public void editTodo(Todo todo) {
        TodoCreateAndEditDialog editDialog = new TodoCreateAndEditDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TODO_TO_EDIT, todo);
        editDialog.setArguments(bundle);


        editDialog.show(getSupportFragmentManager(), "EditDialog");
    }


}
