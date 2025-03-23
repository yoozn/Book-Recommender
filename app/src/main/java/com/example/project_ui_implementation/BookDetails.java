package com.example.project_ui_implementation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.project_ui_implementation.model.Books;
import com.example.project_ui_implementation.model.UserInSession;
import com.example.project_ui_implementation.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookDetails extends AppCompatActivity {

    String title, author, description, genre, thumbnail;
    TextView vTitle, vAuthor, vDescription, vGenre;
    ImageView vThumbnail;

    Button vRateBookButton;

    FirebaseDatabase database;

    private float selectedRate;

    //Receive the information of the current user from other activity
    private Users CurrentUser =  UserInSession.sessionUser;

    private String Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");

        vTitle = findViewById(R.id.titlePreview);
        vAuthor = findViewById(R.id.authorPreview);
        vDescription = findViewById(R.id.descriptionPreview);
        vThumbnail = findViewById(R.id.thumbnailPreview);

        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");
        genre = getIntent().getStringExtra("genre");
        thumbnail = getIntent().getStringExtra("thumbnail");
        Username = getIntent().getStringExtra("CurrentUsername");

        vTitle.setText(title);
        vAuthor.setText(author);
        vDescription.setText(description);
        Glide.with(this).load(thumbnail).into(vThumbnail);

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        DatabaseReference booksReference = database.getReference("Books").child(title);

        vRateBookButton = findViewById(R.id.rateBookButton);
        /**
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(BookDetails.this, "Selected rating: " + rating, Toast.LENGTH_SHORT).show();
                booksReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("ratings").exists()) {
                            HashMap<String, Float> ratings = new HashMap<>();
                            for (DataSnapshot bookSnapshot : snapshot.child("ratings").getChildren()) {
                                String user = bookSnapshot.getKey();
                                Float userRating = bookSnapshot.getValue(Float.class);
                                if (user != null && userRating != null) {
                                    ratings.put(user, userRating);
                                }
                            }
                            if (CurrentUser.getUsername() == null || CurrentUser.getUsername().isEmpty()) {
                                Toast.makeText(BookDetails.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ratings.put(CurrentUser.getUsername(), rating);

                            float total = 0;
                            for (float rate : ratings.values()) {
                                total += rate;
                            }
                            float averageRating = total / ratings.size();

                            booksReference.child("ratings").setValue(ratings);
                            booksReference.child("averageRating").setValue(averageRating)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(BookDetails.this, "Added rating", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(BookDetails.this, "Failed to add rating", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            HashMap<String, Float> ratings = new HashMap<>();
                            if (CurrentUser.getUsername() == null || CurrentUser.getUsername().isEmpty()) {
                                Toast.makeText(BookDetails.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ratings.put(CurrentUser.getUsername(), rating);

                            Books book = new Books(title, author, genre, thumbnail, description);

                            booksReference.setValue(book)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(BookDetails.this, "Added new book", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(BookDetails.this, "Failed to add new book", Toast.LENGTH_SHORT).show();
                                    });
                            booksReference.child("ratings").setValue(ratings);
                            booksReference.child("averageRating").setValue(rating);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BookDetails.this, "Failed, cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
         **/

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (fromUser) {
                selectedRate = rating;
            }
        });

        vRateBookButton.setOnClickListener(v -> {
            Toast.makeText(BookDetails.this, "Selected rating: " + selectedRate, Toast.LENGTH_SHORT).show();
            booksReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("ratings").exists()) {
                        HashMap<String, Float> ratings = new HashMap<>();
                        for (DataSnapshot bookSnapshot : snapshot.child("ratings").getChildren()) {
                            String user = bookSnapshot.getKey();
                            Float userRating = bookSnapshot.getValue(Float.class);
                            if (user != null && userRating != null) {
                                ratings.put(user, userRating);
                            }
                        }
                        if (CurrentUser.getUsername() == null || CurrentUser.getUsername().isEmpty()) {
                            Toast.makeText(BookDetails.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ratings.put(CurrentUser.getUsername(), selectedRate);

                        float total = 0;
                        for (float rate : ratings.values()) {
                            total += rate;
                        }
                        float averageRating = total / ratings.size();

                        booksReference.child("ratings").setValue(ratings);
                        booksReference.child("averageRating").setValue(averageRating)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(BookDetails.this, "Added rating", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(BookDetails.this, "Failed to add rating", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        HashMap<String, Float> ratings = new HashMap<>();
                        if (CurrentUser.getUsername() == null || CurrentUser.getUsername().isEmpty()) {
                            Toast.makeText(BookDetails.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ratings.put(CurrentUser.getUsername(), selectedRate);

                        Books book = new Books(title, author, genre, thumbnail, description);

                        booksReference.setValue(book)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(BookDetails.this, "Added new book", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(BookDetails.this, "Failed to add new book", Toast.LENGTH_SHORT).show();
                                });
                        booksReference.child("ratings").setValue(ratings);
                        booksReference.child("averageRating").setValue(selectedRate);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(BookDetails.this, "Failed, cancelled", Toast.LENGTH_SHORT).show();
                }
            });
        });

        //vGenre = findViewById(R.id.genrePreview);
    }
}