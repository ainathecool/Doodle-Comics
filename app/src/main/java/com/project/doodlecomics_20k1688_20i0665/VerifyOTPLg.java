
package com.project.doodlecomics_20k1688_20i0665;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.PhoneAuthCredential;
        import com.google.firebase.auth.PhoneAuthProvider;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class VerifyOTPLg extends AppCompatActivity {

    TextView verifyOTPButton;
    FirebaseAuth mAuth;
    DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_lg);
        String verificationId = getIntent().getStringExtra("verificationId"); // Get verificationId from Intent
        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        verifyOTPButton = findViewById(R.id.verifyOTP);
        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to handle OTP verification
                verifyOTP(verificationId);
            }
        });
    }

    private void verifyOTP(String verificationId) {
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

        // Check if the verificationId is not null or empty
        if (verificationId != null && !verificationId.isEmpty()) {
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(
                            verificationId,
                            otpText
                    );

            mAuth.signInWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(VerifyOTPLg.this, "success", Toast.LENGTH_LONG).show();
                            // If OTP verification is successful, add the user to the database
//                            FirebaseUser user = authResult.getUser();
//                            addUserToDatabase(user.getUid());
                            signInWithPhoneAuthCredential(credential);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VerifyOTPLg.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            // Handle the case where verificationId is null or empty
            Toast.makeText(VerifyOTPLg.this, "Verification failed: Invalid verificationId", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to sign in with the phone authentication credential
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(VerifyOTPLg.this, "signin success.", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(VerifyOTPLg.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

