package com.zsubori.household.activities;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zsubori.household.R;
import com.zsubori.household.adapter.TodoAdapter;
import com.zsubori.household.data.Todo;
import com.zsubori.household.fragment.TodoMessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoFragment extends Fragment implements TodoMessageFragment.TodoHandler {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.addDialog)
    FloatingActionButton addDialogBtn;

    private ArrayList<Todo> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FirebaseFirestore db;
    private String TAG = "database";

    public TodoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.bind(this,view);

        //RecyclerView mRecyclerView = container.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        db = FirebaseFirestore.getInstance();

        myDataset = new ArrayList<Todo>();
        loadTodos();

        mAdapter = new TodoAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        addDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(getString(R.string.dialog_message));
            }
        });

        return view;
    }

    protected void showMessage(String msg) {
        TodoMessageFragment dialog = new TodoMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TodoMessageFragment.KEY_MSG, msg);
        dialog.setArguments(bundle);
        dialog.addHandler(this);
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), TodoMessageFragment.TAG);
    }

    @Override
    public void onTodoCreated(Todo todo) {
        db.collection("todos")
                .add(todo)
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

        myDataset.add(todo);
        mAdapter.notifyDataSetChanged();
    }

    public void loadTodos() {
        db.collection("todos")
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
                                Todo myTodo = new Todo();

                                myTodo.setName(documentSnapshot.getString("name"));
                                myTodo.setAssignee(documentSnapshot.getString("assignee"));

                                myDataset.add(myTodo);
                            }

                            Log.d(TAG, "onSuccess: " + myDataset);
                        }
                    }
                });
    }
}
