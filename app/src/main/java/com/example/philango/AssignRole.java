package com.example.philango;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AssignRole extends AppCompatActivity {
    Spinner roles;
    Button nextButton;
    FirebaseUser user;
    FirebaseFirestore userCreate = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_role);

        roles = findViewById(R.id.spinner);
        nextButton = findViewById(R.id.nextButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roles.setAdapter(adapter);
//        roles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String role = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), role, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AssignRole.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(AssignRole.this, "Please select a role from the drop down", Toast.LENGTH_SHORT).show();
//            }
//        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String role = ((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString();
//                    if(role!=" abc"){
//                        Toast.makeText(AssignRole.this, "Hello", Toast.LENGTH_SHORT).show();
//                    } //Didn't work
                    Intent toMain = new Intent(AssignRole.this,MainActivity.class);
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null && isValid(roles.getSelectedItemPosition())) {
                            UserDetails currUser = new UserDetails(user.getEmail(), user.getDisplayName(), role);
                            userCreate.collection("Users").
                                    add(currUser)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error adding document", e);
                                        }
                                    });
                            startActivity(toMain);
                            finish();
                    }
                    else if(!isValid(roles.getSelectedItemPosition())){
                        Toast.makeText(AssignRole.this, "Select a role to proceed", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AssignRole.this, "User Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String role = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), role, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AssignRole.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(AssignRole.this, "Please select a role from the drop down", Toast.LENGTH_SHORT).show();
//            }
//        });

        });
    }

    private boolean isValid(int position){
        if(position==0){
            return false;
        }
        else{
            return true;
        }
    }

}