package hu.ait.android.todorecylerview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import hu.ait.android.todorecylerview.data.Todo;

public class TodoCreateAndEditDialog extends DialogFragment {

    public interface TodoHandler {
        public void onNewTodoCreated(String todo);

        public void onTodoUpdated(Todo todo);
    }

    private TodoHandler todoHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof TodoHandler) {
            todoHandler = (TodoHandler)context;
        } else {
            throw new RuntimeException(
                    "The Activity does not implement the TodoHandler interface");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New Todo");
        // Set up the input
        final EditText input = new EditText(getActivity());

        if (getArguments() != null &&
                getArguments().containsKey(MainActivity.KEY_TODO_TO_EDIT)) {

            input.setText(
                    ((Todo)getArguments().getSerializable(
                            MainActivity.KEY_TODO_TO_EDIT)).getTodoTitle());
        }

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("Save todo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getArguments()!=null &&
                        getArguments().containsKey(MainActivity.KEY_TODO_TO_EDIT))
                {
                    Todo todoToEdit = (Todo) getArguments().getSerializable(
                            MainActivity.KEY_TODO_TO_EDIT);
                    todoToEdit.setTodoTitle(input.getText().toString());
                    todoHandler.onTodoUpdated(todoToEdit);
                } else {
                    todoHandler.onNewTodoCreated(input.getText().toString());
                }
            }
        });

        return builder.create();
    }


}
