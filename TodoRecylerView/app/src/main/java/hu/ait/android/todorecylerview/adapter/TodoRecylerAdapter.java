package hu.ait.android.todorecylerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.todorecylerview.MainActivity;
import hu.ait.android.todorecylerview.R;
import hu.ait.android.todorecylerview.data.AppDatabase;
import hu.ait.android.todorecylerview.data.Todo;
import hu.ait.android.todorecylerview.touch.TodoTouchHelperAdapter;

public class TodoRecylerAdapter
        extends RecyclerView.Adapter<TodoRecylerAdapter.ViewHolder>
        implements TodoTouchHelperAdapter {

    private List<Todo> todoList;

    private Context context;

    public TodoRecylerAdapter(List<Todo> todos, Context context) {
        todoList = todos;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View viewRow = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(viewRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,
                                 int position) {
        holder.tvDate.setText(
                todoList.get(
                        holder.getAdapterPosition()).getCreateDate());
        holder.cbDone.setText(
                todoList.get(holder.getAdapterPosition()).getTodoTitle()
        );
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).editTodo(
                        todoList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(final int position) {
        final Todo todoToDelete = todoList.get(position);
        todoList.remove(todoToDelete);
        notifyItemRemoved(position);
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(context).todoDao().delete(
                        todoToDelete);
            }
        }.start();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todoList, i, i - 1);
            }
        }

        //notifyDataSetChanged();
        notifyItemMoved(fromPosition, toPosition);
    }

    public void updateTodo(Todo todo) {
        int editPos = findTodoIndexByTodoId(todo.getTodoId());
        todoList.set(editPos,todo);
        notifyItemChanged(editPos);
    }

    private int findTodoIndexByTodoId(long todoId) {
        for (int i = 0; i < todoList.size(); i++) {
            if (todoList.get(i).getTodoId() == todoId) {
                return i;
            }
        }

        return -1;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private CheckBox cbDone;
        private Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            cbDone = itemView.findViewById(R.id.cbDone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

}
