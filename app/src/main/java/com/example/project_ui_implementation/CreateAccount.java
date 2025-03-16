package com.example.project_ui_implementation;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ui_implementation.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CreateAccount extends AppCompatActivity {

    private Users newUser = new Users();

    private Button goBackbtn;
    private boolean[] selectedGenre;

    private ArrayList<Integer> GenreList = new ArrayList<>();

    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Java", "C++", "Kotlin", "C", "Python", "Javascript"};


    String[] genreOptions = {"Fiction", "History", "Poetry", "Medical"};
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        goBackbtn = findViewById(R.id.btnGoBack);
        /**
         * Check if the button is pressed.
         * */
        goBackbtn.setOnClickListener(v -> {
            Intent GoBack = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(GoBack);
        });

        /**
         *Creation of the dropdown box to select the Subject.
         *
         */

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
        //Users newUser = new Users(nUsername, nPassword);
        newUser.setUsername(nUsername);
        newUser.setUsername(nPassword);
        databaseReference=database.getReference("Users");
        databaseReference.child(newUser.toString()).setValue(newUser);
        Toast.makeText(CreateAccount.this, "Congrats. Account created successfully", Toast.LENGTH_LONG).show();
        txtUsername.setText("");
        txtPassword.setText("");
    }
}