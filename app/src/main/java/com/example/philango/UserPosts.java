package com.example.philango;

import static com.example.philango.MainActivity.amount;
import static com.example.philango.MainActivity.goals;
import static com.example.philango.MainActivity.names;
import static com.example.philango.MainActivity.userNames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserPosts extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    CustomAdapter myPostsAdapter;
    FirebaseUser user;

    public static ArrayList<String> myNames = new ArrayList<String>();
    public static ArrayList<String> myGoals = new ArrayList<String>();
    public static ArrayList<String> myUserNames = new ArrayList<String>();
    public static ArrayList<Double> myAmount = new ArrayList<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        user = FirebaseAuth.getInstance().getCurrentUser();
        filterData();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        CustomItem ci = new CustomItem(myNames,myGoals,myUserNames,myAmount);
        myPostsAdapter = new CustomAdapter(ci,this);
        recyclerView.setAdapter(myPostsAdapter);
    }

    public void filterData(){
        myNames.clear();
        myGoals.clear();
        myUserNames.clear();
        myAmount.clear();
        String userName = user.getDisplayName();
        for(int i=0;i< names.size();i++){
            if(userNames.get(i).equals(userName)){
                myNames.add(names.get(i));
                myGoals.add(goals.get(i));
                myUserNames.add(userName);
                myAmount.add(amount.get(i));
            }
        }
    }

    protected void onResume() {
        super.onResume();
        //https://www.youtube.com/watch?v=fvmbNqn-hxI
        filterData();
    }

    @Override
    public void onClick(int position) {
        //String userName = userNames.get(position);
        Intent toFullPost = new Intent(UserPosts.this,FullPost.class);
        toFullPost.putExtra("com.example.philango.UserPosts.position", position);
        startActivity(toFullPost);
        finish();
    }
}