package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView OrganisationSignUpText,NgoSignUpText,ContributorSignUpText;
    EditText userNameInput,passwordInput;
    Button loginButton;

    //Create object of DataBaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://philango-a5bd5-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OrganisationSignUpText = findViewById(R.id.OrganisationSignUpText);
        NgoSignUpText = findViewById(R.id.NgoSignUpText);
        ContributorSignUpText = findViewById(R.id.ContributorSignUpText);
        userNameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);



        OrganisationSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OrganisationSignUpScreen = new Intent(MainActivity.this, signUp.class);
                startActivity(OrganisationSignUpScreen);
            }
        });

        NgoSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NgoSignUpScreen = new Intent(MainActivity.this, NgoSignUp.class);
                startActivity(NgoSignUpScreen);
            }
        });

        ContributorSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ContributorSignUpScreen = new Intent(MainActivity.this, ContributorSignUp.class);
                startActivity(ContributorSignUpScreen);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userName = userNameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                if(userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();
                }
                else if(userNameInput.getText().toString().equals("adminmail") && passwordInput.getText().toString().equals("admin")){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent mainScreen = new Intent(MainActivity.this, mainScreen.class);
                    startActivity(mainScreen);
                }
                else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("",TextView.BufferType.NORMAL);
                }
            }
        });


    }
}