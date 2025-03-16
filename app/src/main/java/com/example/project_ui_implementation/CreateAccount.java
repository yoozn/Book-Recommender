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

    Users newUser= new Users("wName", "wPassword");
    private Button goBackbtn;

    String[] genreOptions = {"Fiction", "History", "Poetry", "Medical", "Music", "Philosophy",
            "Mathematics", "Bibles", "Art", "Design", "Drama", "Computers"};

    private boolean[] selectedGenre=new boolean[genreOptions.length];
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
        TextView txtSelection = findViewById(R.id.textSelection);
        txtSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dropdownBox = new AlertDialog.Builder(CreateAccount.this);
                dropdownBox.setTitle("Select the Subjects to your liking...").setMultiChoiceItems(genreOptions, selectedGenre, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedGenre[which]=isChecked;
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder allSelectedOptions = new StringBuilder();
                        for (int i=0; i<selectedGenre.length; i++){
                            if (selectedGenre[i]){
                                allSelectedOptions.append(genreOptions[i]).append(", ");
                                //Here you would add the to the array list of the user.
                                newUser.addGenre(genreOptions[i]);
                            }
                        }
                        if (allSelectedOptions.length() > 0){
                            allSelectedOptions.setLength(allSelectedOptions.length()-2);
                        }
                        txtSelection.setText(allSelectedOptions.toString());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtSelection.setText("Select Book's Subject");
                    }
                });
                dropdownBox.show();
            }
        });
    }
    //Method to add Usernames inside the database.
    public void addUsername(View view){
        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_LONG).show();
        @SuppressLint("WrongViewCast")
        TextInputLayout fieldUsername = findViewById(R.id.tilwUsername);
        TextInputLayout fieldPassword = findViewById(R.id.tilwPassword);
        TextView txtSelection = findViewById(R.id.textSelection);
        EditText txtUsername =fieldUsername.getEditText();
        EditText txtPassword= fieldPassword.getEditText();
        String nUsername=txtUsername.getText().toString();
        String nPassword=txtPassword.getText().toString();
        newUser.setUsername(nUsername);
        newUser.setPassword(nPassword);
        databaseReference=database.getReference("Users");
        databaseReference.child(newUser.toString()).setValue(newUser);
        Toast.makeText(CreateAccount.this, "Congrats. Account created successfully", Toast.LENGTH_LONG).show();
        txtUsername.setText("");
        txtPassword.setText("");
        txtSelection.setText("Select Book's Subject");
    }
}