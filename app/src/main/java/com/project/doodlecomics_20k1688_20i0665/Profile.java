package com.project.doodlecomics_20k1688_20i0665;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    de.hdodenhof.circleimageview.CircleImageView avatar;
    TextView artistNameTextView, bioTextView, socialMediaTextView, profileSettings, homepageDrawer;

    // Firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize views
        avatar = findViewById(R.id.artist_avatar);
        artistNameTextView = findViewById(R.id.artist_name);
        bioTextView = findViewById(R.id.bio);
        socialMediaTextView = findViewById(R.id.social);
        profileSettings = findViewById(R.id.profileSettingsButton);
        homepageDrawer = findViewById(R.id.homepageDrawer);

        homepageDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, HomepageDrawer.class);
                startActivity(intent);
            }
        });

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ProfileSettings.class);
                startActivity(intent);
            }
        });

        // Set click listener for the avatar
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Load user profile data
        loadUserProfileData();
    }

    private void loadUserProfileData() {
        // Get the current user UID
        String userId = firebaseAuth.getCurrentUser().getUid();

        // Query the "Users" table to get user data
        usersDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data from the snapshot
                    User user = dataSnapshot.getValue(User.class);

                    // Update UI with user data
                    updateUIWithUserData(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error, if any
                Toast.makeText(Profile.this, "Error loading user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUIWithUserData(User user) {
        if (user != null) {
            // Set artist name, bio, and social media
            artistNameTextView.setText(user.getUsername());
            bioTextView.setText(user.getBio());
            socialMediaTextView.setText(user.getSocialMedia());
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}
