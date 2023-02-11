package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signUp extends AppCompatActivity {
    EditText nameText,emailText,passwordText;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainScreen = new Intent(signUp.this,mainScreen.class);
                startActivity(toMainScreen);
            }
        });

    }
}