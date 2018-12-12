package com.zsubori.household.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.zsubori.household.R;
import com.zsubori.household.data.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> mDataset;
    private ViewGroup mParent;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView postCard;
        public TextView tvAuthor;
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);
            postCard = (CardView) itemView.findViewById(R.id.postCard);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }
    }

    private int lastPosition = -1;

    public PostAdapter(ArrayList<Post> posts) {
        mDataset = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mParent = viewGroup;

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.element_post, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        Post tmpPost = mDataset.get(position);
        viewHolder.tvAuthor.setText(tmpPost.getAuthor());
        viewHolder.tvBody.setText(tmpPost.getBody());

/*        if(tmpPost.getAuthor().equals("zse")) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_END);
            viewHolder.postCard.setLayoutParams(params);
        }*/

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mParent.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}