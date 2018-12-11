package com.zsubori.household.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zsubori.household.R;
import com.zsubori.household.data.Todo;

public class TodoMessageFragment extends DialogFragment {

    private TodoHandler todoHandler;

    public interface TodoHandler {
        public void onTodoCreated(Todo todo);
    }

    public static final String TAG = "DialogFragmentMessage";
    public static final String KEY_MSG = "KEY_MSG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(KEY_MSG);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.add_new_todo);
        alertDialogBuilder.setMessage(message);

        View v = getActivity().getLayoutInflater().inflate(R.layout.new_todo_dialog_layout, null);

        alertDialogBuilder.setView(v);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText todoName = (EditText) ((AlertDialog) dialog).findViewById(R.id.dialogTodoName);
                EditText todoAssignee = (EditText) ((AlertDialog) dialog).findViewById(R.id.dialogTodoAssignee);

                Todo myTodo = new Todo(todoName.getText().toString(), todoAssignee.getText().toString());

                todoHandler.onTodoCreated(myTodo);
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return alertDialogBuilder.create();
    }

    public void addHandler(TodoHandler th) {
        todoHandler = th;
    }
}
