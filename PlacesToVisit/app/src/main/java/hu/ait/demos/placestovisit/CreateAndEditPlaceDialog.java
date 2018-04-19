package hu.ait.demos.placestovisit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import hu.ait.demos.placestovisit.data.Place;

public class CreateAndEditPlaceDialog extends DialogFragment {

    public interface PlaceHandler {
        public void onNewPlaceCreated(Place place);

        public void onPlaceUpdated(Place place);
    }

    private PlaceHandler placeHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PlaceHandler) {
            placeHandler = (PlaceHandler)context;
        } else {
            throw new RuntimeException(
                    "The Activity does not implement the PlaceHandler interface");
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
