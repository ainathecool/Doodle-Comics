package com.project.doodlecomics_20k1688_20i0665;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VerifyOTP extends AppCompatActivity {

    TextView verifyOTPButton;
    FirebaseAuth mAuth;
    DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        String code = getIntent().getStringExtra("code");
        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        verifyOTPButton = findViewById(R.id.verifyOTP);
        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to handle OTP verification
                verifyOTP(code);
            }
        });
    }

    private void verifyOTP(String code) {
        // Get the individual OTP digits
        EditText otp1 = findViewById(R.id.OTP_1);
        EditText otp2 = findViewById(R.id.OTP_2);
        EditText otp3 = findViewById(R.id.OTP_3);
        EditText otp4 = findViewById(R.id.OTP_4);
        EditText otp5 = findViewById(R.id.OTP_5);
        EditText otp6 = findViewById(R.id.OTP_6);

        // Concatenate the OTP digits into a single string
        String otpText = otp1.getText().toString() +
                otp2.getText().toString() +
                otp3.getText().toString() +
                otp4.getText().toString() +
                otp5.getText().toString() +
                otp6.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(
                        code,
                        otpText
                );

        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        // If OTP verification is successful, add the user to the database
                        FirebaseUser user = authResult.getUser();
                        addUserToDatabase(user.getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VerifyOTP.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addUserToDatabase(String userId) {

        DatabaseReference userRef = usersDatabase.child(userId);


        User newUser = new User("AdditionalUserInfo");
        userRef.setValue(newUser);

    }
}
