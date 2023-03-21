package com.example.philango;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginRegisterActivity";
    int AUTHUI_REQUEST_CODE = 10001; //Unique for your Application

    Button loginRegister;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // ...
            //We have signed in the user or we have a new user
            assert user != null;
            Log.d(TAG,"onActivityResult: " + user.getEmail());
            if(Objects.requireNonNull(user.getMetadata()).getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                //New User
                //  Toast.makeText(this, "New", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginRegisterActivity.this, "Please Select a Role", Toast.LENGTH_SHORT).show();
                Intent intentn = new Intent(LoginRegisterActivity.this,AssignRole.class);
                startActivity(intentn);
                finish();
            }
            else{
                //This is a returning user
            //    Toast.makeText(this, "Last", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginRegisterActivity.this, "Welcome back again", Toast.LENGTH_SHORT).show();
                Intent intentl = new Intent(LoginRegisterActivity.this,MainActivity.class);

                startActivity(intentl);
            }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            if(response == null){
                Log.d(TAG, "onActivityResult: The User has cancelled the sign in request");
            }
            else{
                Log.e(TAG,"onActivityResult: ",response.getError());
            }
        }
    }
/*
    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");

                    if(result.getResultCode() == AUTHUI_REQUEST_CODE){
                        Intent rintent = result.getData();
                        if(rintent != null){
                            //Extract data here
                            String data = rintent.getStringExtra("result");
                            //We have signed in the user or we have a new user
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Log.d(TAG,"onActivityResult: " + user.getEmail());
                            if(user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                                //New User
                                Toast.makeText(LoginRegisterActivity.this, "Welcome New User", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //This is a returning user
                                Toast.makeText(LoginRegisterActivity.this, "Welcome back again", Toast.LENGTH_SHORT).show();
                            }
                            Intent intentl = new Intent(LoginRegisterActivity.this,MainActivity.class);
                            startActivity(intentl);
                            finish();
                        }
                        else{
                            //Signing in Failed
                            IdpResponse response = IdpResponse.fromResultIntent(rintent);
                            if(response == null){
                                Log.d(TAG, "onActivityResult: The User has cancelled the sign in request");
                            }
                            else{
                                Log.e(TAG,"onActivityResult: ",response.getError());
                            }
                        }
                    }
                }
            });
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        //Toast.makeText(this, "Om Namah Shivaya", Toast.LENGTH_SHORT).show();
        loginRegister = findViewById(R.id.loginRegisterButton);


        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this,MainActivity.class));
            LoginRegisterActivity.this.finish();
        }



    }

    public void handleLoginRegister(View view){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setLogo(R.drawable.ic_launcher_foreground)
                .build();
//                .setAlwaysShowSignInMethodScreen(true)
//                .setIsSmartLockEnabled(false)

        signInLauncher.launch(signInIntent);
        //startActivityForResult(intent, AUTHUI_REQUEST_CODE);  //This method is deprecated
//        getResult.launch(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}