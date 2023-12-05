package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileSettings extends AppCompatActivity {

    EditText editUsername, editEmail, editNumber, editPassword, bio, socialMedia;
    TextView updateProfile;

    // Firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize views
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editNumber = findViewById(R.id.editNumber);
        editPassword = findViewById(R.id.editPassword);
        bio = findViewById(R.id.bio);
        socialMedia = findViewById(R.id.socialMedia);
        updateProfile = findViewById(R.id.updateProfile);

        // Set click listener for the Update Profile button
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        // Get the data from EditText fields
        String username = editUsername.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String number = editNumber.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String userBio = bio.getText().toString().trim();
        String social = socialMedia.getText().toString().trim();

        // Get the current user UID
        String userId = firebaseAuth.getCurrentUser().getUid();

        // Update the user's profile data in the Firebase Realtime Database
        updateUserProfile(userId, username, email, number, password, userBio, social);
    }

    private void updateUserProfile(String userId, String username, String email,
                                   String number, String password, String userBio,
                                   String social) {
        // Create a User object with the provided details
        User updatedUser = new User(email, "", username, number, password, userBio, social);

        // Update the user's profile in the "Users" table in Firebase Realtime Database
        usersDatabase.child(userId).setValue(updatedUser);

        // Notify the user that the profile is updated
        Toast.makeText(ProfileSettings.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProfileSettings.this, Profile.class);
        startActivity(intent);
    }
}
