package com.example.philango;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philango.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    FloatingActionButton fab;
    String[] arr1 = {"Item1","Item2","Item3","Item4","Item5","Item6","Item7","Item8","Item9","Item10"};
    String[] arr2 = {"atem1","atem2","atem3","atem4","atem5","atem6","atem7","atem8","atem9","atem10"};

    ActivityMainBinding binding;

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

        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        CustomItem ci = new CustomItem(arr1, arr2);
        CustomAdapter ca = new CustomAdapter(ci);
        recyclerView.setAdapter(ca);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Data Set append the given values
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
}