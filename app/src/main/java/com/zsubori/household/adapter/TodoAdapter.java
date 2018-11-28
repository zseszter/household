package com.zsubori.household.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsubori.household.R;
import com.zsubori.household.data.Todo;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    private ArrayList<Todo> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLinearLayout;
        public TextView mTextView;
        public TextView mAssignee;
        public MyViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
            mTextView = mLinearLayout.findViewById(R.id.todo_name);
            mAssignee = mLinearLayout.findViewById(R.id.todo_assignee);
        }
    }

    public TodoAdapter(ArrayList<Todo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_todo, parent, false);

        TextView todo_name = (TextView) v.findViewById(R.id.todo_name);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).getName());
        holder.mAssignee.setText(mDataset.get(position).getAssignee());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
