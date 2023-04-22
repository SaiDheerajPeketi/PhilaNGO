package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

public class FullPost extends AppCompatActivity {
    private TextView nameTextView,goalTextView,descriptionTextView,amountTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_post);

        nameTextView = findViewById(R.id.nameTextView);
        goalTextView = findViewById(R.id.goalTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        amountTextView = findViewById(R.id.amountTextView);

        //User Name Setter
//        for(int i=0;i<MainActivity.userNames.size();i++){
//            Toast.makeText(this, Integer.toString(i)+" "+ MainActivity.userNames.get(i), Toast.LENGTH_SHORT).show();
//        }
//        int position = getIntent().getIntExtra("com.example.philango.MainActivity.position", 0);
//        textView2.setText(MainActivity.userNames.get(position));
//        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
        int position = getIntent().getIntExtra("com.example.philango.MainActivity.position", 0);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        nameTextView.setText(MainActivity.names.get(position));
        goalTextView.setText(MainActivity.goals.get(position));
        descriptionTextView.setText(MainActivity.descriptions.get(position));
        amountTextView.setText(String.valueOf(MainActivity.amount.get(position)));
    }
}