package com.example.project_ui_implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ui_implementation.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private TextView googleLogin;

    private TextView crtAccount;

    //private ArrayList<Users> dbUsers= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        /**
         * Setting specific text, Google Account to be clickable.
         * Also to make the Create Account here to be clickable.
         */

        Intent myIntent = new Intent(this, SearchTest.class);
        startActivity(myIntent);

        googleLogin = findViewById(R.id.loginGoogle);
        crtAccount = findViewById(R.id.crtAccountTXT);
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
    public void validLogin(View view){
        //Database setting to access the data from the users reference.
        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("Users");

        //Setting a reference to the actual fields where the user enters their credentials.
        TextInputLayout fieldUsername = findViewById(R.id.layoutUsernameInput);
        TextInputLayout fieldPassword = findViewById(R.id.layoutPasswordInput);

        //Getting the data in the format of a EditText
        EditText txtUsername = fieldUsername.getEditText();
        EditText txtPassword = fieldPassword.getEditText();

        //Getting the actual data into a string variable
        String nUsername = txtUsername.getText().toString();
        String nPassword = txtPassword.getText().toString();

        //Creating a constructor for a user instance.
        Users CurrentUser = new Users (nUsername, nPassword);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isValid = false;
                if (snapshot.exists()){
                    for (DataSnapshot UsersSnapshot: snapshot.getChildren()){
                        Users dbUsers = UsersSnapshot.getValue(Users.class);
                        if (dbUsers.getUsername().equals(nUsername) && dbUsers.getPassword().equals(nPassword)){
                            Toast.makeText(MainActivity.this, "User was successfully found.. ",Toast.LENGTH_LONG).show();
                            isValid= true;
                            break;
                        }
                    }
                }
            if (isValid){
                Intent goHomepage = new Intent(MainActivity.this, homePage.class);
                goHomepage.putExtra("CurrentUser",CurrentUser);
                startActivity(goHomepage);
            }
            else {
                Toast.makeText(MainActivity.this, "User was not found...Try Again ",Toast.LENGTH_LONG).show();
                txtUsername.setText("");
                txtPassword.setText("");
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went Wrong :(", Toast.LENGTH_LONG).show();
            }
        });
    }

}

