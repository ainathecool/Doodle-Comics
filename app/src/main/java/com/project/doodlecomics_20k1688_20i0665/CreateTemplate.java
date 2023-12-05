package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.ByteArrayOutputStream;

public class CreateTemplate extends AppCompatActivity {
    private DrawingView drawingView; // Your custom view class for drawing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_template);

        drawingView = findViewById(R.id.drawing_canvas); // Make sure you have your DrawingView with this ID
        Button buttonSaveTemplate = findViewById(R.id.buttonSaveTemplate);

        buttonSaveTemplate.setOnClickListener(view -> saveDrawing());
    }

    private void saveDrawing() {
        // Capture the drawing from the custom view
        Bitmap bitmap = drawingView.getBitmap();

        // Convert bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        // Get a reference to the storage service
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference for the new template
        StorageReference templateRef = storageRef.child("templates/" + "template_" + System.currentTimeMillis() + ".png");

        // Upload the byte array to Firebase
        templateRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            // Handle successful upload, e.g., display a message to the user or update the UI
        }).addOnFailureListener(e -> {
            // Handle failed upload, e.g., display an error message to the user
        });
    }
}
