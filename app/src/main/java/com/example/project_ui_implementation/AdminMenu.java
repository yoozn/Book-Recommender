package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_ui_implementation.model.Admin;
import com.example.project_ui_implementation.model.Users;

public class AdminMenu extends AppCompatActivity {

    private Admin CurrentAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent getIntent = getIntent();
        CurrentAdmin = (Admin) getIntent.getSerializableExtra("CurrentAdmin");


        TextView welcomeMessage = findViewById(R.id.welcomeMessageAdmin);
        StringBuilder WelcomeMessageAdmin = new StringBuilder();
        WelcomeMessageAdmin.append(welcomeMessage.getText().toString());
        WelcomeMessageAdmin.append(" ");
        WelcomeMessageAdmin.append(CurrentAdmin.getUsername());
        welcomeMessage.setText(WelcomeMessageAdmin.toString());





    }
}