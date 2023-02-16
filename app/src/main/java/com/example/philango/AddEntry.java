package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEntry extends AppCompatActivity {
    EditText OrganisationName,OrganisationGoal;
    Button createButton;

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
}