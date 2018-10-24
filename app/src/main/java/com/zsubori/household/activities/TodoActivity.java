package com.zsubori.household.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zsubori.household.R;
import com.zsubori.household.adapter.TodoAdapter;
import com.zsubori.household.data.Todo;
import com.zsubori.household.fragment.TodoMessageFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoActivity extends AppCompatActivity implements TodoMessageFragment.TodoHandler {

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.addDialog) Button addDialogBtn;

    private ArrayList<Todo> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
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

        addDialogBtn = (Button) findViewById(R.id.addDialog);
        addDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(getString(R.string.dialog_message));
            }
        });
    }

    protected void showMessage(String msg) {
        TodoMessageFragment dialog = new TodoMessageFragment();

        Bundle bundle = new Bundle();
        bundle.putString(TodoMessageFragment.KEY_MSG, msg);
        dialog.setArguments(bundle);

        dialog.setCancelable(false);

        //dialog.show(getSupportFragmentManager(), TodoMessageFragment.TAG);
        dialog.show(getFragmentManager(), TodoMessageFragment.TAG);
    }

    @Override
    public void onTodoCreated(Todo todo) {
        myDataset.add(todo);
        mAdapter.notifyDataSetChanged();
    }
}


