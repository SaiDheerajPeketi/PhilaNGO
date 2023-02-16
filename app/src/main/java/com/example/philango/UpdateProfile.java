package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProfile extends AppCompatActivity {
    FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    EditText userNameEditText;
    TextView emailTextView;

    Button updateDetailsButton;
    FirebaseFirestore userDb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        userNameEditText = findViewById(R.id.userNameEditText);
        //emailEditText = findViewById(R.id.emailEditText);
        emailTextView = findViewById(R.id.emailTextView);
        updateDetailsButton = findViewById(R.id.updateDetailsButton);

        //userNameEditText.setText(currUser.getDisplayName(), TextView.BufferType.EDITABLE);
        userNameEditText.setHint(currUser.getDisplayName());
        //emailEditText.setText(currUser.getEmail(), TextView.BufferType.EDITABLE);
        emailTextView.setText(currUser.getEmail());

        updateDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                if(userName.isEmpty()){
                    Toast.makeText(UpdateProfile.this, "Please enter a valid Username", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateData(userName);
                }
            }
        });
    }

    private void updateData(String userName){
//        userDb.collection("Users")
//                .whereEqualTo()
    }
}