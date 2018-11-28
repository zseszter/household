package com.zsubori.household.activities;

import android.app.Fragment;
import android.os.Bundle;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zsubori.household.R;
import com.zsubori.household.adapter.TodoAdapter;
import com.zsubori.household.data.Todo;
import com.zsubori.household.fragment.TodoMessageFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoFragment extends Fragment implements TodoMessageFragment.TodoHandler {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.addDialog)
    Button addDialogBtn;

    private ArrayList<Todo> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        myDataset = new ArrayList<Todo>();

        Todo t1 = new Todo("Funyiras", "Barnabas");
        Todo t2 = new Todo("Mosogatas", "Eszter");
        Todo t3 = new Todo("Fozes", "Anya");
        Todo t4 = new Todo("Padlas", "Apa");

        myDataset.add(t1);
        myDataset.add(t2);
        myDataset.add(t3);
        myDataset.add(t4);

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

        //dialog.show(getSupportFragmentManager(), TodoMessageFragment.TAG);
//        dialog.show(getFragmentManager(), TodoMessageFragment.TAG);

        dialog.show(getChildFragmentManager(), TodoMessageFragment.TAG);
    }

    @Override
    public void onTodoCreated(Todo todo) {
        myDataset.add(todo);
        mAdapter.notifyDataSetChanged();
    }

}
