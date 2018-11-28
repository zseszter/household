package com.zsubori.household.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zsubori.household.R;
import com.zsubori.household.adapter.EventAdapter;
import com.zsubori.household.data.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventFragment extends Fragment implements EventMessageFragment.EventHandler {

    @BindView(R.id.event_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.addEventDialog)
    Button addEventDialogBtn;

    private ArrayList<Event> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.eventlist_activity, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
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

        addEventDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(getString(R.string.dialog_message));
            }
        });

        return view;
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

        dialog.addHandler(this);

        dialog.show(getChildFragmentManager(), EventMessageFragment.TAG);
    }

}
