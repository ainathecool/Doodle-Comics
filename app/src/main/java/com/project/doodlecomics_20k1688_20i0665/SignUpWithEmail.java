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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpWithEmail extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_email);

        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        TextView signUpButton = findViewById(R.id.signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithEmail();
            }
        });

        TextView loginScreen = findViewById(R.id.loginScreen);
        loginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(SignUpWithEmail.this, LoginOptions.class );
                startActivity(intent);
            }
        });
    }

    private void signUpWithEmail() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpWithEmail.this, "Signup successfull", Toast.LENGTH_LONG).show();
                        //adding it to db, creating profile

                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user.getUid() != null)
                        {
                            addUserToDatabase(user.getUid(), email);
                        }

//
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpWithEmail.this, "Signup Failed. Try Again", Toast.LENGTH_LONG).show();
//
                    }
                });

    }

    private void addUserToDatabase(String userId, String email) {
        // Assuming "Users" is the table name in your Realtime Database
        DatabaseReference userRef = usersDatabase.child(userId);

        // You can add more user information to the database as needed
        User newUser = new User(email, "AdditionalUserInfo");
        userRef.setValue(newUser);

        // Now the user is added to the "Users" table with their unique user ID
    }
}
