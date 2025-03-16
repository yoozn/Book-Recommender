package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class homePage extends AppCompatActivity {

    TextView fieldWelcomeMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        Intent getIntent = getIntent();
        String currentUsername = getIntent.getStringExtra("currentUsername");
        StringBuilder welcomeMessage = new StringBuilder();
        fieldWelcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.append(fieldWelcomeMessage.getText().toString());
        welcomeMessage.append(" ");
        welcomeMessage.append(currentUsername);
        fieldWelcomeMessage.setText(welcomeMessage.toString());

    }
}