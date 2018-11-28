package com.zsubori.household.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsubori.household.R;
import com.zsubori.household.data.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private ArrayList<Event> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLinearLayout;
        public TextView mEventName;
        public TextView mEventDate;
        public TextView mEventComment;
        public MyViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
            mEventName = mLinearLayout.findViewById(R.id.event_name);
            mEventDate = mLinearLayout.findViewById(R.id.event_date);
            mEventComment = mLinearLayout.findViewById(R.id.event_comment);
        }
    }

    public EventAdapter(ArrayList<Event> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_event, parent, false);

        EventAdapter.MyViewHolder vh = new EventAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(EventAdapter.MyViewHolder holder, int position) {
        //String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(mDataset.get(position).getEventDate().toString());

        holder.mEventName.setText(mDataset.get(position).getEventName());
        holder.mEventDate.setText(mDataset.get(position).getEventDate().toString());
        holder.mEventComment.setText(mDataset.get(position).getEventComment());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
