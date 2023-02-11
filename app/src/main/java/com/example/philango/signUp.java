package com.example.philango;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class signUp extends AppCompatActivity {
    EditText nameText,emailText,passwordText;
    Button signUp;

    //Create object of DataBaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://philango-a5bd5-default-rtdb.firebaseio.com/");
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

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameText.getText().toString();
                final String email = emailText.getText().toString();
                final String password = passwordText.getText().toString();
//                final String

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(signUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if Email is not registered before
                            if (snapshot.hasChild(email)) {
                                Toast.makeText(signUp.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                //Sending data to Firebase Realtime Database.
                                //Using email as unique identity of every user.
                                //All other details come under Email id.
                                databaseReference.child("users").child(email).child("password").setValue(password);
                                databaseReference.child("users").child(email).child("name").setValue(name);

                                //Show a success message and then finish the activity
                                Toast.makeText(signUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent toMainScreen = new Intent(signUp.this,mainScreen.class);
                                startActivity(toMainScreen);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }
        });

    }
}