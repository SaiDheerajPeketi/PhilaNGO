package com.example.philango;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;
//import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
//import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
//import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

public class Payment extends AppCompatActivity{
    private EditText amountEditText;
    private Button payButton;

    final int PAY_REQUEST = 1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amountEditText = findViewById(R.id.amountEditText);
        payButton = findViewById(R.id.payButton);

        int position = getIntent().getIntExtra("com.example.philango.FullPost.Position", 0);
        String toUser = MainActivity.userNames.get(position);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountEditText.getText().toString().isEmpty()) {
                    if (getIntent().getDoubleExtra("com.example.philango.FullPost.RemainingTarget", 0.0d) <= Double.valueOf(amountEditText.getText().toString())) {
                            makePayment(Double.valueOf(amountEditText.getText().toString()),MainActivity.upiIds.get(position),toUser);
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


    private void makePayment(double amount,String upiId,String toUser){
        String tid = java.time.Clock.systemUTC().instant().toString();
        String tr = tid;
        Uri uri = new Uri.Builder()
                .scheme("upi").authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", toUser)
                .appendQueryParameter("tn", "Hello I am a Note")
                .appendQueryParameter("am", String.valueOf(amount))
                .appendQueryParameter("tid", tid)
                .appendQueryParameter("tr", tr)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
        upiIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiIntent, "Pay");
        if(chooser.resolveActivity(getPackageManager())!=null){
            startActivityForResult(chooser, PAY_REQUEST);
        }
        else{
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAY_REQUEST){

            if(isInternetAvailable(Payment.this)){

                if(data==null){
                    ArrayList<String> dataList = new ArrayList<String>();
                    dataList.add("nothing");
                    String temp = "nothing";
                    Toast.makeText(this, "Transaction not complete", Toast.LENGTH_SHORT).show();
                }
                else{
                    String text = data.getStringExtra("response");
                    ArrayList<String> dataList = new ArrayList<String>();
                    dataList.add(text);

                    upiPaymentCheck(text);
                }
            }
        }
    }

    void upiPaymentCheck(String data){
        String str = data;

        String payment_cancel = "";
        String status = "";
        String response[] = str.split("&");

        for(int i=0;i<response.length;i++){
            String equalStr[] = response[i].split("=");
            if(equalStr.length >= 2){
                if(equalStr[0].toLowerCase().equals("Status".toLowerCase())){
                    status = equalStr[1].toLowerCase();

                }
            }
            else{
                payment_cancel = "Payment cancelled";

            }

            if(status.equals("success")){
                Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
            }
            else if("Payment cancelled".equals(payment_cancel)){
                Toast.makeText(this, "Payment cancelled by User", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager!= null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo.isConnected() && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable())
                    return true;
        }
        return false;
    }
}