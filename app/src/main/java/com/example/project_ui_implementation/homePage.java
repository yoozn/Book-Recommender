package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


    private RecyclerView bottom_recyclerView;

    private ArrayList<Books> bottom_bookList;

    private BookAdapter bottom_BookAdapter;

    private Users CurrentUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView fieldWelcomeMessage;

    TextView fieldRecommendation;

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
        fieldRecommendation = findViewById(R.id.txtRecommendation);

        StringBuilder txtUserRecommendation= new StringBuilder();
        txtUserRecommendation.append("From ");
        txtUserRecommendation.append(currentUsername);
        txtUserRecommendation.append("'s Favorite Author...");
        fieldRecommendation.setText(txtUserRecommendation.toString());


        welcomeMessage.append(fieldWelcomeMessage.getText().toString());
        welcomeMessage.append(" ");
        welcomeMessage.append(currentUsername);
        fieldWelcomeMessage.setText(welcomeMessage.toString());



        //-----------------------//
        //Setting the environment of the database.
        database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("Users");

        recyclerView = findViewById(R.id.bookCarousel);
        bottom_recyclerView = findViewById(R.id.bookCarousel2);
        Button search = findViewById(R.id.searchBtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(homePage.this, "Book Search Started", Toast.LENGTH_LONG).show();
            }
        });

        bookList = new ArrayList<>();
        bottom_bookList = new ArrayList<>();

        bookList.add(new Books("Book Number One", "Test Author1", "Fantasy"));
        bookList.add(new Books("Book Number Two", "Test Author2", "Non-Fiction"));
        bookList.add(new Books("Book Number Three", "Test Author3", "Biography"));
        bookList.add(new Books("Book Number Four", "Test Author4", "Fiction"));

        bottom_bookList.add(new Books("Book Number Five", "Test Author5", "Romance"));
        bottom_bookList.add(new Books("Book Number Six", "Test Author6", "History"));
        bottom_bookList.add(new Books("Book Number Seven", "Test Author7", "Cooking"));
        bottom_bookList.add(new Books("Book Number Eight", "Test Author8", "Programming"));
        bottom_bookList.add(new Books("Book Number Nine", "Test Author9", "Fiction"));
        bottom_bookList.add(new Books("Book Number Ten", "Test Author", "Science"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        bottom_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false ));

        bookAdapter = new BookAdapter();
        bookAdapter.setBooks(bookList);
        //bookAdapter = new BookAdapter(bookList);
        bottom_BookAdapter = new BookAdapter();
        bottom_BookAdapter.setBooks(bottom_bookList);

        recyclerView.setAdapter(bookAdapter);
        bottom_recyclerView.setAdapter(bottom_BookAdapter);

    }
}