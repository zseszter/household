package com.zsubori.household.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zsubori.household.R;
import com.zsubori.household.data.Event;

import java.util.Calendar;
import java.util.Date;

public class EventMessageFragment extends DialogFragment {

    public interface EventHandler {
        public void onEventCreated(Event event);
    }

    public static final String TAG = "DialogFragmentMessage";
    public static final String KEY_MSG = "KEY_MSG";

    private EventHandler eventHandler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.add_new_event);
        alertDialogBuilder.setMessage(message);

        View v = getActivity().getLayoutInflater().inflate(R.layout.new_event_dialog_layout, null);

        alertDialogBuilder.setView(v);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText eventName = (EditText) ((AlertDialog) dialog).findViewById(R.id.eventName);
                EditText eventComment = (EditText) ((AlertDialog) dialog).findViewById(R.id.eventComment);
                TextView place_tv = (TextView) ((AlertDialog) dialog).findViewById(R.id.eventPlaceChooser);
                DatePicker datePicker = (DatePicker) ((AlertDialog) dialog).findViewById(R.id.eventDatePicker);

                Date eventDate = getDateFromDatePicker(datePicker);

                Event myEvent = new Event(eventName.getText().toString(), eventDate, eventComment.getText().toString());

                eventHandler.onEventCreated(myEvent);
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public void addHandler(EventHandler th) {
        eventHandler = th;
    }

    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
