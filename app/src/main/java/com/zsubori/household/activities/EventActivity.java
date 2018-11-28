package com.zsubori.household.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zsubori.household.R;
import com.zsubori.household.adapter.EventAdapter;
import com.zsubori.household.adapter.TodoAdapter;
import com.zsubori.household.data.Event;
import com.zsubori.household.data.Todo;
import com.zsubori.household.fragment.EventMessageFragment;
import com.zsubori.household.fragment.TodoMessageFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity implements EventMessageFragment.EventHandler {

    @BindView(R.id.event_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.addEventDialog)
    Button addEventDialogBtn;

    private ArrayList<Event> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist_activity);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<Event>();

        Event e1 = new Event("Koncert", newDate("2018-10-27"), "Zongoraest a Müpában");
        Event e2 = new Event("Színház", newDate("2018-11-02"), "Szkéné");
        Event e3 = new Event("Kirándulás", newDate("2018-11-10"), "Mátrai túra biciklivel és gyerekekkel");

        myDataset.add(e1);
        myDataset.add(e2);
        myDataset.add(e3);

        mAdapter = new EventAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        addEventDialogBtn = (Button) findViewById(R.id.addEventDialog);
        addEventDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(getString(R.string.dialog_message));
            }
        });
    }

    @Override
    public void onEventCreated(Event event) {
        myDataset.add(event);
        mAdapter.notifyDataSetChanged();
    }

    public Date newDate(String s) {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    protected void showMessage(String msg) {
        EventMessageFragment dialog = new EventMessageFragment();

        Bundle bundle = new Bundle();
        bundle.putString(EventMessageFragment.KEY_MSG, msg);
        dialog.setArguments(bundle);

        dialog.setCancelable(false);

        dialog.show(getFragmentManager(), EventMessageFragment.TAG);
    }
}
