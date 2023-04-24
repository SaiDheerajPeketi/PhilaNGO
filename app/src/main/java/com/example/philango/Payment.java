package com.example.philango;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firestore.admin.v1.Index;
import com.google.firestore.v1.StructuredQuery;
//import com.razorpay.Checkout;
//import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
//import dev.shreyaspatil.easyupipayment.*;
//import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
//import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
//import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment extends AppCompatActivity {
    //implements PaymentResultListener for RazorPay
    private EditText amountEditText;
    private Button payButton;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    final int PAY_REQUEST = 1;
    final int UPI_PAYMENT = 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amountEditText = findViewById(R.id.amountEditText);
        payButton = findViewById(R.id.payButton);



        int position = getIntent().getIntExtra("com.example.philango.FullPost.Position", 0);
        String toUser = MainActivity.userNames.get(position);
        String phoneNumber = MainActivity.phoneNumbers.get(position);
//
//        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
//                .with(this)
//                .setPayeeVpa("shreyaspatil@upi")
//                .setPayeeName("Shreyas Patil")
//                .setTransactionId("20190603022401")
//                .setTransactionRefId("0120192019060302240")
//                .setDescription("For Today's Food")
//                .setAmount("90.00")
//                .build();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountEditText.getText().toString().isEmpty()) {
                    if (getIntent().getDoubleExtra("com.example.philango.FullPost.RemainingTarget", 0.0d) <= Double.valueOf(amountEditText.getText().toString())) {
                            makePayment(Double.valueOf(amountEditText.getText().toString()),MainActivity.upiIds.get(position),toUser,phoneNumber);
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


    private void makePayment(double amount,String upiId,String toUser,String phoneNumber){
        //String tid = java.time.Clock.systemUTC().instant().toString();
        //String tr = tid;
        String amt = String.valueOf(amount);
//        int payableAmount = (int) Math.round(Double.parseDouble(amt) * 100);
        String tid = generateTransactionId(toUser);
//        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_test_fMc98hk1DBOk4E");
//        checkout.setImage();


//        JSONObject jsonObject = new JSONObject();
//        try{
//            jsonObject.put("name",toUser);
//            jsonObject.put("description",tid);
//            jsonObject.put("amount",payableAmount);
//            jsonObject.put("currency","INR");
//            jsonObject.put("notes","Hello I am a Note");
//
//
//            JSONObject prefill = new JSONObject();
//            prefill.put("contact",phoneNumber);
//            prefill.put("email","hello@gmail.com"); //Change this
//            jsonObject.put("prefill",prefill);

//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            jsonObject.put("retry", retryObj);
//            checkout.open(Payment.this,jsonObject);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        Uri uri = new Uri.Builder()
//                .scheme("upi").authority("pay")
//                .appendQueryParameter("pa", upiId)
//                .appendQueryParameter("pn", toUser)
//                .appendQueryParameter("tn", "Hello I am a Note")
//                .appendQueryParameter("am", String.valueOf(amount))
//                .appendQueryParameter("tid", tid)
//                .appendQueryParameter("tr", tr)
//                .appendQueryParameter("cu", "INR")
//                .build();

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",toUser)
                .appendQueryParameter("tn","Hello")
                .appendQueryParameter("am",String.valueOf(amount))
                .appendQueryParameter("cu","INR")
                .build();
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse("upi://pay?pa=<VPA>&pn=<Payee>&tr=<Transaction ID>&am=<Amount>&cu=<Currency Code>&tn=<Transaction Note>"));
        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
//        upiIntent.setData(uri);
////        upiIntent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
////        startActivityForResult(upiIntent,GOOGLE_PAY_REQUEST_CODE);
//        upiIntent.setData(Uri.parse("upi://pay?pa="+upiId+"&pn="+toUser+"&tr="+tid+"&am="+String.valueOf(amount)+"&cu="+"INR"+"&tn="+"I am a Note"));
        String data = "upi://pay?pa="+upiId+"&pn="+toUser+"&tr="+tid+"&am="+String.valueOf(amount)+"&cu="+"INR"+"&tn="+"I am a Note";
        //String data = "upi://pay?pa="+upiId+"&pn="+toUser+"&tr="+tid+"&am="+String.valueOf(1)+"&cu="+"INR"+"&tn="+"I am a Note";
        String hash = generateHash(data);
        data+="&sign="+hash;
        upiIntent.setData(Uri.parse(data));
        upiIntent.putExtra("hash",hash);
        Intent chooser = Intent.createChooser(upiIntent, "Pay with");
        if(chooser.resolveActivity(getPackageManager())!=null){
//            startActivityForResult(chooser, PAY_REQUEST);
            startActivityForResult(chooser, UPI_PAYMENT);
        }
        else{
            Toast.makeText(this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main","response"+resultCode);
        //-1 Result code implies something happened
        switch (requestCode) {
            case UPI_PAYMENT:
                if((RESULT_OK == resultCode) || resultCode ==11){
                    if(data!=null){
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI","onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    }
                    else{
                        Log.e("UPI","onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                }
                else{
                    //When user simply clicks back without payment
                    Log.e("UPI","onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");

                }
                break;
        }

//        if(requestCode == PAY_REQUEST){
//
//            if(isInternetAvailable(Payment.this)){
//
//                if(data==null){
//                    ArrayList<String> dataList = new ArrayList<String>();
//                    dataList.add("nothing");
//                    String temp = "nothing";
//                    Toast.makeText(this, "Transaction not complete", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    String text = data.getStringExtra("response");
//                    ArrayList<String> dataList = new ArrayList<String>();
//                    dataList.add(text);
//
//                    upiPaymentCheck(text);
//                }
//            }
//        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data){
        if(isInternetAvailable(Payment.this)){
            String str = data.get(0);
            Log.e("UPIPAY","upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str==null)
                str="discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for(int i=0;i< response.length;i++){
                String equalStr[] = response[i].split("=");
                if(equalStr.length>=2){
                    if(equalStr[0].toLowerCase().equals("Status".toLowerCase())){
                        status=equalStr[1].toLowerCase();
                    }
                    else if(equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())){
                        approvalRefNo = equalStr[1];
                    }
                }
                else{
                    paymentCancel = "Payment cancelled by user";
                }
            }
            if(status.equals("success")){
                Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                Log.e("UPI","payment successful: " + approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    void upiPaymentCheck(String data){
        String str = data;

        String payment_cancel = "";
        String status = "";
        String response[] = str.split("&");

        for(int i=0;i<response.length;i++){
            String equalStr[] = response[i].split("");
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

    private String generateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String generateTransactionId(String userId) {
        // Generate a random UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Concatenate the UUID, user ID, and current date and time
        String data = uuid + userId + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Calculate the SHA-256 hash of the data
        String checksum = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte[] byteData = md.digest();
            checksum = bytesToHex(byteData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Combine the UUID and checksum to create the transaction ID
        String transactionId = uuid + checksum;

        return transactionId;
    }

    private static String bytesToHex(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteData) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

//    @Override
//    public void onPaymentSuccess(String s) {
//        Toast.makeText(this, "Transaction Successful" + s, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Toast.makeText(this, "Transaction Failed" + s, Toast.LENGTH_SHORT).show();
//    }
}