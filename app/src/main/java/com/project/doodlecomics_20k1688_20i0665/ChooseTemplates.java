package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChooseTemplates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_templates);

        // Define URLs
        String[] imageUrls = {
                "gs://doodle-comics.appspot.com/template1.jpg",
                "gs://doodle-comics.appspot.com/template2.jpg",
                "gs://doodle-comics.appspot.com/template3.jpg",
                "gs://doodle-comics.appspot.com/template4.jpg"
        };

        // Load images from URLs
        loadFirebaseImage(imageUrls[0], R.id.imageTemplate1);
        loadFirebaseImage(imageUrls[1], R.id.imageTemplate2);
        loadFirebaseImage(imageUrls[2], R.id.imageTemplate3);
        loadFirebaseImage(imageUrls[3], R.id.imageTemplate4);
    }

    private void loadFirebaseImage(String imageUrl, int imageViewId) {
        ImageView imageView = findViewById(imageViewId);
        new Thread(() -> {
            try {
                URL url = new URL(imageUrl.replace("gs://", "https://firebasestorage.googleapis.com/v0/b/"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                runOnUiThread(() -> {
                    imageView.setImageBitmap(myBitmap);
                    imageView.setOnClickListener(view -> startDoodlingActivity(imageUrl));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startDoodlingActivity(String imageUrl) {
        Intent intent = new Intent(this, StartDoodlingTemplate.class);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }
}
