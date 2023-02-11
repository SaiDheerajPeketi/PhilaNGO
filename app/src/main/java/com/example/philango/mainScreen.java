package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class mainScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    String[] arr1 = {"Item1","Item2","Item3","Item4","Item5","Item6","Item7","Item8","Item9","Item10"};
    String[] arr2 = {"atem1","atem2","atem3","atem4","atem5","atem6","atem7","atem8","atem9","atem10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        CustomItem ci = new CustomItem(arr2, arr2);
        CustomAdapter ca = new CustomAdapter(ci);
        recyclerView.setAdapter(ca);
    }
}