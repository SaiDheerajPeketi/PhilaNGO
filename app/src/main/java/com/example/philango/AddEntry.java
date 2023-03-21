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
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddEntry extends AppCompatActivity {
    EditText OrganisationName,OrganisationGoal,Amount;
    Button createButton;


    FirebaseFirestore userDb = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID = user.getUid();

    boolean isValidAmount(String amount){
        double amt = Double.valueOf(amount);
        String temp=String.valueOf(amt);
        for(int i = 0; i < temp.length(); i++){
            if(temp.charAt(i)=='.'){
                if(temp.length()-i-1<=2)
                    return true;
            }
        }
//        String amtStr = Double.toString(amt);
//        String[] amtSplit = amtStr.split("\\.");
//        Toast.makeText(this, String.valueOf(amtSplit.length), Toast.LENGTH_SHORT).show();
//        if(amtSplit.length<2 || amtSplit[1].length()<=2){
//            return true;
//        }

        return false;
    }

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
        Amount = findViewById(R.id.editTextAmount);
        createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!OrganisationName.getText().toString().isEmpty() && !OrganisationGoal.getText().toString().isEmpty() && !Amount.getText().toString().isEmpty() && isValidAmount(Amount.getText().toString())) {
//                    MainActivity.names.add(OrganisationName.getText().toString());
//                    MainActivity.goals.add(OrganisationGoal.getText().toString());
//                    String amount = Amount.getText().toString();
//                    String[] split = amount.split(".");
//                    while(split[1].length()>1){
//                        Toast.makeText(AddEntry.this, "Only one decimal place allowed", Toast.LENGTH_SHORT).show();
//                        amount = Amount.getText().toString();
//                        split = amount.split(".");
//                    }



                    createNewDBEntry(OrganisationName.getText().toString(),OrganisationGoal.getText().toString(),Amount.getText().toString());
                    Intent toMain = new Intent(AddEntry.this,MainActivity.class);
//                    Bundle args = new Bundle();
//                    args.putStringArrayList("com.example.philango.AddEntry.names",names);
//                    args.putStringArrayList("com.example.philango.AddEntry.goals",goals);
//                    toMain.putExtra("addEntryBUNDLE", args);
                    startActivity(toMain);
                    finish();
                }
                else{
                    if(OrganisationName.getText().toString().isEmpty() || OrganisationGoal.getText().toString().isEmpty() || Amount.getText().toString().isEmpty())
                        Toast.makeText(AddEntry.this, "Fields shouldn't be empty", Toast.LENGTH_SHORT).show();
                    if(isValidAmount(Amount.getText().toString())==false){
                        Toast.makeText(AddEntry.this, "Amount should be only upto two decimal places", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void createNewDBEntry(String OrganisationName,String OrganisationGoal,String Amount) {
        double amount = Double.valueOf(Amount);
        //Create a new Entry
        Map<String, Object> entry = new HashMap<>();
        //entry.put("UserID", userID);


        final long[] currCount = {0};
        userDb.collection("postCount")
                        .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Hello", document.getId() + " => " + document.getData());
                                Map<String, Object> mp = document.getData();
                                currCount[0] = (long) mp.get("count");


                                //Toast.makeText(MainActivity.this, document.getId() + " => " + document.getData(), Toast.LENGTH_SHORT).show();

                            }
                        }
                });

        entry.put("PostID", currCount[0]++);
        entry.put("Username",user.getDisplayName().toString());
        entry.put("Name", OrganisationName);
        entry.put("Goal", OrganisationGoal);
        entry.put("Amount", amount);
        CollectionReference entries = userDb.collection("entries");
        entries.document(userID.toString()).set(entry);

        Map<String, Object> counter = new HashMap<>();
        counter.put("count",currCount[0]);
        userDb.collection("postCount")
                .add(counter)
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

        //Add a new Document with a generated ID
//        userDb.collection("entries")
//                .add(entry)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });
    }
}