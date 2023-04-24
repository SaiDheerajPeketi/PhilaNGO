package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AboutUs extends AppCompatActivity {
    private RecyclerView aboutUsRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        aboutUsRecyclerView = findViewById(R.id.aboutUsRecyclerView);
        aboutUsRecyclerView.setHasFixedSize(true);
    }
}