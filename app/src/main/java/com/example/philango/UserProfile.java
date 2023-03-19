package com.example.philango;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserProfile extends AppCompatActivity {
    TextView userNameText,emailText;
    Button updateButton;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore userDb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userNameText = findViewById(R.id.userNameText);
        emailText = findViewById(R.id.emailText);
        updateButton = findViewById(R.id.updateButton);
//        userDb.collection("Users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if(document.getId()=="email"){
//                                    emailText.setText((CharSequence) document.getData().get("email"));
//                                }
//                                else if(document.getId()=="username"){
//                                    userNameText.setText((CharSequence) document.getData().get("username"));
//                                }
//                            }
//                        } else {
//                            Log.w("TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
        
        userNameText.setText(currentUser.getDisplayName());
        emailText.setText(currentUser.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Update Details Feature Here
                Intent toUpdateDetails = new Intent(UserProfile.this,UpdateProfile.class);
                startActivity(toUpdateDetails);
                finish();
            }
        });
    }
}