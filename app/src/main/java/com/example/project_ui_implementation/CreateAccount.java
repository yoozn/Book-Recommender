package com.example.project_ui_implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ui_implementation.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private Button goBackbtn;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        goBackbtn=findViewById(R.id.btnGoBack);
        goBackbtn.setOnClickListener(v -> {
            Intent GoBack=new Intent(CreateAccount.this, MainActivity.class);
            startActivity(GoBack);
        });
    }
    //Method to add Usernames inside the database.
    public void addUsername(View view){
        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_LONG).show();
        @SuppressLint("WrongViewCast")
        TextInputLayout fieldUsername = findViewById(R.id.tilwUsername);
        TextInputLayout fieldPassword = findViewById(R.id.tilwPassword);
        EditText txtUsername =fieldUsername.getEditText();
        EditText txtPassword= fieldPassword.getEditText();
        String nUsername=txtUsername.getText().toString();
        String nPassword=txtPassword.getText().toString();
        Users newUser = new Users(nUsername, nPassword);
        newUser.addGenre("Romance");
        newUser.addGenre("Action");
        databaseReference=database.getReference("Users");
        databaseReference.child(newUser.toString()).setValue(newUser);
        Toast.makeText(CreateAccount.this, "Congrats. Account created successfully", Toast.LENGTH_LONG).show();
        txtUsername.setText("");
        txtPassword.setText("");
    }
}