package com.example.philango;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philango.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.PhoneNumber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    Toolbar toolbar;
    FloatingActionButton fab;
    CustomAdapter ca;

    FirebaseFirestore userDb = FirebaseFirestore.getInstance();
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //String userID = user.getUid();

    public static ArrayList<String> names = new ArrayList<String>();
    public static ArrayList<String> goals = new ArrayList<String>();
    public static ArrayList<String> userNames = new ArrayList<String>();
    public static ArrayList<Double> amount = new ArrayList<Double>();
    public static ArrayList<Double> paidAmount = new ArrayList<Double>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    public static ArrayList<String> phoneNumbers = new ArrayList<String>();
    //String[] arr1 = {"Underprivileged and Physically Challenged People","Lists the No of VO's and NGO's","Flood Relief","Oldage Home","Children's EEducation","Water Distribution","Education","Education","Charity","Maintaining Cleanliness"};
    //String[] arr2 = {"Narayan Seva, 2946622222","Darpan,14414","Astha, 15558555","Sumathi Sevashram, 09369551056","Arohan Foundation, 09598051515","Indradevi NGO,128654566","Akhila Bharat,232324424","NMJS, 58373647347","Lokmanav Sahayak Sansthan,6252652","Mamatva Foundation, 09452637363"};

    ActivityMainBinding binding;

    private void getData(){
        userDb.collection("entries")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                goals.clear();
                                names.clear();
                                amount.clear();
                                userNames.clear(); //Line was missing // Random Order of Users
                                descriptions.clear();
                                phoneNumbers.clear();
                                paidAmount.clear();
                                for (QueryDocumentSnapshot document : value) {
                                    Log.d("Hello", document.getId() + " => " + document.getData());
                                    Map<String, Object> mp = document.getData();
                                    goals.add((String) mp.get("Goal"));
                                    names.add((String) mp.get("Name"));
                                    userNames.add((String) mp.get("Username"));
                                    descriptions.add((String) mp.get("Description"));
                                    amount.add((Double) mp.get("Amount"));
                                    paidAmount.add((Double) mp.get("PaidAmount"));
                                    phoneNumbers.add((String) mp.get("PhoneNumber"));
                                    //Toast.makeText(MainActivity.this, document.getId() + " => " + document.getData(), Toast.LENGTH_SHORT).show();
                                }
                                ca.notifyDataSetChanged();
                            }
                        });
//        userDb.collection("Users")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        userNames.clear();
//                        for (QueryDocumentSnapshot document : value) {
//                            Log.d("Hello", document.getId() + " => " + document.getData());
//                            Map<String, Object> mp = document.getData();
//                            userNames.add((String) mp.get("username"));
//                            //Toast.makeText(MainActivity.this, document.getId() + " => " + document.getData(), Toast.LENGTH_SHORT).show();
//                        }
//                        ca.notifyDataSetChanged();
//                    }
//                });

//        userDb.collection("entries")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            goals.clear();
//                            names.clear();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("Hello", document.getId() + " => " + document.getData());
//                                Map<String, Object> mp = document.getData();
//                                goals.add((String) mp.get("Goal"));
//                                names.add((String) mp.get("Name"));
//
//                                //Toast.makeText(MainActivity.this, document.getId() + " => " + document.getData(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        } else {
//                            Log.d("Hello", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle("Posts");
        toolbar.inflateMenu(R.menu.menu_main);

        //Problematic Line
        //setSupportActionBar(toolbar);

        getData();

        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CustomItem ci = new CustomItem(names,goals,userNames,amount);
        ca = new CustomAdapter(ci,this);
        recyclerView.setAdapter(ca);
        //ca.setClickListener(this);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Data Set append the given values
                    Intent intent = new Intent(MainActivity.this, AddEntry.class);
//                    Bundle args = new Bundle();
//                    args.putStringArrayList("com.example.philango.MainActivity.names",names);
//                    args.putStringArrayList("com.example.philango.MainActivity.goals",goals);
//                    intent.putExtra("BUNDLE", args);
                    startActivity(intent);
                    finish();
//                    Intent toMain = getIntent();
//                    Bundle newArgs = toMain.getBundleExtra("addEntryBUNDLE");
//                    names = (ArrayList<String>) args.getStringArrayList("com.example.philango.AddEntry.names");
//                    goals = (ArrayList<String>) args.getStringArrayList("com.example.philango.AddEntry.goals");

            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Profile:
                        Intent intent = new Intent(MainActivity.this,UserProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.MyPosts:
                        getData();
                        Intent myPosts = new Intent(MainActivity.this,UserPosts.class);
                        startActivity(myPosts);
                        return true;
                    case R.id.Logout:
                        AuthUI.getInstance()
                                .signOut(MainActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent1 = new Intent(MainActivity.this,LoginRegisterActivity.class);
                                        startActivity(intent1);
                                        finish();
                                    }
                                });
                        return true;
//                        Intent intent1 = new Intent(MainActivity.this,LoginRegisterActivity.class);
//                        startActivity(intent1);
//                        finish();

//                        AuthUI.getInstance().signOut(MainActivity.this);
//                        finish();


                    default:
                        //Else Case
                }
                return false;
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() == null ){
            Intent intent = new Intent(MainActivity.this,LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //https://www.youtube.com/watch?v=fvmbNqn-hxI
        getData();
    }


    @Override
    public void onClick(int position) {
        //String userName = userNames.get(position);
        Intent toFullPost = new Intent(MainActivity.this,FullPost.class);
        //Log.d("MAINERROR", Integer.toString(position));
        toFullPost.putExtra("com.example.philango.MainActivity.position", position);
        startActivity(toFullPost);
        //finish();
    }
}