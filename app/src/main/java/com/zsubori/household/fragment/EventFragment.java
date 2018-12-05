package com.zsubori.household.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zsubori.household.R;
import com.zsubori.household.adapter.EventAdapter;
import com.zsubori.household.data.Event;
import com.zsubori.household.data.Todo;

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
    FloatingActionButton addEventDialogBtn;

    private ArrayList<Event> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FirebaseFirestore db;
    private String TAG = "database";

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

        db = FirebaseFirestore.getInstance();

        myDataset = new ArrayList<Event>();
        loadEvents();

/*        Event e1 = new Event("Koncert", newDate("2018-10-27"), "Zongoraest a Müpában");
        Event e2 = new Event("Színház", newDate("2018-11-02"), "Szkéné");
        Event e3 = new Event("Kirándulás", newDate("2018-11-10"), "Mátrai túra biciklivel és gyerekekkel");*/


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
        db.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

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

    public void loadEvents() {
        db.collection("events")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
                                Event myEvent = new Event();

                                myEvent.setEventName(documentSnapshot.getString("eventName"));
                                myEvent.setEventComment(documentSnapshot.getString("eventComment"));
                                myEvent.setEventDate(documentSnapshot.getDate("eventDate"));

                                myDataset.add(myEvent);
                            }

                            Log.d(TAG, "onSuccess: " + myDataset);
                        }
                    }
                });
    }

}
