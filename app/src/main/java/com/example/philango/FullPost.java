package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class FullPost extends AppCompatActivity {
    private TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_post);

        textView2 = findViewById(R.id.textView2);
        for(int i=0;i<MainActivity.userNames.size();i++){
            Toast.makeText(this, Integer.toString(i)+" "+ MainActivity.userNames.get(i), Toast.LENGTH_SHORT).show();
        }
        int position = getIntent().getIntExtra("com.example.philango.MainActivity.position", 0);
        textView2.setText(MainActivity.userNames.get(position));
        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
    }
}