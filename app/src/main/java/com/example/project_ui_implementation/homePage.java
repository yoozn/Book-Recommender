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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ui_implementation.model.BookAdapter;
import com.example.project_ui_implementation.model.Books;
import com.example.project_ui_implementation.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homePage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Books> bookList;
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

        recyclerView = findViewById(R.id.bookCarousel);
        bookList = new ArrayList<>();
        bookList.add(new Books("Book Number One", "Test Author1", "Fantasy"));
        bookList.add(new Books("Book Number Two", "Test Author2", "Non-Fiction"));
        bookList.add(new Books("Book Number Three", "Test Author3", "Biography"));
        bookList.add(new Books("Book Number Four", "Test Author4", "Fiction"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        bookAdapter = new BookAdapter();
        bookAdapter.setBooks(bookList);
        recyclerView.setAdapter(bookAdapter);


    }
}