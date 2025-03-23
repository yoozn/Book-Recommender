package com.example.project_ui_implementation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_ui_implementation.model.Admin;
import com.example.project_ui_implementation.model.Books;
import com.example.project_ui_implementation.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminMenu extends AppCompatActivity {

    private Admin CurrentAdmin;

    FirebaseDatabase database;


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


        if (CurrentAdmin != null ) {
            TextView welcomeMessage = findViewById(R.id.welcomeMessageAdmin);
            StringBuilder WelcomeMessageAdmin = new StringBuilder();
            WelcomeMessageAdmin.append(welcomeMessage.getText().toString());
            WelcomeMessageAdmin.append(" ");
            WelcomeMessageAdmin.append(CurrentAdmin.getUsername());
            welcomeMessage.setText(WelcomeMessageAdmin.toString());
            Toast.makeText(AdminMenu.this, "Admin gotten." + CurrentAdmin.getUsername(), Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(AdminMenu.this, "Null Admin", Toast.LENGTH_SHORT).show();
        }
        Button goBack = findViewById(R.id.goBackbtn);

        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");


        goBack.setOnClickListener(v -> {
            Intent goHome = new Intent(AdminMenu.this, homePage.class);
            startActivity(goHome);
        });

        Button editBooks = findViewById(R.id.editBook);

        editBooks.setOnClickListener(v -> {
            Intent myIntent = new Intent(AdminMenu.this, editBooks.class);
            startActivity(myIntent);
        });

        Button seeUsers = findViewById(R.id.usersbtn);

        seeUsers.setOnClickListener(v -> {

            ArrayList<Users> CurrentUsers= new ArrayList<>();

            DatabaseReference usersReference = database.getReference("Users");
            usersReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()){
                        Users db = userSnapshot.getValue(Users.class);
                            CurrentUsers.add(db);
                    }
                    StringBuilder allUsers = new StringBuilder();
                    for (int i=0; i<CurrentUsers.size(); i++){
                        allUsers.append(i +". ");
                        allUsers.append(CurrentUsers.get(i).toString());
                        allUsers.append("\n");
                    }
                    AlertDialog.Builder displayUsers = new AlertDialog.Builder(AdminMenu.this);
                    displayUsers.setTitle("Current Users inside the Database");
                    displayUsers.setMessage(allUsers.toString());
                    displayUsers.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdminMenu.this, "Something went Wrong :(", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
