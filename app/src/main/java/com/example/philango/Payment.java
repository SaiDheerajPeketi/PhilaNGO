package com.example.philango;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment extends AppCompatActivity {
    private EditText amountEditText;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amountEditText = findViewById(R.id.amountEditText);
        payButton = findViewById(R.id.payButton);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountEditText.getText().toString().isEmpty()) {
                    if (getIntent().getDoubleExtra("com.example.philango.FullPost.RemainingTarget", 0.0d) <= Double.valueOf(amountEditText.getText().toString())) {

                    } else {
                        Toast.makeText(Payment.this, "Enter an amount lesser than or equal to remaining target", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Payment.this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}