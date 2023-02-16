package com.example.philango;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddEntry extends AppCompatActivity {
    EditText OrganisationName,OrganisationGoal;
    Button createButton;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        //ArrayList<String> names =(ArrayList<String>) args.getStringArrayList("com.example.philango.MainActivity.names");
        //ArrayList<String> goals =(ArrayList<String>) args.getStringArrayList("com.example.philango.MainActivity.goals");

        OrganisationName = findViewById(R.id.organisationName);
        OrganisationGoal = findViewById(R.id.organisationGoal);
        createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!OrganisationName.getText().toString().isEmpty() && !OrganisationGoal.getText().toString().isEmpty()) {
                    MainActivity.names.add(OrganisationName.getText().toString());
                    MainActivity.goals.add(OrganisationGoal.getText().toString());
                    createNewDBEntry(OrganisationName.getText().toString(),OrganisationGoal.getText().toString());
                    Intent toMain = new Intent(AddEntry.this,MainActivity.class);
//                    Bundle args = new Bundle();
//                    args.putStringArrayList("com.example.philango.AddEntry.names",names);
//                    args.putStringArrayList("com.example.philango.AddEntry.goals",goals);
//                    toMain.putExtra("addEntryBUNDLE", args);
                    startActivity(toMain);
                    finish();
                }
                else{
                    Toast.makeText(AddEntry.this, "Fields shouldn't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createNewDBEntry(String OrganisationName,String OrganisationGoal) {
        //Create a new Entry
        Map<String, String> entry = new HashMap<>();
        entry.put("Name", OrganisationName);
        entry.put("Goal", OrganisationGoal);

        //Add a new Document with a generated ID
        db.collection("entries")
                .add(entry)
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
    }
}