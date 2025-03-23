package com.example.project_ui_implementation;

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

public class BookDetails extends AppCompatActivity {

    String title, author, description, genre, thumbnail;
    TextView vTitle, vAuthor, vDescription, vGenre;
    ImageView vThumbnail;

    private float rate;

    //Receive the information of the current user from other activity
    private Users CurrentUser = UserInSession.sessionUser;

    private Books selectedBooks;

    FirebaseDatabase database;

    DatabaseReference booksReference;

    DatabaseReference usersReferences;


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

        vTitle = findViewById(R.id.titlePreview);
        vAuthor = findViewById(R.id.authorPreview);
        vDescription = findViewById(R.id.descriptionPreview);
        vThumbnail = findViewById(R.id.thumbnailPreview);

        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");
        genre = getIntent().getStringExtra("genre");
        thumbnail = getIntent().getStringExtra("thumbnail");

        selectedBooks = new Books(title, author, genre, thumbnail, description);
        vTitle.setText(title);
        vAuthor.setText(author);
        vDescription.setText(description);
        Glide.with(this).load(thumbnail).into(vThumbnail);

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
                Toast.makeText(BookDetails.this, "Selected rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });


        Button rateBtn = findViewById(R.id.rateBookButton);

        rateBtn.setOnClickListener(v -> ratingBook());

    }
    public void ratingBook() {





        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");
        booksReference = database.getReference("Books1");

        booksReference.orderByChild("title").equalTo(selectedBooks.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    booksReference.child("ratings").child(CurrentUser.getUsername()).setValue(rate);
                    Toast.makeText(BookDetails.this, "New rating was updated", Toast.LENGTH_SHORT).show();
                } else {
                    selectedBooks.addRatingtoTree(CurrentUser.getUsername(), rate);
                    booksReference.child(selectedBooks.toString()).setValue(selectedBooks);
                    Toast.makeText(BookDetails.this, "Book was save", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookDetails.this, "An Error Happened.. :(", Toast.LENGTH_SHORT).show();
            }
        });

        selectedBooks.addRatingtoTree(CurrentUser.getUsername(), rate);
        booksReference.child(selectedBooks.toString()).setValue(selectedBooks);
        Toast.makeText(BookDetails.this, "Book was save", Toast.LENGTH_SHORT).show();
    }
}