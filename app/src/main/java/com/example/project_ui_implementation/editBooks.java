package com.example.project_ui_implementation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ui_implementation.model.BookAdapter;
import com.example.project_ui_implementation.model.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class editBooks extends AppCompatActivity {
    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    private List<Books> bookList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_books);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GridLayoutManager LayoutManager = new GridLayoutManager(this, 2);

        recyclerView = findViewById(R.id.adminRV);
        bookAdapter = new BookAdapter(editBooks.this);
        bookAdapter.setBooks(bookList);
        bookAdapter.setWideBook(true);
        bookAdapter.setEditable(true);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(LayoutManager);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Books");

        fetchBooks();
    }

    private void fetchBooks() {
        DatabaseReference booksReference = database.getReference("Books");
        booksReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                    String title = bookSnapshot.getKey();
                    String author = bookSnapshot.child("author").getValue(String.class);
                    String genre = bookSnapshot.child("genre").getValue(String.class);
                    String description = bookSnapshot.child("description").getValue(String.class);
                    String thumbnail = bookSnapshot.child("thumbnail").getValue(String.class);
                    HashMap<String, Float> ratings = (HashMap<String, Float>) bookSnapshot.child("ratings").getValue();
                    Float averageRating = bookSnapshot.child("averageRating").getValue(Float.class);
                    Books book = new Books(title, author, genre, thumbnail, description, averageRating);
                    bookList.add(book);
                    Log.d("Test Log",book.getTitle());
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(editBooks.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }

}