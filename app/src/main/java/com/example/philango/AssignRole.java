package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AssignRole extends AppCompatActivity {
    Spinner roles;
    Button nextButton;

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
                    Intent intent = new Intent(AssignRole.this,MainActivity.class);
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(currentUser!=null) {

                        startActivity(intent);
                        finish();
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

}