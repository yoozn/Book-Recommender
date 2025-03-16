package com.example.project_ui_implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ui_implementation.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private TextView googleLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        /**
         * Setting specific text, Google Account to be clickable.
         * Also to make the Create Account here to be clickable.
         */

        Intent intent = new Intent(MainActivity.this, HorizontalCarouselActivity.class);
        startActivity(intent);
        TextView googleLogin = findViewById(R.id.loginGoogle);
        TextView crtAccount = findViewById(R.id.crtAccountTXT);
        String txtGL= googleLogin.getText().toString();
        String txtcrtA=crtAccount.getText().toString();
        SpannableString spannabletxtGL= new SpannableString(txtGL);
        SpannableString spannabletxtcrtA= new SpannableString(txtcrtA);
        //Changing the settings of the Strings inside the textViews
        spannabletxtcrtA.setSpan(new ForegroundColorSpan(Color.RED), 33, 45, 0);
        //Clickable to create a new user.
        ClickableSpan ClicktxtcrtA= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Toast.makeText(MainActivity.this, "Creating User", Toast.LENGTH_LONG).show();
                Intent goCreateAccunt= new Intent(MainActivity.this, CreateAccount.class);
                startActivity(goCreateAccunt);
            }
        };
        spannabletxtcrtA.setSpan(ClicktxtcrtA, 33, 45, 0);
        crtAccount.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        crtAccount.setText(spannabletxtcrtA);

        //Clickable logging for google
        spannabletxtGL.setSpan(new ForegroundColorSpan(Color.RED), 11, 25, 0);
        ClickableSpan ClicktxtGL= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "Redirecting", Toast.LENGTH_LONG).show();
            }
            };
        spannabletxtGL.setSpan(ClicktxtGL, 11, 25, 0);
        googleLogin.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        googleLogin.setText(spannabletxtGL);
        //----------------------------------------------------------------------//
    }
}

