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

    public interface TodoHandler {
        public void onTodoCreated(Todo todo);
    }

    public static final String TAG = "DialogFragmentMessage";
    public static final String KEY_MSG = "KEY_MSG";

    private TodoHandler todoHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof TodoHandler) {
            todoHandler = (TodoHandler)context;
        } else {
            throw new RuntimeException(
                    getString(R.string.error_wrong_interface));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.add_new_todo);
        alertDialogBuilder.setMessage(message);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, null);

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

        return alertDialogBuilder.create();
    }
}
