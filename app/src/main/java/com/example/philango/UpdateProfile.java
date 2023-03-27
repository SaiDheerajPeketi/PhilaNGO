package com.example.philango;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
                else if(userName==currUser.getDisplayName()){
                    Toast.makeText(UpdateProfile.this, "User Name unchanged", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateData(userName);

                    Intent toMainActivity = new Intent(UpdateProfile.this,MainActivity.class);
                    startActivity(toMainActivity);
                    finish();
                }
            }
        });
    }

    private void updateData(String userName){
        Map<String,Object> updatedUserMap = new HashMap<>();
        updatedUserMap.put("email", currUser.getEmail());
        updatedUserMap.put("username",userName);
        updatedUserMap.put("role", AssignRole.role);

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build();

        currUser.updateProfile(userProfileChangeRequest)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.d("TAG", "User Profile Updated");
                                    Toast.makeText(UpdateProfile.this, "Main Profile Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

//        userDb.collection("Users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if(document.getId()=="role"){
//                                    updatedUserMap.put("role",document.getData());
//                                }
//                                Log.d("TAG", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w("TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });



//        DocumentReference documentReference = userDb.collection("Users")
//                .document()

//        userDb.collection("Users")
//                .whereEqualTo("username",userName)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful() && !task.getResult().isEmpty()){
//                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                            String documentID = documentSnapshot.getId();
//                            userDb.collection("Users")
//                                    .document(documentID)
//                                    .update(updatedUserMap)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(UpdateProfile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(UpdateProfile.this, "Some error occured", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
//                        else{
//                            Toast.makeText(UpdateProfile.this, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



//        userDb.collection("Users").document(currUser.)

//        DocumentReference documentReference = userDb.collection("Users")
//                        .document(currUser.)
//        currUser.updateProfile()

//        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()



    }
}