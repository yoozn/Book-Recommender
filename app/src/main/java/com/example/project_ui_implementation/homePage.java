package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_ui_implementation.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homePage extends AppCompatActivity {

    private Users CurrentUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView fieldWelcomeMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        Intent getIntent = getIntent();
        CurrentUser = (Users) getIntent.getSerializableExtra("CurrentUser");
        String currentUsername = CurrentUser.getUsername();
        StringBuilder welcomeMessage = new StringBuilder();
        fieldWelcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.append(fieldWelcomeMessage.getText().toString());
        welcomeMessage.append(" ");
        welcomeMessage.append(currentUsername);
        fieldWelcomeMessage.setText(welcomeMessage.toString());

        //-----------------------//
        //Setting the environment of the database.
        database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("Users");
    }
}