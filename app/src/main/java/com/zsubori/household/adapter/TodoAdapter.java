package com.zsubori.household.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zsubori.household.R;
import com.zsubori.household.data.Todo;
import com.zsubori.household.fragment.TodoMessageFragment;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    private ArrayList<Todo> mDataset;
    private ViewGroup mParent;

    private TodoUpdateHandler todoUpdateHandler;
    private String TAG;

    public interface TodoUpdateHandler {
        void onTodoUpdated(String id, String assignee);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLinearLayout;
        public TextView mTextView;
        public TextView mAssignee;
        public Button mAsigneeBtn;
        public CardView mCardView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
            mTextView = mLinearLayout.findViewById(R.id.todo_name);
            mAssignee = mLinearLayout.findViewById(R.id.todo_assignee);
            mAsigneeBtn = mLinearLayout.findViewById(R.id.assignForTodo);
            mCardView = mLinearLayout.findViewById(R.id.card_view);
        }
    }

    public TodoAdapter(ArrayList<Todo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mParent = parent;
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_todo, parent, false);

        TextView todo_name = (TextView) v.findViewById(R.id.todo_name);

        //MyViewHolder vh = new MyViewHolder(v);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position).getName());
        holder.mAssignee.setText(mDataset.get(position).getAssignee());

        holder.mAsigneeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mParent.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mParent.getContext());
                }
                builder.setTitle("Jelentkezés a feladatra")
                        .setMessage("Biztosan elvállalod a feladatot?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //holder.mAssignee.setText("Felelos beirva");
                                //holder.mAsigneeBtn.setText("Lejelentkezés");
                                holder.mAsigneeBtn.setVisibility(View.GONE);

                                String name = "Felelos neve";

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    for (UserInfo profile : user.getProviderData()) {
                                        // Id of the provider (ex: google.com)
                                        String providerId = profile.getProviderId();

                                        // UID specific to the provider
                                        String uid = profile.getUid();

                                        // Name, email address, and profile photo Url
                                        name = profile.getDisplayName();
                                        String email = profile.getEmail();
                                        Uri photoUrl = profile.getPhotoUrl();
                                    }
                                }

                                //holder.mAssignee.setText(mDataset.get(position).getId());
                                todoUpdateHandler.onTodoUpdated(mDataset.get(position).getId(), name);
                                //mDataset.get(position).setAssignee(name);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });

        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mParent.getContext(), "Card long clicked", Toast.LENGTH_SHORT).show();
                Vibrator vibe = (Vibrator) mParent.getContext().getSystemService(mParent.getContext().VIBRATOR_SERVICE);
                vibe.vibrate(100);
                return true;
            }
        });

        holder.mAsigneeBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(mParent.getContext(), "Long clicked", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addUpdateHandler(TodoUpdateHandler th) {
        todoUpdateHandler = th;
    }
}
