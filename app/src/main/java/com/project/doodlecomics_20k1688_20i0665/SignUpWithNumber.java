package com.project.doodlecomics_20k1688_20i0665;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpWithNumber extends AppCompatActivity {

    EditText number;
    TextView sendOTP, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_number);

        number = findViewById(R.id.number);
        sendOTP = findViewById(R.id.sendOTP);
        login = findViewById(R.id.loginScreen);

        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options = PhoneAuthOptions
                        .newBuilder()
                        .setActivity(SignUpWithNumber.this)
                        .setPhoneNumber(number.getText().toString())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                Intent intent = new Intent(SignUpWithNumber.this, VerifyOTP.class);
                                intent.putExtra("code", s);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(SignUpWithNumber.this, "Successfull, sending otp", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SignUpWithNumber.this, "Failed", Toast.LENGTH_LONG).show();

                            }
                        })

                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

    }
}


