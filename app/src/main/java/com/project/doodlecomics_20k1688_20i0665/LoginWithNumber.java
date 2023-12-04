package com.project.doodlecomics_20k1688_20i0665;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginWithNumber extends AppCompatActivity {

    private EditText numberEditText;
    private TextView sendOTP;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_number);

        mAuth = FirebaseAuth.getInstance();
        numberEditText = findViewById(R.id.number);
        sendOTP = findViewById(R.id.sendOTP);

        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithNumber();
            }
        });

        TextView signUpScreen = findViewById(R.id.signupScreen);
        signUpScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginWithNumber.this, CreateYourAccount.class);
                startActivity(intent);
            }
        });
    }

    private void loginWithNumber() {
        String phoneNumber = numberEditText.getText().toString().trim();

        // Check if the phone number is valid (you may need to add more validation)
        if (phoneNumber.isEmpty()) {
            Toast.makeText(LoginWithNumber.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Implement Firebase Phone Authentication
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout duration
                .setActivity(LoginWithNumber.this) // Activity (for callback binding)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // The SMS verification code has been sent to the provided phone number, now you need to get the code from the user
                        // You can store the verificationId and forceResendingToken to use later when verifying the code
                        Intent intent = new Intent(LoginWithNumber.this, VerifyOTPLg.class);
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                       // signInWithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(LoginWithNumber.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }

                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }



}
