package com.example.project_ui_implementation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        TextView googleLogin = findViewById(R.id.loginGoogle);
        String txtGL= googleLogin.getText().toString();
        SpannableString spannabletxtGL= new SpannableString(txtGL);
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
    }
    //Method to add Usernames inside the database.
    public void addUsername(View view){
        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_LONG).show();
        @SuppressLint("WrongViewCast")
        TextInputLayout fieldUsername = findViewById(R.id.layoutUsernameInput);
        TextInputLayout fieldPassword = findViewById(R.id.layoutPasswordInput);
        EditText txtUsername =fieldUsername.getEditText();
        EditText txtPassword= fieldUsername.getEditText();
        String nUsername=txtUsername.getText().toString();
        String nPassword=txtPassword.getText().toString();
        Users newUser = new Users(nUsername, nPassword);
        newUser.addGenre("Romance");
        newUser.addGenre("Action");
        databaseReference=database.getReference("Users");
        databaseReference.child(newUser.toString()).setValue(newUser);
        Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_LONG).show();

    }

}

