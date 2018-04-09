package hu.ait.android.todorecylerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.todorecylerview.R;
import hu.ait.android.todorecylerview.data.Todo;

public class TodoRecylerAdapter
        extends RecyclerView.Adapter<TodoRecylerAdapter.ViewHolder> {

    private List<Todo> todoList;

    public TodoRecylerAdapter() {
        todoList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            todoList.add(new Todo(
                    "Todo"+i,
                    "05. 04. 2018",
                    false
            ));
        }
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
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.tvDate.setText(
                todoList.get(
                        holder.getAdapterPosition()).getCreateDate());
        holder.cbDone.setText(
                todoList.get(holder.getAdapterPosition()).getTodoTitle()
        );
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private CheckBox cbDone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }

}
