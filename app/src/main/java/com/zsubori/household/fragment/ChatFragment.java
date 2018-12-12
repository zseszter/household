package com.zsubori.household.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zsubori.household.R;
import com.zsubori.household.adapter.PostAdapter;
import com.zsubori.household.data.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatFragment extends Fragment {

    @BindView(R.id.recyclerViewPosts)
    RecyclerView recyclerViewPosts;
    @BindView(R.id.postInput)
    EditText postInput;
    @BindView(R.id.sendPostBtn)
    ImageButton sendPostBtn;
    @BindView(R.id.postScroll)
    ScrollView postScroll;

    private ArrayList<Post> postList;
    private PostAdapter chatAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FirebaseFirestore db;
    private String TAG = "database";

    public ChatFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this,view);

        recyclerViewPosts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPosts.setLayoutManager(mLayoutManager);

        db = FirebaseFirestore.getInstance();

        postList = new ArrayList<Post>();
        chatAdapter = new PostAdapter(postList);

        loadPosts();

        postScroll.post(new Runnable() {
            @Override
            public void run() {
                postScroll.scrollTo(0, postScroll.getBottom());
            }
        });

        recyclerViewPosts.setAdapter(chatAdapter);

        sendPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!postInput.getText().equals("")) {

                    Post newPost = new Post();

                    String username = "Sender";

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        for (UserInfo profile : user.getProviderData()) {
                            username = profile.getDisplayName();
                        }
                    }

                    newPost.setAuthor(username);
                    newPost.setBody(postInput.getText().toString());
                    postInput.getText().clear();


                    onPostCreated(newPost);

                    chatAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    private void onPostCreated(final Post newPost) {
        db.collection("posts")
                .add(newPost)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        newPost.setUid(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        postList.add(newPost);
        chatAdapter.notifyDataSetChanged();
    }

    private void loadPosts() {
        db.collection("posts")
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
                                Post myPost = new Post();

                                myPost.setAuthor(documentSnapshot.getString("author"));
                                myPost.setBody(documentSnapshot.getString("body"));

                                postList.add(myPost);
                                chatAdapter.notifyDataSetChanged();
                            }

                            Log.d(TAG, "onSuccess: " + postList);
                        }
                    }
                });
    }


}
