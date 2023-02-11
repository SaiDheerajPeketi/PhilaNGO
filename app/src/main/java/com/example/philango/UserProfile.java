package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView userNameText,emailText;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userNameText = findViewById(R.id.userNameText);
        emailText = findViewById(R.id.emailText);
        updateButton = findViewById(R.id.updateButton);

        userNameText.setText(R.string.auh);
        emailText.setText(R.string.aeh);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Update Details Feature Here
            }
        });
    }
}